package com.SoojalGupta.jobPortal.service;
 
import com.SoojalGupta.jobPortal.model.Company;
import com.SoojalGupta.jobPortal.model.Job;
import com.SoojalGupta.jobPortal.model.JobStatus;
import com.SoojalGupta.jobPortal.repositry.JobRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<Job> getJobsPostedWithinDays(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        return jobRepository.findByPostedAtAfterAndJobStatus(cutoffDate,JobStatus.ENABLE);
    }

	public List<Job> findByTitleContainingIgnoreCase(String keyword) {
		// TODO Auto-generated method stub
		return jobRepository.findByTitleContainingIgnoreCase(keyword);
	}

	 

	public List<Job> getAllEnableJobs() {
		// TODO Auto-generated method stub
		List<Job> jobs = jobRepository.findByJobStatus(JobStatus.ENABLE);
	 
		return jobs;

	}

 
	 
}
