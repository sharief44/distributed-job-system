package com.example.jobservice.dto;

import com.example.jobservice.enums.JobType;

public class CreateJobRequest {
	
	private JobType jobType;
	private String payload;
	
	public CreateJobRequest() {}

	public JobType getJobType() {
		return jobType;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	
	
	
}
