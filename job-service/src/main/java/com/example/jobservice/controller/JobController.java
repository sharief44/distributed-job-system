package com.example.jobservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jobservice.dto.CreateJobRequest;
import com.example.jobservice.entity.Job;
import com.example.jobservice.service.JobService;
import com.example.jobservice.service.RateLimiterService;

@RestController
@RequestMapping("/jobs")
public class JobController {
    
    private final JobService jobService;
    private final RateLimiterService rateLimiterService;

    public JobController(JobService jobService,
    		             RateLimiterService rateLimiterService) {
        this.jobService = jobService;
        this.rateLimiterService=rateLimiterService;
    }

    // ✅ CREATE JOB
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody CreateJobRequest request){

        if (!rateLimiterService.isAllowed("user1")) {
            return ResponseEntity.status(429).body(null);
        }

        Job job = jobService.createJob(request); 
        return ResponseEntity.ok(job);
    }

    // ✅ GET JOB BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJob(@PathVariable String id){
        Job job = jobService.getJob(id);
        return ResponseEntity.ok(job);
    }

    // ✅ GET ALL JOBS
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs(){
        return ResponseEntity.ok(jobService.getAllJobs());
    }
}