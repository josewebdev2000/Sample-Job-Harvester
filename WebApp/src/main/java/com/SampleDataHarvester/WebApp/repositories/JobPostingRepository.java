package com.SampleDataHarvester.WebApp.repositories;

import com.SampleDataHarvester.WebApp.entities.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long>
{

}
