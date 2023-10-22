package com.SampleDataHarvester.WebApp.services;

import com.SampleDataHarvester.WebApp.dtos.JobPostingDto;
import com.SampleDataHarvester.WebApp.entities.JobPosting;

import java.util.List;

public interface JobPostingService
{
    JobPostingDto createJobPosting(JobPostingDto jobPostingDto);
    JobPostingDto getJobPostingById(Long jobPostingId);
    List<JobPostingDto> getAllJobPostings();
    JobPostingDto updateJobPosting(Long jobPostingId, JobPostingDto updatedJobPostingDto);
    void deleteJobPosting(Long jobPostingId);

}
