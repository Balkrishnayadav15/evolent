package com.evolenthealth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
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

/**
 * @author Balkrishna Yadav
 * This is a rest controller class which provides API's to manager user information. 
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class UserDetailsController {
	
	Logger log = LoggerFactory.getLogger(UserDetailsController.class);
	
	@Autowired
	UserDetailsRepository userRepository;
	
	
	/**
	 * @return - It will return all user with their details
	 */
	@GetMapping("/user")
	public List<User> getAllUsers(){
		log.info("Fetching all users");
		return userRepository.findAll();
	}
	
	/**
	 * @param - It accept user id 
	 * @return - It will return user detail for particular user id.
	 */
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) throws ResourceNotFoundException{
		log.info("Calling respository to fetch user informaiton for user id :"+id);
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id:"+id));
		log.info("Returing user informaiton for user id :"+id);
		return ResponseEntity.ok().body(user);
	}
	
	/**
	 * @param - It accept user id 
	 * @return - It will return user detail after inserting it into database.
	 */
	@PostMapping("/user")
	public User createUser(@RequestBody User user) throws ResourceNotFoundException {
		
		log.info("Calling respository to insert user into database");
		User savedUser = null;
		User duplicateUser = userRepository.findByEmailId(user.getEmailId());
		if(duplicateUser != null) {
			log.info("Email id "+user.getEmailId()+" is already exists.");
			throw new ResourceNotFoundException("Email id "+user.getEmailId()+" is already exists.");
		}else {
			log.info("User with user id :"+user.getId()+" added successfully");
			savedUser = userRepository.save(user);
		}
		return savedUser;
	}
	
	/**
	 * @param - It accept user id  and used detail
	 * @return - It will return user detail after updating it into database.
	 */
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value="id") Long id, @RequestBody User userDetails) throws ResourceNotFoundException{
		
		log.info("Before update, checking if user is present into database or not for id:"+id);
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id:"+id));
		
		User duplicateUser = userRepository.findByEmailId(userDetails.getEmailId());
		if(duplicateUser != null && duplicateUser.getId() != id) {
			log.info("Email id \"+userDetails.getEmailId()+\" is already exists for id:"+id);
			throw new ResourceNotFoundException("Email id "+userDetails.getEmailId()+" is already exists.");
		}
		
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setEmailId(userDetails.getEmailId());
		user.setMobileNumber(userDetails.getMobileNumber());
		user.setStatus(userDetails.getStatus());
		User updatedUser = userRepository.save(user);
		log.info("User is updated successfully into database for id:"+user.getId());
		return ResponseEntity.ok(updatedUser);
	}

	/**
	 * @param - It accept user id 
	 * @return - It will return true if user deleted or throws exception if used id is not matched in table
	 */
	@DeleteMapping("/user/{id}")
	public Map<String,Boolean> deleteUser(@PathVariable(value="id") Long id) throws ResourceNotFoundException{
		log.info("Before delete, checking if user is present into database or not for id:"+id);
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id:"+id));
		
		userRepository.delete(user);
		log.info("User is deleted successfully into database for id:"+user.getId());
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
		
	}
	
	/**
	 * 
	 * @return - Default routing to index.html page.
	 */
	 @GetMapping("/")
     public String home(Model model) {
            return "forward:/index.html";
     }
	
}
