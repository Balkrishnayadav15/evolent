package com.evolenthealth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evolenthealth.exception.ResourceNotFoundException;
import com.evolenthealth.model.User;
import com.evolenthealth.repository.UserDetailsRepository;

@RestController
@RequestMapping("/api/v1")
public class UserDetailsController {

	@Autowired
	UserDetailsRepository userRepository;
	
	@GetMapping("/user")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) throws ResourceNotFoundException{
		
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id:"+id));
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping("/user")
	public User createUser(@RequestBody User user) {
		
		return userRepository.save(user);
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value="id") Long id, @RequestBody User userDetails) throws ResourceNotFoundException{
		
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id:"+id));
		
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setEmailId(userDetails.getEmailId());
		user.setMobileNumber(userDetails.getMobileNumber());
		
		User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}

	
	@DeleteMapping("/user/{id}")
	public Map<String,Boolean> deleteUser(@PathVariable(value="id") Long id) throws ResourceNotFoundException{
		
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id:"+id));
		
		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
		
	}
	
}
