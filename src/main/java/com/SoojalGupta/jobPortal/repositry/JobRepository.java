package com.SoojalGupta.jobPortal.repositry;
import com.SoojalGupta.jobPortal.model.Company;
import com.SoojalGupta.jobPortal.model.Job;
import com.SoojalGupta.jobPortal.model.JobStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findAllByOrderByIdDesc(); // Latest jobs first
    List<Job> findByCompany(Company company);
//    @Query("SELECT j FROM Job j WHERE j.postedAt >= :cutoffDate AND j.jobStatus = :status")
//	List<Job> findJobsPostedAfterAndJobStatus(LocalDateTime cutoffDate,JobStatus enable);
//  
    List<Job> findByPostedAtAfterAndJobStatus(LocalDateTime cutoffDate, JobStatus status);

    List<Job> findByTitleContainingIgnoreCase(String keyword);
	List<Job> findByJobStatus(JobStatus enable);
}
