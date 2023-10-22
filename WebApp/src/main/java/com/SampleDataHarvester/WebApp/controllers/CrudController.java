package com.SampleDataHarvester.WebApp.controllers;

import com.SampleDataHarvester.WebApp.dtos.JobPostingDto;
import com.SampleDataHarvester.WebApp.exceptions.MissingIdParameterException;
import com.SampleDataHarvester.WebApp.services.JobPostingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class CrudController
{
    private JobPostingService jobPostingService;

    // Build route to add a job posting
    @PostMapping("/jobPosting")
    public ResponseEntity<JobPostingDto> createJobPosting(@RequestBody JobPostingDto jobPostingDto)
    {
        JobPostingDto savedJobPosting = jobPostingService.createJobPosting(jobPostingDto);
        return new ResponseEntity<>(savedJobPosting, HttpStatus.CREATED);
    }

    // Build route to get a job posting by its id
    @GetMapping("/jobPostingById")
    public ResponseEntity<JobPostingDto> getJobPostingById(@RequestParam(name= "id", required = true) Long jobPostingId)
    {
        if (jobPostingId == null)
        {
            throw new MissingIdParameterException("No id parameter was given");
        }

        JobPostingDto jobPostingDto = jobPostingService.getJobPostingById(jobPostingId);
        return ResponseEntity.ok(jobPostingDto);
    }

    // Build route to get all job postings
    @GetMapping("/jobPostings")
    public ResponseEntity<List<JobPostingDto>> getAllJobPostings()
    {
        List<JobPostingDto> jobPostings = jobPostingService.getAllJobPostings();
        return ResponseEntity.ok(jobPostings);
    }

    // Build route to update job posting by ID
    @PutMapping("/jobPostingById")
    public ResponseEntity<JobPostingDto> updateJobPosting(@RequestParam(name = "id", required = true) Long jobPostingId, @RequestBody JobPostingDto updatedJobPostingDto)
    {
        if (jobPostingId == null)
        {
            throw new MissingIdParameterException("No id parameter was given");
        }

        JobPostingDto jobPostingDto = jobPostingService.updateJobPosting(jobPostingId, updatedJobPostingDto);
        return ResponseEntity.ok(jobPostingDto);
    }

    // Build route to delete job posting
    @DeleteMapping("/jobPostingById")
    public ResponseEntity<String> deleteJobPosting(@RequestParam(name = "id", required = false) Long jobPostingId)
    {
        if (jobPostingId == null)
        {
            throw new MissingIdParameterException("No id parameter was given");
        }

        jobPostingService.deleteJobPosting(jobPostingId);
        return ResponseEntity.ok("Job Posting of id " + jobPostingId + " was successfully deleted.");
    }
}
