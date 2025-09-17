package com.SoojalGupta.jobPortal.service;

 

import com.SoojalGupta.jobPortal.model.Application;
import com.SoojalGupta.jobPortal.model.Company;
import com.SoojalGupta.jobPortal.model.Job;
import com.SoojalGupta.jobPortal.model.User;
import com.SoojalGupta.jobPortal.repositry.ApplicationRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public void applyJob(User user, Job job) {
        Application application = new Application();
        application.setUser(user);
        application.setJob(job);
        applicationRepository.save(application);
    }

	public boolean ApplyExistsByUserAndJob(User user, Job job) {
		// TODO Auto-generated method stub
		return applicationRepository.existsByUserAndJob(user,job);
	}

	public List<Application> getApplicationsByUser(User user) {
		// TODO Auto-generated method stub
		return applicationRepository.findByUser(user);
	}
	
	  public List<Application> getApplicationsByCompany(Company company) {
	        return applicationRepository.findByJobCompany(company);
	    }
	  public List<Application> getApplicationsByJob_id(long id,Company company) {
	        return applicationRepository.findByJobIdAndJobCompany(id,company);
	    }

	public List<Application> findByUser(User user) {
		// TODO Auto-generated method stub
		return applicationRepository.findByUser(user);
	}
}
