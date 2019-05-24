package com.revature.boot.service;

import java.util.List;


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

	public void setArtistRepository(UserRepository usertRepository) {
		this.userRepository = userRepository;
	}

	
	@Transactional
	public List<User> getAllUsers() {
		return (List<User>) this.userRepository.findAll();
	}
	
	@Transactional
	public User saveNewArtist(User user) {
		return userRepository.save(user);
	}
	
	@Transactional
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

}
