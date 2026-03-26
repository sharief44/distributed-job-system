package com.example.pdfservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.pdfservice.dto.JobDTO;
import com.example.pdfservice.entity.Job;
import com.example.pdfservice.enums.JobStatus;
import com.example.pdfservice.enums.JobType;
import com.example.pdfservice.repository.JobRepository;

@Service
public class KafkaConsumerService {

    private final JobRepository jobRepository;

    public KafkaConsumerService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @KafkaListener(topics = "job-topic-v2", groupId = "worker-group")
    public void consume(JobDTO jobDTO) {

        System.out.println("Got job: " + jobDTO.getJobId());

        // 🔥 Convert DTO → Entity
        JobType type = JobType.valueOf(jobDTO.getType());

        if (type != JobType.PDF_GENERATION) {
            return;
        }

        Job dbJob = jobRepository.findById(jobDTO.getJobId()).orElseThrow();

        dbJob.setStatus(JobStatus.PROCESSING);
        jobRepository.save(dbJob);

        try {
            Thread.sleep(3000);

            dbJob.setStatus(JobStatus.COMPLETED);
            jobRepository.save(dbJob);

            System.out.println("Done job: " + jobDTO.getJobId());

        } catch (Exception e) {
            dbJob.setStatus(JobStatus.FAILED);
            jobRepository.save(dbJob);
        }
    }
}