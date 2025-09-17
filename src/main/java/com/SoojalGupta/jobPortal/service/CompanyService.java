package com.SoojalGupta.jobPortal.service;

import com.SoojalGupta.jobPortal.model.Company;
import com.SoojalGupta.jobPortal.repositry.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company getCompanyByEmail(String email) {
        return companyRepository.findByEmail(email);
    }
    public Company loginValidation(String companyEmail,String companypassword)
    {
    	return companyRepository.findByEmailAndPassword(companyEmail, companypassword);
    }

    public void updateCompany(Company updatedCompany, String email) {
        Company existingCompany = companyRepository.findByEmail(email);

        if (existingCompany != null) {
            existingCompany.setCompanyName(updatedCompany.getCompanyName());
            existingCompany.setAddress(updatedCompany.getAddress());
            existingCompany.setIndustry(updatedCompany.getIndustry());

            // अगर पासवर्ड change करने देना है
            if (updatedCompany.getPassword() != null && !updatedCompany.getPassword().isEmpty()) {
                existingCompany.setPassword(updatedCompany.getPassword());
            }

            companyRepository.save(existingCompany);
        }
    }
}
