package com.revature.boot.service;

import java.util.List;
import java.util.Optional;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.boot.domain.User;
import org.springframework.data.repository.CrudRepository;
import com.revature.boot.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	

	public User getUserById(Long id){
		System.out.println("ID OF LOOKUP");
		System.out.println("ID: "+id);
		Optional<User> user =  this.userRepository.findById(id);
		if(user == null){
			User myUser = new User();
			return myUser;
		}
		User myUser = user.get();
		return myUser;
	}
	
	public User getUserByName(String username){
		System.out.println("username OF LOOKUP");
		System.out.println("username: "+username);
		return this.userRepository.findByUsername(username);
	}
	
	public void deleteUserByName(String username){
		System.out.println("username OF LOOKUP");
		System.out.println("username: "+username);
		User user = userRepository.findByUsername(username);
		this.userRepository.delete(user);
	}
	
	public String getUserCustomIndexByName(String username){
		User user = this.userRepository.findByUsername(username);
		if(user != null){
			return user.getCustomIndex();
		}
		return "NULL USER";
	}
	
	@Transactional
	public List<User> getAllUsers() {
		return (List<User>) this.userRepository.findAll();
	}

	@Transactional
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	@Transactional
	public User saveNewUser(User user) {
		return userRepository.save(user);
	}
	
	@Transactional
	public User saveNewUserUnamePword(User user) {
		return userRepository.save(user);
	}
	
	public User updateUser(User user){
		System.out.println("USER ID BEING SAVED: "+user.getId());
		return userRepository.save(user);
	}
	
	@Transactional
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

}
