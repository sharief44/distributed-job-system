package com.example.jobservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobservice.entity.Job;

public interface JobRepository extends JpaRepository<Job, String> {

}
