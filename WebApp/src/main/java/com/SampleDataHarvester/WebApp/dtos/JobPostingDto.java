package com.SampleDataHarvester.WebApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobPostingDto
{
    private Long id;
    private String role;
    private String company;
    private String location;
    private Date date;
}
