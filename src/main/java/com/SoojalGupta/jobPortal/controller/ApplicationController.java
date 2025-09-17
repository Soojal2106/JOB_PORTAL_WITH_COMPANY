package com.SoojalGupta.jobPortal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {
	@GetMapping("/index")
    public String showindex() { 
        return "index";  // company_register.html
    }
}
