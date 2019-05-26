package com.project2.stonkApplication;

import org.springframework.web.bind.annotation.RestController;

import com.project2.stockConsumerResources.Stock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Source;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@RestController
@RequestMapping("/bus")
public class StonkController {
	   
	   @GetMapping("/stocks")
	   	public String ListOfStocks()
	   	{
		   //call db to return table of stocks
		   // format table
		   //return table back as key value pairs
		   
		   return "<h1> This is a list of stocks</h1>\n"
		   +"WMT <br>"
		   + "XOM <br>"
		   + "MCK <br>"
		   +"UNH <br>"
		   +"CVS <br>"
		   +"GM <br>"
		   +"F <br>"
		   +"T <br>"
		   +"GE <br>"
		   +"ABC <br>"
		   +"VZ <br>"
		   +"CVX <br>"
		   +"COST <br>"
		   +"FNMA <br>";
	   	}
	   
	   @GetMapping("/compare/{stock1}/{stock2}")
	   public String DeltaChange(@PathVariable String stock1, @PathVariable String stock2)
	   {
		   String stock1Data = "null";
		   String stock2Data = "null";
		   try {
			stock1Data = Stock.sendGet(stock1);
			stock2Data = Stock.sendGet(stock2);
		} catch (Exception e) {
			return "<h1> Error 500 <h1> " + stock1 + " and " + stock2 + " cannot be fetched "
					+ "due to heavy load on our servers, try again later";
		}
		   
		   return "this is a monthly comparison of " + stock1Data + " and " + stock2Data;
	   
	   }
	   
	   @GetMapping("/hello/{name}")
	   @ResponseBody
	   public String getFooById(@PathVariable String name) {
	       return "hello " + name + " you did it!";
	   }
	   
	   @GetMapping("/customindex/{timeframe}")
	   @ResponseBody
	   public String GenerateReport(@PathVariable String timeframe) // int is placeholder for object
	   {
		   //take stock 1
		   //get shares
		   //get ticker name stock
		   //get value
		   //shares*value
		   // get sums of values to get %
		   // use % and viability of stock to make conglomerated rate
		   
		   return "this is a generated report for " + timeframe + " units";
	   }

	   
	   /*
	    * 

	   @GetMapping("/newindex")
	   public String makeindex()
	   {
		   // get ticker name of stocks
		   // get amount of shares
		   // repeat up to 10 times
		   // save config into db and link to user
		   
		   return "this is a generated new index, figure out how to pass queryparams";
	   }
	   
	   *
	   */
	   
	   
	   
	@SuppressWarnings("unchecked")
	@GetMapping("/stock/{stock}")
	   public String ListStock(@PathVariable String stock)
	   {
		   try {
			   
			String stockUrl = "https://financialmodelingprep.com/api/v3/historical-price-full/"+stock+"?timeseries=1";	
			String stockData = Stock.sendGet(stockUrl);
			
			JSONParser parser = new JSONParser();
			JSONObject stockJson = (JSONObject) parser.parse(stockData);
			JSONArray details = (JSONArray) stockJson.get("historical");
			
			List<String> stockInfo = new ArrayList<>();
			Hashtable<String, Float> stockKeyValue = new Hashtable<String,Float>();
					
			details.forEach
			(o -> 
				{
					String values[] = o.toString().split(","); 
					
					for(int i = 0; i < values.length; i++) // fills up the hashtable with json data
					{
						System.out.println(values[i]);
						stockInfo.add(values[i]);
						
						try {
							stockKeyValue.put(values[i].split(":")[0].replace("\"", ""),
								(float) Double.parseDouble(values[i].split(":")[1].replace("}","")));
						}
						catch(Exception e) {/*ignore date*/}
					}
				}
			);
			
			System.out.println(stockKeyValue.toString());
			return "<h1> Info about "+stock+"</h1>" 
			+ "opened at: " + stockKeyValue.get("open") + "<br>"
			+ "closed at: " + stockKeyValue.get("close") + "<br>"
			+ "changed by: " + (stockKeyValue.get("open") - stockKeyValue.get("close"))
			+ " USD yesterday, a " + 
			((stockKeyValue.get("close") - stockKeyValue.get("open"))/stockKeyValue.get("open") *100)
			+ " percent change <br>"
			+ Stock.Evaluate(((stockKeyValue.get("close") - stockKeyValue.get("open"))/stockKeyValue.get("open") *100));

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   return "this is the data for a share of " + stock;
	   }
	   
	   
	   
	   @GetMapping("/comparemonth/{stock1}/{stock2}")
	   @ResponseBody
	   public String monthlyComparison(@PathVariable String stock1, @PathVariable String stock2)
	   {
		   String stock1Url = "https://financialmodelingprep.com/api/company/price/"+stock1+"?datatype=json";
		   String stock2Url = "https://financialmodelingprep.com/api/company/price/"+stock2+"?datatype=json";
		   String stock1Data = "null";
		   String stock2Data = "null";
		   JSONObject stock1Json = null;
		   JSONObject stock2Json = null;
		   double stock1Price = 0.0;
		   double stock2Price = 0.0;
		   
		   
		try 
		{
			stock1Data = Stock.sendGet(stock1Url);
			stock2Data = Stock.sendGet(stock2Url);
		} 
		catch (Exception e) 
		{
			return "<h1> Error 500 <h1> " + stock1 + " and " + stock2 + " cannot be fetched "
					+ "due to heavy load on our servers, try again later";
		}
		   
		  
		try 
		{
			JSONParser parser = new JSONParser();
			stock1Json = (JSONObject) parser.parse(stock1Data);
			stock2Json = (JSONObject) parser.parse(stock2Data);
			
			stock1Price = Double.parseDouble(stock1Json.get(stock1).toString().split(":")[1].split("}")[0]);
			stock2Price = Double.parseDouble(stock2Json.get(stock2).toString().split(":")[1].split("}")[0]);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   
		   return " <h1> this is a monthly comparison of " 
		   + stock1  + " and " + stock2 + "</h1>" 
		   + " with the values <br>" 
		   + stock1Price+ " and " + stock2Price + "<br>"
		   + "A share of "+ stock1 + " is worth " 
		   + (stock1Price/stock2Price)
		   +" shares of " + stock2;
		     
				   
	   }

	}