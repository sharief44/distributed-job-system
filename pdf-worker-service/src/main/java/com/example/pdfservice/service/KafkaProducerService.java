package com.example.pdfservice.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.pdfservice.dto.JobDTO;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, JobDTO> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, JobDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToMainTopic(JobDTO job) {
        kafkaTemplate.send("job-topic-v2", job);
    }

    public void sendToDLQ(JobDTO job) {
        kafkaTemplate.send("job-dlq", job);
    }
}