package com.revature.powerstock.functions.stockinfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.PathVariable;

import com.project2.stockConsumerResources.Stock;

public class StockInformation {
	public String ListStockInfo(String stock, int days)
	   {
		
		   try {
			   
			String stockUrl = "https://financialmodelingprep.com/api/v3/historical-price-full/"+stock+"?timeseries=" + days;	
			String stockData = Stock.sendGet(stockUrl);
			JSONParser parser = new JSONParser();
			JSONObject stockJson = (JSONObject) parser.parse(stockData);
			JSONArray details = (JSONArray) stockJson.get("historical");
			
			List<String> stockInfo = new ArrayList<>();
			
			HashMap<String, String> stockKeyValue = new HashMap<String, String>();
			
			ArrayList<Double> stockHighData = new ArrayList<Double>();
			ArrayList<Double> stockLowData = new ArrayList<Double>();
			ArrayList<Double> stockChangeData = new ArrayList<Double>();
		
			details.forEach( timeseries ->
			{
				JSONObject parse = (JSONObject) timeseries;
				
				for(int i = 0; i<parse.size(); i++)
				{
					String key = parse.keySet().toArray()[i].toString();
					
					stockKeyValue.put(key, parse.get(key).toString());
					stockInfo.add(i, parse.get(key).toString());
				}

				stockHighData.add(Double.parseDouble(parse.get("high").toString()));
				stockLowData.add(Double.parseDouble(parse.get("low").toString()));
				stockChangeData.add(Double.parseDouble(parse.get("changeOverTime").toString()));

			});
			
			double sumHigh = 0;
			double avgHigh = 0;
			
			for(int i = 0; i<stockHighData.size();i++) {
				sumHigh = sumHigh + stockHighData.get(i);
				avgHigh = sumHigh/ stockHighData.size();
			}
			
			double sumLow = 0;
			double avgLow = 0;
			
			for(int i = 0; i<stockLowData.size();i++) {
				sumLow = sumLow + stockLowData.get(i);
				avgLow = sumLow / stockLowData.size();
			}
			
			double sumChange = 0;
			double avgChange = 0;
			
			for(int i = 0; i<stockChangeData.size();i++) {
				sumChange = sumChange + stockChangeData.get(i);
				avgChange = sumChange / stockChangeData.size();
			}
			
			DecimalFormat df = new DecimalFormat("####0.00");
			
			System.out.println("\n");
			System.out.println("average daily high over "+days+" days: " + (avgHigh));
			System.out.println("average daily low over "+days+" days: "+ (avgLow));
			System.out.println("average change over "+days+" days: "+ (avgChange) + "%");
			System.out.println(stockKeyValue.toString());
			
			return "<h1> This is the stock info for " + stock.toUpperCase() + "</h1>\n"
			 +" <br>"
			 +" Average High over " + days + " days : " + df.format(avgHigh)+" <br>" 
			 +" Average Low over " + days + " days : " + df.format(avgLow)  +" <br>"
			 +" Average Change over " + days + " days : " + df.format(avgChange) + " %"+" <br>";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
		   
		   
	   }
}
