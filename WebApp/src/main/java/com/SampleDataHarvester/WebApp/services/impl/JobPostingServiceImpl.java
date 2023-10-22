package com.SampleDataHarvester.WebApp.services.impl;

import com.SampleDataHarvester.WebApp.dtos.JobPostingDto;
import com.SampleDataHarvester.WebApp.entities.JobPosting;
import com.SampleDataHarvester.WebApp.exceptions.ResourceNotFoundException;
import com.SampleDataHarvester.WebApp.mappers.JobPostingMapper;
import com.SampleDataHarvester.WebApp.repositories.JobPostingRepository;
import com.SampleDataHarvester.WebApp.services.JobPostingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobPostingServiceImpl implements JobPostingService
{
    private JobPostingRepository jobPostingRepository;

    @Override
    public JobPostingDto createJobPosting(JobPostingDto jobPostingDto)
    {
        // Insert a new Job Posting to the DB
        JobPosting jobPosting = JobPostingMapper.mapToJobPosting(jobPostingDto);
        JobPosting savedJobPosting = jobPostingRepository.save(jobPosting);
        return JobPostingMapper.mapToJobPostingDto(savedJobPosting);
    }

    @Override
    public JobPostingDto getJobPostingById(Long jobPostingId)
    {
        // Get a Job Posting By Its id
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no job posting of ID: " + jobPostingId));

        return JobPostingMapper.mapToJobPostingDto(jobPosting);
    }

    @Override
    public List<JobPostingDto> getAllJobPostings()
    {
        List<JobPosting> jobPostings = jobPostingRepository.findAll();
        return jobPostings
                .stream()
                .map(JobPostingMapper::mapToJobPostingDto)
                .collect(Collectors.toList());
    }

    @Override
    public JobPostingDto updateJobPosting(Long jobPostingId, JobPostingDto updatedJobPostingDto)
    {
        JobPosting jobPosting = jobPostingRepository
                .findById(jobPostingId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no job posting of ID: " + jobPostingId));

        jobPosting.setRole(updatedJobPostingDto.getRole());
        jobPosting.setCompany(updatedJobPostingDto.getCompany());
        jobPosting.setLocation(updatedJobPostingDto.getLocation());
        jobPosting.setDate(updatedJobPostingDto.getDate());

        JobPosting updatedJobPosting = jobPostingRepository.save(jobPosting);

        return JobPostingMapper.mapToJobPostingDto(updatedJobPosting);
    }

    @Override
    public void deleteJobPosting(Long jobPostingId)
    {
        jobPostingRepository.findById(jobPostingId).orElseThrow(() -> new ResourceNotFoundException("There is no job posting of ID: " + jobPostingId));
        jobPostingRepository.deleteById(jobPostingId);
    }

}
