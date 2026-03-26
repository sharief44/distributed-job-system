package com.example.jobservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.jobservice.dto.CreateJobRequest;
import com.example.jobservice.dto.JobDTO; // ✅ NEW
import com.example.jobservice.entity.Job;
import com.example.jobservice.enums.JobStatus;
import com.example.jobservice.repository.JobRepository;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final KafkaProducerService kafkaProducerService;

    public JobService(JobRepository jobRepository,
                      KafkaProducerService kafkaProducerService) {
        this.jobRepository = jobRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public Job createJob(CreateJobRequest request) {

        // ✅ Step 1: Create Entity
        Job job = new Job();
        job.setType(request.getJobType());
        job.setStatus(JobStatus.PENDING);
        job.setPayload(request.getPayload());
        job.setRetryCount(0);
        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());

        // ✅ Step 2: Save to DB
        Job savedJob = jobRepository.save(job);

        // ✅ Step 3: Convert Entity → DTO
        JobDTO dto = mapToDTO(savedJob);

        // ✅ Step 4: Send DTO to Kafka
        kafkaProducerService.sendJob(dto);

        return savedJob;
    }

    public Job getJob(String jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // 🔥 Mapper method (IMPORTANT)
    private JobDTO mapToDTO(Job job) {
        JobDTO dto = new JobDTO();

        dto.setJobId(job.getJobId());
        dto.setType(job.getType().name());
        dto.setStatus(job.getStatus().name());
        dto.setPayload(job.getPayload());
        dto.setRetryCount(job.getRetryCount());
        dto.setCreatedAt(job.getCreatedAt());
        dto.setUpdatedAt(job.getUpdatedAt());

        return dto;
    }
}