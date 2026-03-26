package com.example.pdfservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pdfservice.entity.Job;

public interface JobRepository extends JpaRepository<Job,String> {

}
