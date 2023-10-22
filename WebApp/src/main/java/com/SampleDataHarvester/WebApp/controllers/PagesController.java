package com.SampleDataHarvester.WebApp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class PagesController
{
    @GetMapping("/")
    public String index()
    {
        return "index";
    }
}
