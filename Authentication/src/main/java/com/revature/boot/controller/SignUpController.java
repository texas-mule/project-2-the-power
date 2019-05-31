package com.revature.boot.controller;

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
@RequestMapping("/signup")
public class SignUpController {
	@Autowired
	UserService userService;
	@GetMapping
	public Mykeys getAll() {
		Mykeys jsonRet=new Mykeys("please add your username and password to the url seperated by '/'");
		
		return jsonRet;
	}
	@GetMapping("/{username}/{password}")
	public Mykeys auth(@PathVariable("username") String username, @PathVariable("password") String password){
		User user =new User();
		user.setName(username);
		user.setPassword(password);
		int aNumber = 0; 
        aNumber = (int)((Math.random() * 9000000)+1000000); 
		user.setApikey(Integer.toString(aNumber)+"aabb32");
		userService.saveNewArtist(user);
		Mykeys jsonRet=new Mykeys(user.getApikey());
		return jsonRet;
	}
}
