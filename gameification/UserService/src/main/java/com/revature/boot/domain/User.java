package com.revature.boot.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.google.gson.Gson;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(generator="users_id_seq", strategy=GenerationType.IDENTITY)
	private Long id;

	@Pattern(regexp="[a-zA-Z]+")
	@javax.validation.constraints.Size(min=2, max=15)
	@NotBlank
	private String username;

	@javax.validation.constraints.Size(min=4, max=15)
	@NotBlank
	private String password;
	private double funds;
	private double previous_funds;
	private double previous_profit_margin;
	private double current_profit_margin;
	private String custom_index;
	private String portfolio;


	public User(){
		super();
	}
	public User(Long id, String name) {
		this.id=id;
		this.username=name;
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.funds = 10000;
		this.previous_funds = 0;
		this.custom_index = "";
		this.current_profit_margin = 0;
		this.previous_profit_margin = 0;
		this.portfolio = "";
	}


	public User(Long id, String username, String password, double funds, double previous_funds,double previous_profit_margin, double current_profit_margin, String custom_index, String portfolio) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.funds = funds;
		this.previous_funds = previous_funds;
		this.current_profit_margin = current_profit_margin;
		this.previous_profit_margin = previous_profit_margin;
		this.custom_index = custom_index;
		this.portfolio = portfolio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public double getFunds() {
		return funds;
	}


	public void setFunds(double funds) {
		this.funds = funds;
	}

	public void addFunds(double amount){
		this.previous_funds = this.funds;
		this.funds = this.funds+amount;
	}

	public Boolean removeFunds(double amount){
		if((this.funds - amount)<0 ){
			return false;
		}else{
			this.previous_funds = this.funds;
			this.funds = this.funds - amount;
			return true;
		}
	}

	public double getPreviousFunds() {
		return previous_funds;
	}

	public void setPreviousFunds(double previousFunds) {
		this.previous_funds = previousFunds;
	}

	public double getPreviousProfitMargin() {
		return previous_profit_margin;
	}

	public void setPreviousProfitMargin(double priorProfitMargin) {
		this.previous_profit_margin = priorProfitMargin;
	}


	public double getCurrentProfitMargin() {
		return current_profit_margin;
	}


	public void setCurrentProfitMargin(double currentProfitMargin) {
		this.current_profit_margin = currentProfitMargin;
	}

	public String getCustomIndex() {
		return custom_index;
	}

	public void setCustomIndex(String custom_index) {
		this.custom_index = custom_index;
	}

	public void addCustomIndex(String newIndex){
		//Check for empty
		if(this.custom_index.equals("")){
			this.custom_index=newIndex;
		}else{
			this.custom_index=this.custom_index+","+newIndex;
		}
	}

	
	
	/*PORTFOLIO METHODS*/
	public void addCustomPortfolioIndexes(String newIndex){
		//Check for empty
		if(this.portfolio.equals("")){
			this.portfolio=newIndex;
		}else{
			this.portfolio=this.portfolio+","+newIndex;
		}
	}
	
	public String getPortfolioValue(){
		return this.portfolio;
	}
	public String getPortfolio() {
		return "{\"portfolio\":["+this.portfolio+"]}";
	}
	public void setPortfolio(String portfolio) {
		this.portfolio = portfolio;
	}
	public void returnTestPortfolioData(){
		Map jsonJavaRootObject = new Gson().fromJson(this.portfolio, Map.class);
		System.out.println(jsonJavaRootObject.get("portfolio"));
	}
	public void addToPortfolio(String stocksToAppend){
		//Add functionality to append 
		//this.portfolio = this.portfolio+","+stocksToAppend;
		
	}
	
	public void removeFromPortfolio(String stocksToAppend){
		//ADD CODE TO REMOVE STOCKS FROM 
	}
	
	
	/*END OF PORTFOLIO METHODS*/
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double growthPercentage(){
		double currentDifference = this.current_profit_margin - this.previous_profit_margin;
		return (currentDifference/this.previous_profit_margin)*100;
	}

	public double growthNormalizer(){
		double growthPercentage = this.growthPercentage();
		return growthPercentage/(this.funds/this.previous_funds);
	}

	public String returnCustomIndex(){
		return "{\"index\":["+this.custom_index+"]}";
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + this.username + "]";
	}

}
