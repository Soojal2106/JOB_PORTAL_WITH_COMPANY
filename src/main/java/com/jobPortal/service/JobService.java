package com.jobPortal.service;
 
import com.jobPortal.model.Company;
import com.jobPortal.model.Job;
import com.jobPortal.repositry.JobRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
@Autowired
    private  JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
 

//    public List<Job> getJobsByCompany(Company company) {
//        return jobRepository.findByCompany(company);
//    }
    public List<Job> getJobsByCompany( Company company) {
        return jobRepository.findAll()
                .stream()
                .filter(job -> job.getCompany().getId().equals(company.getId()))
                .toList();
    }

    // 👉 यह method add करो
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

 
	 
}
