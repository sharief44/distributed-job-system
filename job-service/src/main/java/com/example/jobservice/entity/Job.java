package com.example.jobservice.entity;

import java.time.LocalDateTime;

import com.example.jobservice.enums.JobStatus;
import com.example.jobservice.enums.JobType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="jobs")
public class Job {

	
	    @Id
	    @GeneratedValue(strategy = GenerationType.UUID)
	    private String jobId;

	    @Enumerated(EnumType.STRING)
	    private JobType type;

	    @Enumerated(EnumType.STRING)
	    private JobStatus status;

	    @Column(columnDefinition = "TEXT")
	    private String payload;
	    
	    private int retryCount;
	    
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
	    
	    public Job() {}

		public String getJobId() {
			return jobId;
		}

		public void setJobId(String jobId) {
			this.jobId = jobId;
		}

		public JobType getType() {
			return type;
		}

		public void setType(JobType type) {
			this.type = type;
		}

		public JobStatus getStatus() {
			return status;
		}

		public void setStatus(JobStatus status) {
			this.status = status;
		}

		public String getPayload() {
			return payload;
		}

		public void setPayload(String payload) {
			this.payload = payload;
		}

		public int getRetryCount() {
			return retryCount;
		}

		public void setRetryCount(int retryCount) {
			this.retryCount = retryCount;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}
	
}
