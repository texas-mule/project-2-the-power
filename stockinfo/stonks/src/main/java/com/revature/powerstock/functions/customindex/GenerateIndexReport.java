package com.revature.powerstock.functions.customindex;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.PathVariable;

public class GenerateIndexReport {
	public double GenerateReport(@PathVariable int timeframe, @PathVariable String username) {
		   
		   // Testing Purposes
		   /******************************************/
		   
			   String Test = "{ \"index\" : ["
			   		+ "{ \"stock\": \"AAPL\","
			   		+ "	\"amount\": 20}, "
			   		+ "{ \"stock\": \"WMT\","
			   		+ "	\"amount\": 20}, "
			   		+ "] }";
			   
			/******************************************/
		   
		  // Generate Report Parser
			   JSONParser reportParser = new JSONParser();
		   
		   
			   try {
			   
		  // Uncomment for running
				/******************************************/
				    /*
				   	String UserUrl = "https://localhost:7070/users/" + username + "/index";	
					String stockData = Stock.sendGet(stockUrl);
					JSONParser parser = new JSONParser();
					JSONObject json = (JSONObject) parser.parse(stockData);
					*/
				 /******************************************/
			   
		  // Testing Purpose
				   /******************************************/
				   JSONObject json = (JSONObject) reportParser.parse(Test); // replace this with above comment when production
				   /******************************************/
				   
		 //	 Get Index
				   JSONArray details = (JSONArray) json.get("index");
			
		// Create Hashmap
			HashMap<String, String> stockKeyValue = new HashMap<String, String>();
			
			ArrayList<Double> change = new ArrayList<Double>();
			
			details.forEach( timeseries ->
			{
				JSONObject parse = (JSONObject) timeseries;
				
				for(int i = 0; i<parse.size(); i++)
				{
					String key = parse.keySet().toArray()[i].toString();
					stockKeyValue.put(key, parse.get(key).toString());
					
				}
				DailyStockMovement stockMovement = new DailyStockMovement();
				change.add(stockMovement.ListDailyMovement(stockKeyValue.get("stock"),timeframe));	
				System.out.println(stockKeyValue.toString());

			});
			
			double sum = 0;
			for(int i = 0; i <change.size();i++)
				sum = sum + change.get(i);
			
			return sum;

			

	   	} catch (Exception e) {

			return -999;
		}
}
}
