package com.revature.powerstock;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project2.stockConsumerResources.Stock;
import com.revature.powerstock.functions.comparison.CompareStocks;
import com.revature.powerstock.functions.customindex.DailyStockMovement;
import com.revature.powerstock.functions.customindex.GenerateIndexReport;
import com.revature.powerstock.functions.stockinfo.StockInformation;
import com.revature.powerstock.functions.stocksectors.ConsumerDefensiveStocks;
import com.revature.powerstock.functions.stocksectors.HealthCareStocks;
import com.revature.powerstock.functions.stocksectors.TechnologyStocks;

// Create A Rest Controller to manage PowerStock functions
@RestController
@RequestMapping("/stock")
public class PowerStockController {
	
	// List Stocks By Sector Functionality
	@GetMapping("/stocks")
	public HashMap<String, HashMap<String, String>> availableStocks() {
		
		// Create An ArrayList to Hold Stock Names
			HashMap<String, HashMap<String, String>> sectors = new HashMap<String, HashMap<String, String>>();
					
		// Gather Technology Stock Data
			TechnologyStocks techStockData = new TechnologyStocks();
			sectors.put("Technology", techStockData.getStocks());
			
			System.out.println("Technology Stocks");
		
		// Gather Consumer Defensive Stock Data
			ConsumerDefensiveStocks conDefStockData = new ConsumerDefensiveStocks();
			sectors.put("Consumer Defensive", conDefStockData.getStocks());
				
			System.out.println("Consumber Defensive Stocks");
		
		// Gather HealthCare Stock Data
			HealthCareStocks healthStockData = new HealthCareStocks();
			sectors.put("Health Care", healthStockData.getStocks());
					
			System.out.println("HealthCare Stocks");
		
		return sectors;

		   
		
	}

	// Compare Two Stocks Functionality
	// pathvariableone - stocksymbol
	// pathvariabletwo - stocksymbol
   @GetMapping("/compare/{stock1}/{stock2}")
   @ResponseBody
   public HashMap<String, Double> compareStocks(@PathVariable String stock1, @PathVariable String stock2) throws Exception
   {
	   HashMap<String, Double> compHashData = new HashMap<String, Double>();
	   
	   CompareStocks comparison = new CompareStocks();
	   compHashData.putAll(comparison.getStockComparison(stock1, stock2));

	   return compHashData;   
   }

   // Get Price Of Stock 
   // pathvariableone - stocksymbol
   @GetMapping("/price/{ticker}")
  	public HashMap<String, Double> priceOfStock(@PathVariable String ticker) throws Exception
  	{
	   HashMap<String, Double> stockPrice = new HashMap<String, Double>();
	   String stockURL= "https://financialmodelingprep.com/api/company/price/"+ticker+"?datatype=json";
	   String stockData = Stock.sendGet(stockURL);
	   stockPrice.put(ticker ,Double.parseDouble((stockData.toString().split(":")[2]).replace("}", "")));
		
	   return stockPrice;
  	}

   // Gather Overall Change in Custom Index
   // timeframe - int
   // username will be string
   @SuppressWarnings("unchecked")
   @GetMapping("/customindex/{timeframe}/{username}")
   @ResponseBody
   public HashMap<String, Double> generateIndexReport(@PathVariable int timeframe, @PathVariable String username) {
	   HashMap<String, Double> stockMovement = new HashMap<String, Double>();
	   
	   GenerateIndexReport indexReport = new GenerateIndexReport();
	   double totalMovement = indexReport.GenerateReport(timeframe, username);
	   
	   stockMovement.put(username, totalMovement);
	   
	   return stockMovement;
	   
   }
   
