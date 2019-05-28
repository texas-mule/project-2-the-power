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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.xml.transform.Source;

import org.jboss.logging.Param;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@RestController
@RequestMapping("/")
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
	   
	   /*getprice
	    * 
	    * return price only when given ticker name
	    * 
	    * 
	    */
	   
	   @GetMapping("/hello/{name}")
	   @ResponseBody
	   public String getFooById(@PathVariable String name) {
	       return "hello " + name + " you did it!";
	   }
	   
	   @GetMapping("/customindex/{timeframe}")
	   @ResponseBody
	   public String GenerateReport(@PathVariable String timeframe) // int is placeholder for object
	   {
		   //
		   //for each ticker, get summary from
		   // /stock/{name}/{id}
		   //
		   //
		   //
		   
		   
		   return "this is a generated report for " + timeframe + " units";
	   }

	   @SuppressWarnings("unchecked")
	   @GetMapping("/Dailyhigh/{stock}/{days}")
	   public double ListCustomIndex(@PathVariable String stock, @PathVariable int days)
	   {
		   try {
			   
				String stockUrl = "https://financialmodelingprep.com/api/v3/historical-price-full/"+stock+"?timeseries=" + days;	
				String stockData = Stock.sendGet(stockUrl);
				JSONParser parser = new JSONParser();
				JSONObject stockJson = (JSONObject) parser.parse(stockData);
				JSONArray details = (JSONArray) stockJson.get("historical");
				
				List<String> stockInfo = new ArrayList<>();
				
				HashMap<String, String> stockKeyValue = new HashMap<String, String>();
				
				
				double testint[] = {0,0,0,0};
			
				details.forEach( timeseries ->
				{
					JSONObject parse = (JSONObject) timeseries;
					
					for(int i = 0; i<parse.size(); i++)
					{
						String key = parse.keySet().toArray()[i].toString();
						
						stockKeyValue.put(key, parse.get(key).toString());
						stockInfo.add(i, parse.get(key).toString());
					}

					testint[0] += 1;
					testint[1] += Double.parseDouble(parse.get("high").toString());
					System.out.println(stockKeyValue.toString());

				});
				
				System.out.println("\n");
				System.out.println("average daily high over "+days+" days: " + (testint[1]/testint[0]));
				System.out.println(stockKeyValue.toString());
				
				return (testint[1]/testint[0]);
				
		   	} catch (Exception e) {

				return -999;
			}
		   
			   
		   
	   }
	   
	   
	   @SuppressWarnings("unchecked")
	   @GetMapping("/Dailylow/{stock}/{days}")
	   public double ListHighIndex(@PathVariable String stock, @PathVariable int days)
	   {
		   try {
			   
				String stockUrl = "https://financialmodelingprep.com/api/v3/historical-price-full/"+stock+"?timeseries=" + days;	
				String stockData = Stock.sendGet(stockUrl);
				JSONParser parser = new JSONParser();
				JSONObject stockJson = (JSONObject) parser.parse(stockData);
				JSONArray details = (JSONArray) stockJson.get("historical");
				
				List<String> stockInfo = new ArrayList<>();
				
				HashMap<String, String> stockKeyValue = new HashMap<String, String>();
				
				
				double testint[] = {0,0,0,0};
			
				details.forEach( timeseries ->
				{
					JSONObject parse = (JSONObject) timeseries;
					
					for(int i = 0; i<parse.size(); i++)
					{
						String key = parse.keySet().toArray()[i].toString();
						
						stockKeyValue.put(key, parse.get(key).toString());
						stockInfo.add(i, parse.get(key).toString());
					}

					testint[0] += 1;
					testint[1] += Double.parseDouble(parse.get("low").toString());
					System.out.println(stockKeyValue.toString());

				});
				
				System.out.println("\n");
				System.out.println("average daily high over "+days+" days: " + (testint[1]/testint[0]));
				System.out.println(stockKeyValue.toString());
				
				return (testint[1]/testint[0]);
				
		   	} catch (Exception e) {

				return -999;
			}
		   
			   
		   
	   }
	   
	   @SuppressWarnings("unchecked")
	   @GetMapping("/DailyMovement/{stock}/{days}")
	   public double ListDailyMovement(@PathVariable String stock, @PathVariable int days)
	   {
		   try {
			   
				String stockUrl = "https://financialmodelingprep.com/api/v3/historical-price-full/"+stock+"?timeseries=" + days;	
				String stockData = Stock.sendGet(stockUrl);
				JSONParser parser = new JSONParser();
				JSONObject stockJson = (JSONObject) parser.parse(stockData);
				JSONArray details = (JSONArray) stockJson.get("historical");
				
				List<String> stockInfo = new ArrayList<>();
				
				HashMap<String, String> stockKeyValue = new HashMap<String, String>();
				
				
				double testint[] = {0,0,0,0};
			
				details.forEach( timeseries ->
				{
					JSONObject parse = (JSONObject) timeseries;
					
					for(int i = 0; i<parse.size(); i++)
					{
						String key = parse.keySet().toArray()[i].toString();
						
						stockKeyValue.put(key, parse.get(key).toString());
						stockInfo.add(i, parse.get(key).toString());
					}

					testint[0] += 1;
					testint[1] += Double.parseDouble(parse.get("changeOverTime").toString());
					System.out.println(stockKeyValue.toString());

				});
				
				System.out.println("\n");
				System.out.println("average daily change over "+days+" days: " + (testint[1]/testint[0]));
				System.out.println(stockKeyValue.toString());
				
				return (testint[1]/testint[0]);
				
		   	} catch (Exception e) {

				return -999;
			}
		   
			   
		   
	   }
	   
	   
	   @GetMapping("/gradeIndex/{id}")
	   public void GetCustomIndex(@RequestParam String id)
	   {
		   
		   //fetch index from database
		   // get all stocks data
		   //conglom growth %
		   // return grade of custom index
		   
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
	@GetMapping("/stock/{stock}/{days}")
	   public String ListStock(@PathVariable String stock, @PathVariable int days)
	   {
		
		   try {
			   
			String stockUrl = "https://financialmodelingprep.com/api/v3/historical-price-full/"+stock+"?timeseries=" + days;	
			String stockData = Stock.sendGet(stockUrl);
			JSONParser parser = new JSONParser();
			JSONObject stockJson = (JSONObject) parser.parse(stockData);
			JSONArray details = (JSONArray) stockJson.get("historical");
			
			List<String> stockInfo = new ArrayList<>();
			
			HashMap<String, String> stockKeyValue = new HashMap<String, String>();
			
			
			double testint[] = {0,0,0,0};
		
			details.forEach( timeseries ->
			{
				JSONObject parse = (JSONObject) timeseries;
				
				for(int i = 0; i<parse.size(); i++)
				{
					String key = parse.keySet().toArray()[i].toString();
					
					stockKeyValue.put(key, parse.get(key).toString());
					stockInfo.add(i, parse.get(key).toString());
				}

				testint[0] += 1;
				testint[1] += Double.parseDouble(parse.get("high").toString());
				testint[2] += Double.parseDouble(parse.get("low").toString());
				testint[3] += Double.parseDouble(parse.get("changeOverTime").toString());
				System.out.println(stockKeyValue.toString());

			});
			
			System.out.println("\n");
			System.out.println("average daily high over "+days+" days: " + (testint[1]/testint[0]));
			System.out.println("average daily low over "+days+" days: "+ (testint[2]/testint[0]));
			System.out.println("average change over "+days+" days: "+ (testint[3]/testint[0]) + "%");
			System.out.println(stockKeyValue.toString());
			
			
			//System.out.println(stockInfo.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   return "this is the data for a share of " + stock;
	   }
	   
	  @PostMapping("/portfolio")
	  public String GradePortfolio(@PathVariable String stock, @PathVariable int days)
	   {
		  return "ok";
	   }
	   
	   
	   @GetMapping("/compareDay/{stock1}/{stock2}")
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
		   
		   
		   return " <h1> this is a Daily comparison of " 
		   + stock1  + " and " + stock2 + "</h1>" 
		   + " with the values <br>" 
		   + stock1Price+ " and " + stock2Price + "<br>"
		   + "A share of "+ stock1 + " is worth " 
		   + (stock1Price/stock2Price)
		   +" shares of " + stock2;
		     
				   
	   }

	}