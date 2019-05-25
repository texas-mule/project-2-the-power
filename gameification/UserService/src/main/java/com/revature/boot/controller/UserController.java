package com.revature.boot.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.boot.domain.User;
import com.revature.boot.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired

	UserService userService;
	
	@GetMapping("/")
	public String sayHello() {
		return "Welcome to user route";

	}
	
	@GetMapping("/getUser/{id}")
	public Optional<User> getUser(@PathVariable Long id){
		return userService.getUser(id);
	}
	
	@GetMapping("/getUserByName/{username}")
	public User getUser(@PathVariable String username){
		return userService.getUserByName(username);
	}
	
	@PostMapping(path = "/newUser", consumes = "application/json", produces = "application/json")
	public User addNewUser(@RequestBody @Valid User user, Errors errors) {
		if(errors.hasErrors()) return null;
    
		return userService.saveNewArtist(user);

	}
	
	@DeleteMapping("/deleteById/{id}")
	public String deleteById(@PathVariable("id") Long id) {

		userService.deleteById(id);

		return "deleted!";
	}
	
	@GetMapping("/oops")
	public void oops() throws IOException {
		throw new IOException();
	}
	
	@ExceptionHandler(IOException.class)
	@ResponseStatus(value=HttpStatus.I_AM_A_TEAPOT)
	public String ioProblem() {
		return "oops!";
	}
}