   // Get The Daily High For A Stock
   @SuppressWarnings("unchecked")
   @GetMapping("/high/{stock}/{days}")
   public HashMap<String, Double> stockAverageHigh(@PathVariable String stock, @PathVariable int days) throws Exception {
	   HashMap<String, Double> high = new HashMap<String, Double>();
		   
			String stockUrl = "https://financialmodelingprep.com/api/v3/historical-price-full/"+stock+"?timeseries=" + days;	
			String stockData = Stock.sendGet(stockUrl);
			JSONParser parser = new JSONParser();
			JSONObject stockJson = (JSONObject) parser.parse(stockData);
			JSONArray details = (JSONArray) stockJson.get("historical");
			
			List<String> stockInfo = new ArrayList<>();
			
			HashMap<String, String> stockKeyValue = new HashMap<String, String>();
			
			ArrayList<Double> dailyHigh = new ArrayList<Double>();
		
			details.forEach( timeseries ->
			{
				JSONObject parse = (JSONObject) timeseries;
				
				for(int i = 0; i<parse.size(); i++)
				{
					String key = parse.keySet().toArray()[i].toString();
					
					stockKeyValue.put(key, parse.get(key).toString());
					stockInfo.add(i, parse.get(key).toString());
				}

				dailyHigh.add(Double.parseDouble(parse.get("high").toString()));

			});
			
			double sum = 0;
			double avg = 0;
			
			for(int i = 0; i<dailyHigh.size();i++) {
				sum = sum + dailyHigh.get(i);
				avg = sum/dailyHigh.size();
			}
			
			DecimalFormat df = new DecimalFormat("####0.00");
			
			high.put(stock, avg);
			System.out.println("\n");
			System.out.println("average daily high over "+days+" days: " + (df.format(avg)));
			
			return (high);
			
   }
   
   // Get The Daily Low For A Stock
   @SuppressWarnings("unchecked")
   @GetMapping("/low/{stock}/{days}")
   public HashMap<String, Double> stockAverageLow(@PathVariable String stock, @PathVariable int days) throws Exception {
	   HashMap<String, Double> low = new HashMap<String, Double>();

			String stockUrl = "https://financialmodelingprep.com/api/v3/historical-price-full/"+stock+"?timeseries=" + days;	
			String stockData = Stock.sendGet(stockUrl);
			JSONParser parser = new JSONParser();
			JSONObject stockJson = (JSONObject) parser.parse(stockData);
			JSONArray details = (JSONArray) stockJson.get("historical");
			
			List<String> stockInfo = new ArrayList<>();
			
			HashMap<String, String> stockKeyValue = new HashMap<String, String>();
			
			ArrayList<Double> dailyHigh = new ArrayList<Double>();
		
			details.forEach( timeseries ->
			{
				JSONObject parse = (JSONObject) timeseries;
				
				for(int i = 0; i<parse.size(); i++)
				{
					String key = parse.keySet().toArray()[i].toString();
					
					stockKeyValue.put(key, parse.get(key).toString());
					stockInfo.add(i, parse.get(key).toString());
				}

				dailyHigh.add(Double.parseDouble(parse.get("low").toString()));

			});
			
			double sum = 0;
			double avg = 0;
			
			for(int i = 0; i<dailyHigh.size();i++) {
				sum = sum + dailyHigh.get(i);
				avg = sum/dailyHigh.size();
			}
			
			DecimalFormat df = new DecimalFormat("####0.00");
			
			low.put(stock, avg);
			System.out.println("\n");
			System.out.println("average daily low over "+days+" days: " + (df.format(avg)));
			
			return (low);
			
   }
   
   // Get Basic Stock Info
	@SuppressWarnings("unchecked")
	@GetMapping("/stockinfo/{stock}/{days}")
	   public HashMap<String, HashMap<String, Double>> ListStockInfo(@PathVariable String stock, @PathVariable int days) throws Exception
	   {
		
		HashMap<String, HashMap<String, Double>> stockInfo = new HashMap<String, HashMap<String,Double>>();
		
		StockInformation stockData = new StockInformation();
		stockInfo.put(stock, stockData.ListStockInfo(stock, days));

		return stockInfo;
		
	   }
	
