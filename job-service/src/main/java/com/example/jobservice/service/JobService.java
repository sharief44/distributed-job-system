package com.example.jobservice.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.jobservice.dto.CreateJobRequest;
import com.example.jobservice.entity.Job;
import com.example.jobservice.enums.JobStatus;
import com.example.jobservice.repository.JobRepository;

@Service
public class JobService {

    private final JobRepository jobRepository;

    // Constructor Injection
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job createJob(CreateJobRequest request) {
        Job job = new Job();

        job.setType(request.getJobType());
        job.setStatus(JobStatus.PENDING);
        job.setPayload(request.getPayload());
        job.setRetryCount(0);
        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());

        return jobRepository.save(job);
    }

    public Job getJob(String jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}
