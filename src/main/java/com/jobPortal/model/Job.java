package com.jobPortal.model;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String location;
    private String salary;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    // Job post date & time
    private LocalDateTime postedAt;

    // Constructor, getters, setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public LocalDateTime getPostedAt() { return postedAt; }
    public void setPostedAt(LocalDateTime postedAt) { this.postedAt = postedAt; }

    // Automatically set postedAt before saving
    @PrePersist
    protected void onCreate() {
        this.postedAt = LocalDateTime.now();
    }
}