  // Fair Price Algorithm
	   @SuppressWarnings("unchecked")
	   @GetMapping("/fairprice/{stock}")
 	   public FairPriceData displayFairPrice(@PathVariable String stock)
	   {
		   FairPriceData fairPrice = new FairPriceData();
		   
		   try {
			   
				String stockUrl = "https://api.iextrading.com/1.0/stock/"+stock+"/book";	
				String stockData = Stock.sendGet(stockUrl);
				JSONParser parser = new JSONParser();
				JSONObject stockJson = (JSONObject) parser.parse(stockData);
				JSONObject details = (JSONObject) stockJson.get("quote");
				

				double stockPENEC = (double) details.get("peRatio");
				fairPrice.setStockPeRatio((double) details.get("peRatio"));
				String sectorComparison = (String) details.get("sector");
				sectorComparison.trim();
				sectorComparison = sectorComparison.replaceAll("\\s", "%20");
	
				String stockUrl2 = "https://api.iextrading.com/1.0/stock/market/collection/sector?collectionName="+sectorComparison;	
				String stockData2 = Stock.sendGet(stockUrl2);
				JSONParser parser2 = new JSONParser();
				JSONArray stockJson2 = (JSONArray) parser2.parse(stockData2);
			
				
				HashMap<String, Double> stockKeyValue = new HashMap<String, Double>();
				int size = 0;
				int i = 0;
				double summation = 0.0;
				
					while(size<30)
					{
						String stockName;
						double stockPE;
	
						try {
							JSONObject stock4 = (JSONObject) stockJson2.get(i);

							stockName = (String) stock4.get("symbol");
				
							stockPE = (double) stock4.get("peRatio");
			
							if(stockPE>0) {
								stockKeyValue.put(stockName, stockPE);
								size++;
								i++;
								summation = summation + stockPE;
							}
							
							i++;
						
						} catch (Exception e) {
							i++;
						}
	
					}
				fairPrice.setSectorPeRatio(summation/30);
				
				if(stockPENEC<((summation/30))&& stockPENEC>0) {
					fairPrice.setBuyindicator("BUY");
					return fairPrice;
				}	
						
				else {
					fairPrice.setBuyindicator("DONT BUY");
					return fairPrice;
				}
				
		   	} catch (Exception e) {
		   		return null;

		   	}
		   
	   }
	
	   @SuppressWarnings("unchecked")
		@GetMapping("/portfolio/{username}")
		  public HashMap<String, String> GradePortfolio(@PathVariable String username) throws Exception
		   {		   
				   JSONParser parser = new JSONParser();
				    
				   	String UserUrl = "https://localhost:7070/users/" + username + "/index";	
					String stockData = Stock.sendGet(UserUrl);
					JSONObject json = (JSONObject) parser.parse(stockData);
		
					JSONArray details = (JSONArray) json.get("portfolio");
					
					System.out.println(json.toString());
					List<String> stockInfo = new ArrayList<>();
					
					HashMap<String, String> stockKeyValue = new HashMap<String, String>();
					
					HashMap<String, String> portGrade = new HashMap<String, String>();
					
					double testint[] = {0};
				
					details.forEach( timeseries ->
					{
						JSONObject parse = (JSONObject) timeseries;
						
						for(int i = 0; i<parse.size(); i++)
						{
							String key = parse.keySet().toArray()[i].toString();
							
							stockKeyValue.put(key, parse.get(key).toString());
							stockInfo.add(i, parse.get(key).toString());
							
						}
						
						DailyStockMovement movement = new DailyStockMovement();
						
						try {
							testint[0]+= ((movement.ListDailyMovement(stockKeyValue.get("ticker"),5) *.01))
										*((stockAverageHigh(stockKeyValue.get("ticker"),5)).get(0)*
										Double.parseDouble(stockKeyValue.get("amount")));
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						System.out.println(stockKeyValue.toString());
						
						System.out.println(testint[0]);

					});
					System.out.println(testint[0]);
					
					portGrade.put(username, Stock.Evaluate(testint[0]));
					
					return portGrade;

		   }
}
   

