package com.revature.powerstock.functions.customindex;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.PathVariable;

import com.project2.stockConsumerResources.Stock;

public class DailyStockMovement {

	public double ListDailyMovement(@PathVariable String stock, @PathVariable int days)
	   {
		   try {
			   
			    // Daily Movement Parser
			   		JSONParser dmParser = new JSONParser();
			   
			   // Gather Movement Data for Stock
					String stockUrl = "https://financialmodelingprep.com/api/v3/historical-price-full/"+stock+"?timeseries=" + days;	
					String stockData = Stock.sendGet(stockUrl);
					JSONObject stockJson = (JSONObject) dmParser.parse(stockData);
					JSONArray details = (JSONArray) stockJson.get("historical");
				
				
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
					change.add(Double.parseDouble(parse.get("changeOverTime").toString()));
				});
				
				double sum = 0;
				double avg = 0;
				
				for(int i = 0; i<change.size();i++) {
					sum = sum + change.get(i);
					avg = sum/change.size();
				}
				DecimalFormat df = new DecimalFormat("####0.00");
				
				System.out.println("\n");
				System.out.println("Average Daily Change over "+days+" Days: " + (df.format(avg)));
				
				return (avg);
					
		   	} catch (Exception e) {
		   		e.printStackTrace();
		   		return -999;
			}
		   
			   
		   
	   }
}
