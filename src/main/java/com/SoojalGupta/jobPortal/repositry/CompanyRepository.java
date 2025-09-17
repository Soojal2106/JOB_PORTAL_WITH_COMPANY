package com.SoojalGupta.jobPortal.repositry;

 
import com.SoojalGupta.jobPortal.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByEmail(String email);

	Company findByEmailAndPassword(String companyEmail, String companyPassword);
}

