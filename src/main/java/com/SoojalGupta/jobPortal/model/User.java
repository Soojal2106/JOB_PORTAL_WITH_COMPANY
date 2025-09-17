package com.SoojalGupta.jobPortal.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String userId; // e.g., U123

  @Column(nullable = false)
  private String userName;

  @Column(nullable = false, unique = true)
  private String userEmail;

  private String userPassword; // BCrypt store recommended

  private String UserPhone;
  private String UserCity;
  private String userState;
  private String userCountry;
  private String userEducation;
  private String userExperince;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Application> applications;


public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public String getUserEmail() {
	return userEmail;
}

public void setUserEmail(String userEmail) {
	this.userEmail = userEmail;
}

public String getUserPassword() {
	return userPassword;
}

public void setUserPassword(String userPassword) {
	this.userPassword = userPassword;
}

public String getUserPhone() {
	
	return UserPhone;
}

public void setUserPhone(String userPhone) {
	UserPhone = userPhone;
}

public String getUserCity() {
	return UserCity;
}

public void setUserCity(String userCity) {
	UserCity = userCity;
}

public String getUserState() {
	return userState;
}

public void setUserState(String userState) {
	this.userState = userState;
}

public String getUserCountry() {
	return userCountry;
}

public void setUserCountry(String userCountry) {
	this.userCountry = userCountry;
}

public String getUserEducation() {
	return userEducation;
}

public void setUserEducation(String userEducation) {
	this.userEducation = userEducation;
}

public String getUserExperince() {
	return userExperince;
}

public void setUserExperince(String userExperince) {
	this.userExperince = userExperince;
}

public List<Application> getApplications() {
	return applications;
}

public void setApplications(List<Application> applications) {
	this.applications = applications;
}
  

  // getters/setters
  
  
}
