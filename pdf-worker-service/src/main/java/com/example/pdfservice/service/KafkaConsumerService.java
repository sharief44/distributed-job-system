package com.example.pdfservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.pdfservice.dto.JobDTO;
import com.example.pdfservice.entity.Job;
import com.example.pdfservice.enums.JobStatus;
import com.example.pdfservice.enums.JobType;
import com.example.pdfservice.repository.JobRepository;

import java.time.LocalDateTime;

@Service
public class KafkaConsumerService {

    private final JobRepository jobRepository;
    private final KafkaProducerService producer;

    public KafkaConsumerService(JobRepository jobRepository,
                                KafkaProducerService producer) {
        this.jobRepository = jobRepository;
        this.producer = producer;
    }

    @KafkaListener(topics = "job-topic-v2", groupId = "worker-group")
    public void consume(JobDTO jobDTO) {

        System.out.println("Got job: " + jobDTO.getJobId());

        JobType type = JobType.valueOf(jobDTO.getType());

        if (type != JobType.PDF_GENERATION) {
            return;
        }

        Job dbJob = jobRepository.findById(jobDTO.getJobId()).orElseThrow();

        try {
            // 🔄 Mark PROCESSING
            dbJob.setStatus(JobStatus.PROCESSING);
            dbJob.setUpdatedAt(LocalDateTime.now());
            jobRepository.save(dbJob);

            // 🔥 Simulate processing
            Thread.sleep(3000);

            // 🔥 SUCCESS
            dbJob.setStatus(JobStatus.COMPLETED);
            dbJob.setUpdatedAt(LocalDateTime.now());
            jobRepository.save(dbJob);

            System.out.println("Done job: " + jobDTO.getJobId());

        } catch (Exception e) {

            e.printStackTrace();

            int retryCount = dbJob.getRetryCount();

            if (retryCount < 3) {
                // 🔁 Retry
                dbJob.setRetryCount(retryCount + 1);
                dbJob.setStatus(JobStatus.PENDING);
                dbJob.setUpdatedAt(LocalDateTime.now());
                jobRepository.save(dbJob);

                System.out.println("Retrying job: " + jobDTO.getJobId());

                // Send back to Kafka
                producer.sendToMainTopic(jobDTO);

            } else {
                // ☠️ Send to DLQ
                dbJob.setStatus(JobStatus.FAILED);
                dbJob.setUpdatedAt(LocalDateTime.now());
                jobRepository.save(dbJob);

                System.out.println("Sending to DLQ: " + jobDTO.getJobId());

                producer.sendToDLQ(jobDTO);
            }
        }
    }
}