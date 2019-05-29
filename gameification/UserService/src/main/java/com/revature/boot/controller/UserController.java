package com.revature.boot.controller;

import com.revature.boot.domain.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;

//import org.json.JSONObject;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.boot.domain.User;
import com.revature.boot.service.UserService;

import java.io.DataOutputStream;
import java.io.InputStreamReader;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/")
	public String sayHello() {
		return "Welcome to user route";

	}

	@GetMapping("/{username}")
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

	@DeleteMapping("/deleteById/{username}")
	public String deleteById(@PathVariable("username") String username) {
		userService.deleteUserByName(username);
		return "deleted!";
	}

	/*User Funds*/
	@GetMapping("/{username}/addFunds/{amount}")
	public User addFunds(@PathVariable String username, @PathVariable double amount){
		System.out.println("Amount: "+amount);
		User user = userService.getUserByName(username);
		user.addFunds(amount);
		return userService.saveUser(user);
	}
	/* End of User Funds*/

	/*PORTFOLIO*/
	@RequestMapping(value="/{username}/portfolio", method = RequestMethod.POST)
	public String addPortfolio(@PathVariable String username, @RequestParam(value="ticker") String[] tickers, @RequestParam(value="amount") int[] amounts){
		User user = userService.getUserByName(username);
		System.out.println("USER PORTFOLIO VALUE: "+user.getPortfolioValue());
		if(tickers.length != amounts.length){
			return "Invalid entry please try again";
		}else if(user.getPortfolioValue().length() != 0|| !user.getPortfolioValue().equals("") || user.getPortfolioValue().equals("null") || user.getPortfolioValue().equals(null)){
			return "User already has an index";
		}else{
			ArrayList<PortfolioIndex> portfolioIndexes = new ArrayList<PortfolioIndex>();
			PortfolioIndex index;
			for(int i=0; i<tickers.length; i++ ){
				index = new PortfolioIndex(tickers[i],amounts[i]);
				user.addCustomPortfolioIndexes(index.returnIndex());
			}
			userService.saveUser(user);
			return "Succesfully added portfolio please go to user account to see";
		}
	}

	@RequestMapping(value="/{username}/portfolio", method = RequestMethod.GET)
	public String addPortfolio(@PathVariable String username){
		User user = userService.getUserByName(username);
		return user.getPortfolio();
	}


	/*END OF PORTFOLIO*/

	/*CUSTOM INDEXES*/

	//Gets users custom index
	@GetMapping("/{username}/index")
	public String getCustomIndex(@PathVariable String username){
		User user = userService.getUserByName(username);
		String returnString = userService.getUserCustomIndexByName(username);
		if(returnString.equals("NULL USER") || returnString.equals("")){
			return "NULL USER";
		}else{
			return user.returnCustomIndex();
		}
	}

	//Add a custom index
	@RequestMapping(value="/{username}/index", method = RequestMethod.POST)
	public User addIndex(@PathVariable String username, @RequestParam(value="stock") String[] stocks){
		User user = userService.getUserByName(username);
		StockIndex newStockIndex;
		for(String stock: stocks){
			newStockIndex = new StockIndex(stock);
			user.addCustomIndex(newStockIndex.returnIndex());
		}
		return userService.updateUser(user);
	}
	/*END OF CUSTOM INDEXES*/

	/*STOCK MANIPULATION*/
	@SuppressWarnings({ "unchecked", "finally" })
	@RequestMapping(value="/{username}/buy", method = RequestMethod.POST)
	public String buyStock(@PathVariable String username, @RequestParam(value="stock") String stock, @RequestParam(value="shares") int shares ){
		User user = userService.getUserByName(username);
		String portfolio = user.getPortfolio();
		System.out.println(portfolio);
		String stockToBuy = stock;
		int amountToBuy = shares;

		JSONParser parser = new JSONParser();
		try {
			JSONObject json = (JSONObject) parser.parse(portfolio);
			JSONArray details = (JSONArray) json.get("portfolio");
			System.out.println("Details:");
			System.out.println(details.toJSONString());
			System.out.println(json.toString());
			List<String> stockInfo = new ArrayList<>();

			HashMap<String, String> stockKeyValue = new HashMap<String, String>();


			//			double testint[] = {0,0,0,0};

			details.forEach( stockInfoFromPortfolio -> {
				JSONObject parse = (JSONObject) stockInfoFromPortfolio;

				for(int i = 0; i<parse.size(); i++){

					String key = parse.keySet().toArray()[i].toString();
					stockKeyValue.put(key, parse.get(key).toString());
					stockInfo.add(i, parse.get(key).toString());

				}
				System.out.println(stockKeyValue.get("amount"));

				System.out.println(stockKeyValue.toString());

			});
			ArrayList<PortfolioIndex> stocks = new ArrayList<PortfolioIndex>();
			PortfolioIndex index;
			for(int i = 0; i<stockInfo.size()-1;i+=2){
				index = new PortfolioIndex(stockInfo.get(i+1).toString(), Integer.parseInt(stockInfo.get(i)));
				stocks.add(index);
			}

			boolean exists = false;
			int positionIndex = -1;
			int counter = 0;
			for(PortfolioIndex indexes: stocks){
				if(indexes.getTicker().equalsIgnoreCase(stock)){
					exists=true;
					positionIndex = counter;
				}
				counter++;
			}
			System.out.println(counter);
			System.out.println("OK TILL NOW");
			System.out.println("STOCK:"+stock);
			String stockURL = "http://localhost:9090/price/"+stock;
			double responsePrice = Double.parseDouble(this.sendGet(stockURL));
			System.out.println("RESPONSE PRICE:" + responsePrice);
			if(responsePrice == 0.0){
				System.out.println("WHY?");
				return "Stock not found please try again later";
			}else{
				if(user.getFunds()-(responsePrice*shares) < 0.0){
					System.out.println("ALSO WHY?");
					return "Invalid funds for transaction";
				}else{
					System.out.println("price not 0");
					user.removeFunds((responsePrice*shares));
					if(positionIndex != -1){
						System.out.println("counter found");
						//Stock was in portfolio need to save new user with new portfolio
						System.out.println(positionIndex);
						System.out.println(stocks);
						stocks.get(positionIndex).setValue(stocks.get(positionIndex).getValue()+shares);
						System.out.println("VALUE:");
						System.out.println(stocks.get(positionIndex).getValue()+" "+stocks.get(positionIndex).getTicker());
						String newPortfolio = "";
						for(int i=0; i<stocks.size(); i++){
							if(i != stocks.size()-1){
								newPortfolio += stocks.get(i).returnIndex()+",";
							}else{
								newPortfolio += stocks.get(i).returnIndex();
							}
							System.out.print("New Portfolio:");
							System.out.println(newPortfolio);
						}

						//System.out.println(stocks.get(counter).getValue()+" "+stocks.get(counter+1).getValue());
						user.setPortfolio(newPortfolio);
						System.out.println(user.getPortfolioValue());
					}else{
						PortfolioIndex portIndex = new PortfolioIndex(stock,shares);
						user.addCustomPortfolioIndexes(portIndex.returnIndex());
						System.out.println("WE HIT");
						System.out.println(user.getPortfolioValue());
					}

					userService.saveNewUser(user);
				}
			}

			//System.out.println(user.get);
			return "Purchase successful heres your new portfolio:\n"+user.getPortfolio();
		}catch(Exception e){
			return "Exception found!";
		}
	}

	@RequestMapping(value="/{username}/sell", method = RequestMethod.POST)
	public String sellStock(@PathVariable String username, @RequestParam(value="stock") String stock, @RequestParam(value="shares") int shares ){
		User user = userService.getUserByName(username);
		String portfolio = user.getPortfolio();
		System.out.println(portfolio);
		String stockToBuy = stock;
		int amountToBuy = shares;

		JSONParser parser = new JSONParser();
		try {
			JSONObject json = (JSONObject) parser.parse(portfolio);
			JSONArray details = (JSONArray) json.get("portfolio");
			System.out.println("Details:");
			System.out.println(details.toJSONString());
			System.out.println(json.toString());
			List<String> stockInfo = new ArrayList<>();

			HashMap<String, String> stockKeyValue = new HashMap<String, String>();


			//			double testint[] = {0,0,0,0};

			details.forEach( stockInfoFromPortfolio -> {
				JSONObject parse = (JSONObject) stockInfoFromPortfolio;

				for(int i = 0; i<parse.size(); i++){

					String key = parse.keySet().toArray()[i].toString();
					stockKeyValue.put(key, parse.get(key).toString());
					stockInfo.add(i, parse.get(key).toString());

				}
				System.out.println(stockKeyValue.get("amount"));

				System.out.println(stockKeyValue.toString());

			});
			ArrayList<PortfolioIndex> stocks = new ArrayList<PortfolioIndex>();
			PortfolioIndex index;
			for(int i = 0; i<stockInfo.size()-1;i+=2){
				index = new PortfolioIndex(stockInfo.get(i+1).toString(), Integer.parseInt(stockInfo.get(i)));
				stocks.add(index);
			}

			boolean exists = false;
			int positionIndex = -1;
			int counter = 0;
			for(PortfolioIndex indexes: stocks){
				if(indexes.getTicker().equalsIgnoreCase(stock)){
					exists=true;
					positionIndex = counter;
				}
				counter++;
			}
			System.out.println(counter);
			System.out.println("OK TILL NOW");
			System.out.println("STOCK:"+stock);
			String stockURL = "http://localhost:9090/price/"+stock;
			double responsePrice = Double.parseDouble(this.sendGet(stockURL));
			System.out.println("RESPONSE PRICE:" + responsePrice);
			if(responsePrice == 0.0){
				System.out.println("WHY?");
				return "Stock not found please try again later";
			}else{
				if(!exists || stocks.get(positionIndex).getValue()-shares <0){
					System.out.println("ALSO WHY?");
					return "Invalid stocks for transaction";
				}else{
					System.out.println("price not 0");
					user.addFunds((responsePrice*shares));
					if(positionIndex != -1){
						System.out.println("counter found");
						//Stock was in portfolio need to save new user with new portfolio
						System.out.println(positionIndex);
						System.out.println(stocks);
						stocks.get(positionIndex).setValue(stocks.get(positionIndex).getValue()-shares);
						System.out.println("VALUE:");
						System.out.println(stocks.get(positionIndex).getValue()+" "+stocks.get(positionIndex).getTicker());
						String newPortfolio = "";
						for(int i=0; i<stocks.size(); i++){
							if(i != stocks.size()-1){
								newPortfolio += stocks.get(i).returnIndex()+",";
							}else{
								newPortfolio += stocks.get(i).returnIndex();
							}
							System.out.print("New Portfolio:");
							System.out.println(newPortfolio);
						}

						//System.out.println(stocks.get(counter).getValue()+" "+stocks.get(counter+1).getValue());
						user.setPortfolio(newPortfolio);
						System.out.println(user.getPortfolioValue());
					}else{
						PortfolioIndex portIndex = new PortfolioIndex(stock,shares);
						user.addCustomPortfolioIndexes(portIndex.returnIndex());
						System.out.println("WE HIT");
						System.out.println(user.getPortfolioValue());
					}

					userService.saveNewUser(user);
				}
			}

			//System.out.println(user.get);
			return "Sell successful heres your new portfolio:\n"+user.getPortfolio();
		}catch(Exception e){
			return "Exception found!";
		}
	}

	/*END STOCK MANIPULATION*/

	public static String sendGet(String ticker) throws Exception {

		final String USER_AGENT = "Mozilla/5.0";
		String url = ticker;
		System.out.println(url);
		URL obj = new URL(url);
		System.out.println("WORKING");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		System.out.println("S WORKING");
		// optional default is GET
		con.setRequestMethod("GET");
		System.out.println("STILL WORKING");
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		System.out.println("WE OK");
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println("GOOD TO GO");
		//print result
		return response.toString();

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


	@ExceptionHandler(IOException.class)
	@ResponseStatus(value=HttpStatus.FORBIDDEN)
	public String tickerNotValid() {
		return "Problem with a ticker!";
	}

}
