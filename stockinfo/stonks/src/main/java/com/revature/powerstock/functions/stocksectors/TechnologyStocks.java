package com.revature.powerstock.functions.stocksectors;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.project2.stockConsumerResources.Stock;

public class TechnologyStocks {

	// Create An ArrayList to Hold Stock Names
				ArrayList<String> stockSymbols = new ArrayList<String>();
				
			// Create An ArrayList to Hold Stock Names
				ArrayList<String> stockNames = new ArrayList<String>();
				
			// Create An ArrayList to Hold Stock Names
				ArrayList<String> sector = new ArrayList<String>();
				
			// Create A HashMap
				HashMap<String, String> sectorData = new HashMap<String, String>();
			
		public HashMap<String, String> getStocks() {
			// Technology
			try {
				
				// JSON Parser
					JSONParser stockParser = new JSONParser();
				
				// URL
					String techURL = "https://cloud.iexapis.com/stable/stock/market/collection/sector?collectionName=Technology&token=sk_77ef8dc8d96c43f7ae4624d7ea5b228e";	
				
				// URL DATA -> String
					String techDataString = Stock.sendGet(techURL);
				
				// Technology Sector Data
					JSONArray techData = (JSONArray) stockParser.parse(techDataString);
				
				
				// While Loop Iteration
					int stockNumber = 0;
					

				// Gather 10 stockNames from TechSector
					while(stockNumber<10)
					{
						String stockName;
						String stockSymbol;
						try {
							JSONObject stock = (JSONObject) techData.get(stockNumber);
							stockSymbol = (String) stock.get("symbol");
							stockName = (String) stock.get("companyName");
							
							stockSymbols.add(stockSymbol);
							stockNames.add(stockName);
							
							sectorData.put(stockSymbol, stockName);
							
							stockNumber++;		
						
						} catch (Exception e) {
							stockNumber++;
						}	
					}
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sector.add("<h1> This is a list of Technology tickers</h1>\n");
			sector.add(" "+stockSymbols.get(0) + " - " + stockNames.get(0) +" <br>");
			sector.add(" "+stockSymbols.get(1) + " - " + stockNames.get(1) +" <br>");
			sector.add(" "+stockSymbols.get(2) + " - " + stockNames.get(2) +" <br>");
			sector.add(" "+stockSymbols.get(3) + " - " + stockNames.get(3) +" <br>");
			sector.add(" "+stockSymbols.get(4) + " - " + stockNames.get(4) +" <br>");
			sector.add(" "+stockSymbols.get(5) + " - " + stockNames.get(5) +" <br>");
			sector.add(" "+stockSymbols.get(6) + " - " + stockNames.get(6) +" <br>");
			sector.add(" "+stockSymbols.get(7) + " - " + stockNames.get(7) +" <br>");
			sector.add(" "+stockSymbols.get(8) + " - " + stockNames.get(8) +" <br>");
			sector.add(" "+stockSymbols.get(9) + " - " + stockNames.get(9) +" <br>");

			
			return sectorData;

		}
}
		
