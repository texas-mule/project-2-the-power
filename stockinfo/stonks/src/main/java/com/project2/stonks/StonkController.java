package com.project2.stonks;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// we will have to use jackson to consume the stock webservice
//rest templates should be used to read objects

//make a basic stock object to load with the proper fields

// make method to do all portfolio analysis at once

//most likely will need a new class to just handle consumption

//restTemplate can be used to do this easily

//@JsonIgnoreProperties from the Jackson JSON processing 
// library to indicate that any properties not bound in this type should be ignored.


//Spring provides a template class called JdbcTemplate that makes it easy to work with
//SQL relational databases and JDBC

@RestController
@RequestMapping("/bus")
public class StonkController {
	   
	   @GetMapping("/stocks")
	   	public String ListOfStocks()
	   	{
		   //call db to return table of stocks
		   // format table
		   //return table back as key value pairs
		   
		   return "This is a list of stocks";
	   	}
	   
	   @GetMapping("/compare/{Stock1}/{Stock2}")
	   public String DeltaChange()
	   {
		   // get stock 1
		   // put into stock item object
		   
		   // get stock 2
		   // put into stock item object
		   
		   // compare fields
		   // store in new stock item object
		   
		   // present object to the user
		   
		   return "this is the comparison of the stocks";
	   }
	   
	   @GetMapping("/customindex/{timeframe}")
	   public String GenerateReport() // int is placeholder for object
	   {
		   //take stock 1
		   //get shares
		   //get ticker name stock
		   //get value
		   //shares*value
		   // get sums of values to get %
		   // use % and viability of stock to make conglomerated rate
		   
		   return "this is a generated report";
	   }

	   
	   @PostMapping("/newindex")
	   public String makeindex()
	   {
		   // get ticker name of stocks
		   // get amount of shares
		   // repeat up to 10 times
		   // save config into db and link to user
		   
		   return "this is a generated new index";
	   }
	   
	   @GetMapping("/newindex/{Stock1}/{Stock2}")
	   public String monthlyComparison()
	   {
		   //get last months earning from stock 1
		   // load into object

		   //get last months earning from stock 2
		   // load into object
		   
		   // do the math on it
		   // generate reports on each stock
		   
		   return "this is a monthly comparison";
	   }

	}