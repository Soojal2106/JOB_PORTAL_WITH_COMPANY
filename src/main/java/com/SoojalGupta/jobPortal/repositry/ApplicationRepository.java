package com.SoojalGupta.jobPortal.repositry;
 
import com.SoojalGupta.jobPortal.model.Application;
import com.SoojalGupta.jobPortal.model.Company;
import com.SoojalGupta.jobPortal.model.Job;
import com.SoojalGupta.jobPortal.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
	 boolean existsByUserAndJob(User user, Job job);
	 List<Application> findByUser(User user);
	 List<Application> findByJobCompany(Company company); 
	 List<Application> findByJob(Job job);  // company use कर सकती है
	 List<Application> findByUserId(Long userId);
	 List<Application> findByJobIdAndJobCompany(Long jobId, Company company);
 
}
