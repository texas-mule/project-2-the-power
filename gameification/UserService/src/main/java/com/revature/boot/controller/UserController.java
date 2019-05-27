package com.revature.boot.controller;

import com.revature.boot.domain.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	public User getUser(@PathVariable Long id){
		return userService.getUserById(id);
	}
	
	@GetMapping("/getUserByName/{username}")
	public User getUser(@PathVariable String username){
		return userService.getUserByName(username);
	}
	
	@PostMapping(path = "/newUser", consumes = "application/json", produces = "application/json")
	public User addNewUser(@RequestBody @Valid User user, Errors errors) {
		if(errors.hasErrors()) return null;
    
		return userService.saveNewUser(user);

	}
	
	@PostMapping(path = "/newUserCreation", consumes = "application/json", produces = "application/json")
	public User addNewUser(@RequestBody @Valid String credentials, Errors errors) {
		if(errors.hasErrors()) return null;
		String username;
		String password;
		String[] parsedValues = credentials.split(":|,|\"|username|password|\\{|\\}|\\t|\\s+");
		ArrayList<String> values = new ArrayList<String>();
		for(String arrayElement: parsedValues){
			if(arrayElement != null && !arrayElement.equals(""))
				values.add(arrayElement);
		}
		
 		User user = new User(values.get(0), values.get(1));
		return userService.saveNewUserUnamePword(user);
	}
	
	@DeleteMapping("/deleteById/{id}")
	public String deleteById(@PathVariable("id") Long id) {
		userService.deleteById(id);
		return "deleted!";
	}

	/*User Funds*/
	@GetMapping("/addFunds/{id}/{amount}")
	public User addFunds(@PathVariable Long id, @PathVariable double amount){
		System.out.println("Amount: "+amount);
		User user = userService.getUserById(id);
		user.addFunds(amount);
		return userService.saveUser(user);
	}
	/* End of User Funds*/
	
	/*PORTFOLIO*/
	
	/*END OF PORTFOLIO*/
	@RequestMapping(value="/{id}/portfolio", method = RequestMethod.POST)
	public void addPortfolio(@PathVariable Long id, @RequestParam(value="ticker") String[] tickers, @RequestParam(value="amount") int[] amounts){
		for(String ticker: tickers ){
			System.out.println("Ticker: "+ticker);
		}
		for(int amount: amounts ){
			System.out.println("Amount: "+amount);
		}
		
	}
	
	/*CUSTOM INDEXES*/
	
	//Gets users custom index
	@GetMapping("/{id}/getCustomIndexes")
	public String getCustomIndex(@PathVariable Long id){
		User user = userService.getUserById(id);
		String returnString = userService.getUserCustomIndexById(id);
		if(returnString.equals(null) || returnString.equals("")){
			return "NULL";
		}else{
			return user.returnCustomIndexes();
		}
	}
	
	//Add a custom index
	@RequestMapping(value="/{id}/addindex", method = RequestMethod.POST)
	public User addIndex(@PathVariable Long id, @RequestParam(value="stock") String[] stocks){
		System.out.println("ID: "+id);
		User user = userService.getUserById(id);
		ArrayList<StockIndex> stockIndexList = new ArrayList<StockIndex>();
		StockIndex newStockIndex;
		for(String stock: stocks){
			newStockIndex = new StockIndex(stock);
			stockIndexList.add(newStockIndex);
			System.out.println(newStockIndex.returnIndex());
		}
		
		CustomIndex newCustomIndex = new CustomIndex(stockIndexList);
		user.addCustomIndexes(newCustomIndex.returnCustomIndex());
		System.out.println(user.returnCustomIndexes());
		return userService.updateUser(user);
	}
	/*END OF CUSTOM INDEXES*/
	
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
