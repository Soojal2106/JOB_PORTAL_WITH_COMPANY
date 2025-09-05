package com.jobPortal.repositry;
 
import com.jobPortal.model.Application;
import com.jobPortal.model.Company;
import com.jobPortal.model.Job;
import com.jobPortal.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
	 boolean existsByUserAndJob(User user, Job job);
	 List<Application> findByUser(User user);
	 List<Application> findByJobCompany(Company company); 
}
