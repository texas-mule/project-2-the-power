package com.revature.boot.service;

import java.util.List;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.boot.domain.User;

import com.revature.boot.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public void setArtistRepository(UserRepository artistRepository) {
		this.userRepository = artistRepository;
	}
	@Transactional
	public Iterable<User> getAllArtists() {
		return this.userRepository.findAll();
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
