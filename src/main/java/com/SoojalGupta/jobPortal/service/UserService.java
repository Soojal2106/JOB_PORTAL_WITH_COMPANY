package com.SoojalGupta.jobPortal.service;
import com.SoojalGupta.jobPortal.model.User;
import com.SoojalGupta.jobPortal.repositry.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user); // Save in MySQL
    }

	public User loginValidation(String email, String password) {
		// TODO Auto-generated method stub
		return userRepository.findByUserEmailAndUserPassword(email,password);
	}

	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByUserEmail(email);
	}

	public void updateUser(User user, String email) {
	    User existingUser = userRepository.findByUserEmail(email); // userEmail field

	    if (existingUser != null) {
	        existingUser.setUserName(user.getUserName());
	        existingUser.setUserEducation(user.getUserEducation());
	        existingUser.setUserExperince(user.getUserExperince());
	        existingUser.setUserPhone(user.getUserPhone());
	        existingUser.setUserCity(user.getUserCity());
	        existingUser.setUserState(user.getUserState());
	        existingUser.setUserCountry(user.getUserCountry());
	        existingUser.setUserEducation(user.getUserEducation());
	        existingUser.setUserId(user.getUserId());
 
	        if (user.getUserPassword() != null && !user.getUserPassword().isEmpty()) {
	            existingUser.setUserPassword(user.getUserPassword());
	        }

	        userRepository.save(existingUser);
	    } else {
	        throw new RuntimeException("User not found with email: " + email);
	    }
	}



}
