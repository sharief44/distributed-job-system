package com.example.jobservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.jobservice.dto.CreateJobRequest;
import com.example.jobservice.entity.Job;
import com.example.jobservice.service.JobService;

@RestController
@RequestMapping("/jobs")
public class JobController {
    
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // ✅ CREATE JOB
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody CreateJobRequest request){
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