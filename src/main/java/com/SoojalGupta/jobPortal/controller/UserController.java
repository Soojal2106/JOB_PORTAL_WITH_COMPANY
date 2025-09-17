package com.SoojalGupta.jobPortal.controller;

import com.SoojalGupta.jobPortal.model.Application;
import com.SoojalGupta.jobPortal.model.Job;
import com.SoojalGupta.jobPortal.model.User;
import com.SoojalGupta.jobPortal.service.ApplicationService;
import com.SoojalGupta.jobPortal.service.JobService;
import com.SoojalGupta.jobPortal.service.UserService;

import java.util.*;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JobService jobService;
    
    @Autowired
    private ApplicationService applicationService;

    // Show Registration Form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Empty object for form binding
        return "user/register"; // register.html
    }

    // Save User
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        userService.saveUser(user);
        model.addAttribute("message", "User Registered Successfully!");
        return "user/UserLogin"; // UserLogin.html
    }

    // Show Login Page
    @GetMapping("/login")
    public String showLoginPage() {
        return "user/UserLogin"; // userLogin.html
    }

    // Handle Login Form Submit
    @PostMapping("/login")
    public String loginUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        User user = userService.loginValidation(email, password);

        if (user != null) {
            session.setAttribute("loggedInUser", user); // save user in session
            model.addAttribute("user", user);
            return "user/userDashboard"; // login success
        } else {
            model.addAttribute("error", "Invalid Email or Password");
            return "user/UserLogin"; // back to login page
        }
    }

    // Show Dashboard Page
    @GetMapping("/userDashboard")
    public String showDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login"; // if not logged in, redirect to login
        }
        model.addAttribute("user", user);
        return "user/userDashboard"; // userDashboard.html
    }

    // Show User Profile Page
    @GetMapping("/profile")
    public String userProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login"; // if not logged in, redirect to login
        }
        model.addAttribute("user", user);
        return "user/profile"; // userProfile.html
    }

    // Logout User
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // clear session
        return "redirect:/users/login";
    }

    // Show Edit Profile Page
    @GetMapping("/editProfile")
    public String userEditProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login"; // if not logged in, redirect to login
        }
        model.addAttribute("user", user);
        return "user/userEditProfile"; // userEditProfile.html
    }
     
    // Update User Profile
    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, Model model, HttpSession session) {

        // get logged-in user from session
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/users/login"; // if not logged in
        }

        // fetch email of logged-in user
        String email = loggedInUser.getUserEmail();

        // call update logic in service
        userService.updateUser(user, email);

        // update session and model
        session.setAttribute("loggedInUser", user); // save updated user in session
        model.addAttribute("user", user);
        model.addAttribute("message", "Profile updated successfully!");

        return "user/profile"; // Thymeleaf profile page
    }

    // Show Jobs that user can apply
    @GetMapping("/currentJobs")
    public String viewCurrentJobs(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login"; 
        }

        // fetch only ENABLE jobs
        List<Job> allJobs = jobService.getAllEnableJobs();

        // get jobs user already applied for
        List<Application> userApplications = applicationService.findByUser(user);

        Set<Long> appliedJobIds = userApplications.stream()
                                                  .map(app -> app.getJob().getId())
                                                  .collect(Collectors.toSet());

        // filter jobs that are not applied yet
        List<Job> availableJobs = allJobs.stream()
                                         .filter(job -> !appliedJobIds.contains(job.getId()))
                                         .collect(Collectors.toList());

        model.addAttribute("jobs", availableJobs); // filtered jobs
        model.addAttribute("user", user);
        session.setAttribute("loggedInUser", user); 

        return "user/currentJobs";  
    }

    // Show Recent Jobs (within X days)
    @GetMapping("/recent")
    public String viewRecentJobs(@RequestParam(defaultValue = "60") int days, HttpSession session,Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login"; }
    	List<Job> jobs = jobService.getJobsPostedWithinDays(days);
    	 List<Application> userApplications = applicationService.findByUser(user);

         Set<Long> appliedJobIds = userApplications.stream()
                                                   .map(app -> app.getJob().getId())
                                                   .collect(Collectors.toSet());

         // filter jobs that are not applied yet
         List<Job> availableJobs = jobs.stream()
                                          .filter(job -> !appliedJobIds.contains(job.getId()))
                                          .collect(Collectors.toList());

        model.addAttribute("jobs", availableJobs);
        model.addAttribute("days", days);
        session.setAttribute("loggedInUser", user);
        return "user/currentJobs";  
    }

    // Apply for a Job
    @PostMapping("/apply/{jobId}")
    public String applyForJob(@PathVariable Long jobId,
                              HttpSession session,
                              RedirectAttributes redirectAttributes,Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login";
        }

        Job job = jobService.getJobById(jobId);
        if (job == null) {
            redirectAttributes.addFlashAttribute("error", "Job not found!");
            model.addAttribute("user", user);
            return "redirect:/users/currentJobs";
        }
 
        if (applicationService.ApplyExistsByUserAndJob(user, job)) {
            redirectAttributes.addFlashAttribute("error", "⚠️ You have already applied for this job!");
            model.addAttribute("user", user);
            return "redirect:/users/currentJobs";
        }

        // apply for job
        applicationService.applyJob(user, job);

        redirectAttributes.addFlashAttribute("success", "✅ Successfully applied for the job!");
        model.addAttribute("user", user);
        return "redirect:/users/currentJobs";
    }
    
    // View All Applied Jobs
    @GetMapping("/appliedJobs")
    public String viewAppliedJobs(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login"; 
        }

        List<Application> applications = applicationService.getApplicationsByUser(user);

        model.addAttribute("applications", applications);
        model.addAttribute("user", user);

        return "user/appliedJobs"; 
    }

}
