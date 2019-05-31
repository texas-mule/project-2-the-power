package com.revature.boot.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.boot.domain.User;
import com.revature.boot.service.UserService;

@RestController
@RequestMapping("/auth")
public class ArtistController {
	@Autowired
	UserService artistService;
	
	@GetMapping
	public Mykeys getAll() {
		Mykeys jsonRet=new Mykeys("welcome to our api, please add your key to the url to access our sources");
		
		return jsonRet;
	}
	@GetMapping("/{key}")
	public Mykeys auth(@PathVariable("key") String key){
		boolean flag=false;
		
		ArrayList<User> users=(ArrayList<User>) artistService.getAllArtists();
		for(User u:users){
			if(u.getApikey().equals(key)){
				flag=true;
			}
			
		}
		if(flag==true){
			Mykeys s=new Mykeys("Welcome to our api");
			return s;
		}
		else{
			Mykeys s=new Mykeys("please enter a valid api key");
			return s;
		}
	}
	

	
}
