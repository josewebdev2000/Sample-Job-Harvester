package com.SampleDataHarvester.WebApp.mappers;

import com.SampleDataHarvester.WebApp.dtos.JobPostingDto;
import com.SampleDataHarvester.WebApp.entities.JobPosting;

public class JobPostingMapper
{
    public static JobPostingDto mapToJobPostingDto(JobPosting jobPosting)
    {
        return new JobPostingDto(
                jobPosting.getId(),
                jobPosting.getRole(),
                jobPosting.getCompany(),
                jobPosting.getLocation(),
                jobPosting.getDate()
        );
    }

    public static JobPosting mapToJobPosting(JobPostingDto jobPostingDto)
    {
        return new JobPosting(
                jobPostingDto.getId(),
                jobPostingDto.getRole(),
                jobPostingDto.getCompany(),
                jobPostingDto.getLocation(),
                jobPostingDto.getDate()
        );
    }
}
