package com.jobPortal.controller;

import com.jobPortal.model.Application;
import com.jobPortal.model.Job;
import com.jobPortal.model.User;
import com.jobPortal.service.ApplicationService;
import com.jobPortal.service.JobService;
import com.jobPortal.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;

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

    // Registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // empty object form binding
        return "user/register"; // register.html
    }

    // Save UserS
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        userService.saveUser(user);
        model.addAttribute("message", "User Registered Successfully!");
        return "user/UserLogin"; // UserLogin.html
    }

    // Login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "user/UserLogin"; // userLogin.html
    }

    // Login form submit
    @PostMapping("/login")
    public String loginUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        User user = userService.loginValidation(email, password);

        if (user != null) {
            session.setAttribute("loggedInUser", user); // session में user save
            model.addAttribute("user", user);
            return "user/userDashboard"; // login success
        } else {
            model.addAttribute("error", "Invalid Email or Password");
            return "user/UserLogin"; // back to login
        }
    }

    // Dashboard page
    @GetMapping("/userDashboard")
    public String showDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login"; // अगर login नहीं तो login page
        }
        model.addAttribute("user", user);
        return "user/userDashboard"; // userDashboard.html
    }

    // User Profile page
    @GetMapping("/profile")
    public String userProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login"; // अगर login नहीं तो login page
        }
        model.addAttribute("user", user);
        return "user/profile"; // userProfile.html
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // session clear
        return "redirect:/users/login";
    }
    @GetMapping("/editProfile")
    public String userEditProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login"; // अगर login नहीं तो login page
        }
        model.addAttribute("user", user);
        return "user/userEditProfile"; // userProfile.html
    }
     

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, Model model, HttpSession session) {

        // session से logged-in user
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/users/login"; // अगर user login नहीं है
        }

        // email fetch
        String email = loggedInUser.getUserEmail();

        // userService में update logic call
        userService.updateUser(user, email);

        // session और model update करें
        session.setAttribute("loggedInUser", user); // updated user save
        model.addAttribute("user", user);
        model.addAttribute("message", "Profile updated successfully!");

        return "user/profile"; // Thymeleaf profile page
    }


    @GetMapping("/currentJobs")
    public String viewCurrentJobs(Model model,HttpSession session) {
    	User user = (User) session.getAttribute("loggedInUser");
    	if (user == null) {
            return "redirect:/users/login"; // अगर login नहीं तो login page
        }
        List<Job> jobs = jobService.getAllJobs(); // सभी jobs user को दिखाएँ
        model.addAttribute("jobs", jobs);
        model.addAttribute("user", user); // user का data भी bhej do page par

        return "user/currentJobs";  
    }
    
    
 
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

        // Check if already applied
        if (applicationService.ApplyExistsByUserAndJob(user, job)) {
            redirectAttributes.addFlashAttribute("error", "⚠️ You have already applied for this job!");
            model.addAttribute("user", user);
            return "redirect:/users/currentJobs";
        }

        // पहली बार apply कर रहा है
        applicationService.applyJob(user, job);

        redirectAttributes.addFlashAttribute("success", "✅ Successfully applied for the job!");
        model.addAttribute("user", user);
        return "redirect:/users/currentJobs";
    }
    
    
    @GetMapping("/appliedJobs")
    public String viewAppliedJobs(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login"; // अगर login नहीं है
        }

        List<Application> applications = applicationService.getApplicationsByUser(user);

        model.addAttribute("applications", applications);
        model.addAttribute("user", user);

        return "user/appliedJobs";  // Thymeleaf template
    }





     




}  