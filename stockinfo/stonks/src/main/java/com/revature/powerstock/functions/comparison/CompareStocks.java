package com.revature.powerstock.functions.comparison;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.project2.stockConsumerResources.Stock;

public class CompareStocks {
	
	public HashMap<String, Double> getStockComparison(String stock1, String stock2) throws Exception {
		// Comparison Parser
		   		JSONParser comParser = new JSONParser();
		   		
		   	// Create An ArrayList to Hold Comparison Data
		   		ArrayList<String> compData = new ArrayList<String>();
		   		
		   	// Create A String to Display Comparison Data
		   		String comparisonData = new String();
		   
		   // First Stock 
		   		String firstStockURL = "https://financialmodelingprep.com/api/company/price/"+stock1+"?datatype=json";
		   		String firstStockData;
				firstStockData = Stock.sendGet(firstStockURL);

				
		   // Second Stock URL
		   		String secondStockURL = "https://financialmodelingprep.com/api/company/price/"+stock2+"?datatype=json";
		   		String secondStockData;
		   		secondStockData = Stock.sendGet(secondStockURL);

		   		HashMap<String, Double> compHashData = new HashMap<String, Double>();

				// Compare Stock Data
				try 
				{
					
					JSONObject firstStockJson = (JSONObject) comParser.parse(firstStockData);
					JSONObject secondStockJson = (JSONObject) comParser.parse(secondStockData);
					
					double firstStockPrice = Double.parseDouble(firstStockJson.get(stock1).toString().split(":")[1].split("}")[0]);
					double secondStockPrice = Double.parseDouble(secondStockJson.get(stock2).toString().split(":")[1].split("}")[0]);
					
					
					compData.add(" with the values <br>" );
					compData.add(firstStockPrice+ " and " + secondStockPrice + "<br>");
					compData.add("A share of "+ stock1 + " is worth " 
							   + (firstStockPrice/secondStockPrice)
							   +" shares of " + stock2);
					
					double ratio = (firstStockPrice/secondStockPrice);
					
					DecimalFormat df = new DecimalFormat();
					
					compHashData.put(stock1, firstStockPrice);
					compHashData.put(stock2, secondStockPrice);
					compHashData.put("ratio", ratio);
					
					comparisonData =  " <h1> This is a Daily Comparison of "  + stock1.toUpperCase() + " vs. " + stock2.toUpperCase() + "</h1>"
									+ " \nwith the values <br>"
									+ stock1.toUpperCase() + " : " + firstStockPrice + "<br>"
									+ stock2.toUpperCase() + " : " + secondStockPrice + "<br>"
									+ "<br>" 
									+ "A share of "+ stock1.toUpperCase() + " is worth " + (df.format(ratio))
									+ " shares of " + stock2.toUpperCase();
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return compHashData;
	}
}
