package com.jobPortal.repositry;

 
import com.jobPortal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // आप चाहें तो custom query भी बना सकते हैं
    User findByUserEmail(String email);

	User findByUserEmailAndUserPassword(String email, String password);
}
