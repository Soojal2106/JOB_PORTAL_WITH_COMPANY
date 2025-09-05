package com.jobPortal.controller;

import com.jobPortal.model.Application;
import com.jobPortal.model.Company;
import com.jobPortal.model.Job;
import com.jobPortal.service.ApplicationService;
import com.jobPortal.service.CompanyService;
import com.jobPortal.service.JobService;

import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private JobService jobService;
    @Autowired
    private ApplicationService applicationService;

    // Registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("company", new Company());
        return "company/company_register";  // company_register.html
    }

    // Save Company
    @PostMapping("/save")
    public String saveCompany(@ModelAttribute("company") Company company, Model model) {
        companyService.saveCompany(company);
        model.addAttribute("message", "Company Registered Successfully!");
        return "company/companyLogin";
    }

    // Login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "company/companyLogin"; // companyLogin.html
    }

    // Login form submit
    @PostMapping("/login")
    public String login(@RequestParam String companyEmail,
                        @RequestParam String companyPassword,
                        HttpSession session,
                        Model model) {
        Company company = companyService.loginValidation(companyEmail, companyPassword);

        if (company != null) {
            session.setAttribute("loggedInCompany", company); // session में company save
            model.addAttribute("company", company);
            return "company/companyDashboard"; // login success
        } else {
            model.addAttribute("error", "Invalid Email or Password");
            return "company/companyLogin"; // back to login
        }
    }

    // Dashboard page
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        Company company = (Company) session.getAttribute("loggedInCompany");
        if (company == null) {
            return "redirect:/company/login"; // अगर login नहीं तो login page
        }
        model.addAttribute("company", company);
        return "company/companyDashboard";
    }

    // Profile page
    @GetMapping("/profile")
    public String companyProfile(HttpSession session, Model model) {
        Company company = (Company) session.getAttribute("loggedInCompany");
        if (company == null) {
            return "redirect:/company/login";
        }
        model.addAttribute("company", company);
        return "company/companyProfile"; // companyProfile.html
    }

    // Edit Profile page
    @GetMapping("/editProfile")
    public String editProfile(HttpSession session, Model model) {
        Company company = (Company) session.getAttribute("loggedInCompany");
        if (company == null) {
            return "redirect:/company/login";
        }
        model.addAttribute("company", company);
        return "company/companyEditProfile"; // companyEditProfile.html
    }

    // Update Profile
    @PostMapping("/update")
    public String updateCompany(@ModelAttribute("company") Company company,
                                HttpSession session,
                                Model model) {
        Company loggedInCompany = (Company) session.getAttribute("loggedInCompany");
        if (loggedInCompany == null) {
            return "redirect:/company/login";
        }

        // email को हमेशा session वाले company से लेना चाहिए
        companyService.updateCompany(company, loggedInCompany.getEmail());

        session.setAttribute("loggedInCompany", company); // update session
        model.addAttribute("company", company);
        model.addAttribute("message", "Profile updated successfully!");
        return "company/companyProfile"; // back to profile
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // clear session
        return "redirect:/company/login";
    }


    // Job Create Form Page
    @GetMapping("/postJob")
    public String showPostJobForm(Model model, HttpSession session) {
        Company company = (Company) session.getAttribute("loggedInCompany");
        if (company == null) {
            return "redirect:/company/login"; // अगर login नहीं है तो login page
        }

        model.addAttribute("job", new Job());
        return "company/postJob"; // Thymeleaf page
    }

    // Job Submit / Save
    @PostMapping("/saveJob")
    public String saveJob(@ModelAttribute("job") Job job, HttpSession session, Model model) {
        Company company = (Company) session.getAttribute("loggedInCompany");
        if (company == null) {
            return "redirect:/company/login";
        }

        // Job के company को set करें
        job.setCompany(company);

        // Save job using service
        jobService.saveJob(job);

        model.addAttribute("message", "Job posted successfully!");
        return "company/postJob"; // form page पे message दिखाना
    }
    
    @GetMapping("/viewJobs")
    public String viewCompanyJobs(Model model, HttpSession session) {
        Company loggedCompany = (Company) session.getAttribute("loggedInCompany"); // ✔ correct attribute name
        if (loggedCompany == null) {
            return "redirect:/company/login"; // अगर login नहीं है
        }
        List<Job> jobs = jobService.getJobsByCompany(loggedCompany);
        model.addAttribute("jobs", jobs);
        return "company/viewJobs";  // Thymeleaf template
    }
    
    @GetMapping("/applications")
    public String viewApplications(HttpSession session, Model model) {
        Company company = (Company) session.getAttribute("loggedInCompany");
        if (company == null) {
            return "redirect:/company/login";
        }

        List<Application> applications = applicationService.getApplicationsByCompany(company);
        model.addAttribute("applications", applications);
        model.addAttribute("company", company);

        return "company/applications"; // applications.html
    }



}