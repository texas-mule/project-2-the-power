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
	public String availableStocks() {
		
		// Create An ArrayList to Hold Stock Names
			ArrayList<String> sectors = new ArrayList<String>();
					
		// Gather Technology Stock Data
			TechnologyStocks techStockData = new TechnologyStocks();
			ArrayList<String> tech = techStockData.getStocks();
			
			System.out.println("Technology Stocks");
		sectors.addAll(tech);
		
		// Gather Consumer Defensive Stock Data
			ConsumerDefensiveStocks conDefStockData = new ConsumerDefensiveStocks();
			ArrayList<String> conDef = conDefStockData.getStocks();
				
			System.out.println("Consumber Defensive Stocks");
		sectors.addAll(conDef);
		
		// Gather HealthCare Stock Data
			HealthCareStocks healthStockData = new HealthCareStocks();
			ArrayList<String> health = healthStockData.getStocks();
					
			System.out.println("HealthCare Stocks");
		sectors.addAll(health);

		
		return sectors.toString();

		   
		
	}

	// Compare Two Stocks Functionality
	// pathvariableone - stocksymbol
	// pathvariabletwo - stocksymbol
   @GetMapping("/compare/{stock1}/{stock2}")
   @ResponseBody
   public String compareStocks(@PathVariable String stock1, @PathVariable String stock2) throws Exception
   {
	   CompareStocks comparison = new CompareStocks();
	   String comparisonData = comparison.getStockComparison(stock1, stock2);

	   return comparisonData;   
   }

   // Get Price Of Stock 
   // pathvariableone - stocksymbol
   @GetMapping("/price/{ticker}")
  	public Double priceOfStock(@PathVariable String ticker) throws Exception
  	{
	   String stockURL= "https://financialmodelingprep.com/api/company/price/"+ticker+"?datatype=json";
	   String stockData = Stock.sendGet(stockURL);
	   return Double.parseDouble((stockData.toString().split(":")[2]).replace("}", ""));
		
  	}

   // Gather Overall Change in Custom Index
   // timeframe - int
   // username will be string
   @SuppressWarnings("unchecked")
   @GetMapping("/customindex/{timeframe}/{username}")
   @ResponseBody
   public double generateIndexReport(@PathVariable int timeframe, @PathVariable String username) {
	   GenerateIndexReport indexReport = new GenerateIndexReport();
	   double totalMovement = indexReport.GenerateReport(timeframe, username);
	   
	   return totalMovement;
	   
   }
   
   // Get The Daily High For A Stock
   @SuppressWarnings("unchecked")
   @GetMapping("/high/{stock}/{days}")
   public double stockAverageHigh(@PathVariable String stock, @PathVariable int days) {
	   try {
		   
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
			
			System.out.println("\n");
			System.out.println("average daily high over "+days+" days: " + (df.format(avg)));
			
			return (avg);
			
	   	} catch (Exception e) {

			return -999;
		}
   }
   
   // Get The Daily Low For A Stock
   @SuppressWarnings("unchecked")
   @GetMapping("/low/{stock}/{days}")
   public double stockAverageLow(@PathVariable String stock, @PathVariable int days) {
	   try {
		   
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
			
			System.out.println("\n");
			System.out.println("average daily low over "+days+" days: " + (df.format(avg)));
			
			return (avg);
			
	   	} catch (Exception e) {
			return -999;
		}
   }
   
   // Get Basic Stock Info
	@SuppressWarnings("unchecked")
	@GetMapping("/stockinfo/{stock}/{days}")
	   public String ListStockInfo(@PathVariable String stock, @PathVariable int days)
	   {
		StockInformation stockData = new StockInformation();
		return stockData.ListStockInfo(stock, days);
	   }
	
	   
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
				
				
		
				System.out.println(stockKeyValue.toString());
				System.out.println(summation/30);
				fairPrice.setSectorPeRatio(summation/30);
				System.out.println(stockPENEC);
				
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
		  @GetMapping("/portfolio/{stock}/{days}")
		  public String GradePortfolio(@PathVariable String stock, @PathVariable int days)
		   {
			  String Test = "{ \"portfolio\" : ["
				   		+ "{ \"ticker\": \"AAPL\","
				   		+ "	\"amount\": 20}, "
				   		+ "{ \"ticker\": \"WMT\","
				   		+ "	\"amount\": 20}, "
				   		+ "] }";
				   
				   JSONParser parser = new JSONParser();
				   
				   try {
					   
				    /*
				   	String UserUrl = "https://localhost:7070/users/" + username + "/index";	
					String stockData = Stock.sendGet(stockUrl);
					JSONParser parser = new JSONParser();
					JSONObject json = (JSONObject) parser.parse(stockData);
					*/
					   
					JSONObject json = (JSONObject) parser.parse(Test); // replace this with above comment when production
					JSONArray details = (JSONArray) json.get("portfolio");
					
					System.out.println(json.toString());
					List<String> stockInfo = new ArrayList<>();
					
					HashMap<String, String> stockKeyValue = new HashMap<String, String>();
					
					
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
						
						testint[0]+= ((movement.ListDailyMovement(stockKeyValue.get("ticker"),5) *.01))
									*(stockAverageHigh(stockKeyValue.get("ticker"),5)*
									Double.parseDouble(stockKeyValue.get("amount")));
						
						System.out.println(stockKeyValue.toString());
						
						System.out.println(testint[0]);

					});
					System.out.println(testint[0]);
					return Stock.Evaluate(testint[0]);

					

			   	} catch (Exception e) {

					return "none available";
				}
		   }
}
   

