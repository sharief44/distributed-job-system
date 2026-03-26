package com.example.jobservice.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.jobservice.dto.JobDTO;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, JobDTO> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, JobDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendJob(JobDTO job) {
        kafkaTemplate.send("job-topic-v2", job);
    }
}