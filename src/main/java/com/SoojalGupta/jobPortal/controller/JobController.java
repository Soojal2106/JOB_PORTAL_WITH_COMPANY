package com.SoojalGupta.jobPortal.controller;
import com.SoojalGupta.jobPortal.model.Job;
import com.SoojalGupta.jobPortal.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // Job create form (Company side)
    
    @GetMapping("/create")
    public String showCreateJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "job-create";
    }

    // Job save
    @PostMapping("/save")
    public String saveJob(@ModelAttribute("job") Job job) {
        jobService.saveJob(job);
        return "redirect:/jobs/list";
    }

    // Show all jobs (User dashboard side)
    @GetMapping("/list")
    public String listJobs(Model model) {
        model.addAttribute("jobs", jobService.getAllJobs());
        return "job-list";
    }
   

}
