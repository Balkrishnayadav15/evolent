package com.evolenthealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evolenthealth.model.User;

@Repository
public interface UserDetailsRepository extends JpaRepository<User,Long> {

	public User findByEmailId(String emailId);
}
