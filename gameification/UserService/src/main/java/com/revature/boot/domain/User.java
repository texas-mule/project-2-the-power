package com.revature.boot.domain;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
	//ArrayList<CustomIndex> custom_indexes_list;
	private String custom_indexes;
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
		this.custom_indexes = "";
		this.current_profit_margin = 0;
		this.previous_profit_margin = 0;
	}

	
	public User(Long id, String username, String password, double funds, double previous_funds,double previous_profit_margin, double current_profit_margin, String custom_indexes) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.funds = funds;
		this.previous_funds = previous_funds;
		this.current_profit_margin = current_profit_margin;
		this.previous_profit_margin = previous_profit_margin;
		this.custom_indexes = custom_indexes;
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

	public String getCustomIndexes() {
		return custom_indexes;
	}
	
	public void setCustomIndexes(String custom_indexes) {
		this.custom_indexes = custom_indexes;
	}
	
	public void addCustomIndexes(String newIndex){
		//Check for empty
		if(this.custom_indexes.equals("")){
			this.custom_indexes=newIndex;
		}else{
			this.custom_indexes=this.custom_indexes+","+newIndex;
		}
	}
	
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
	
	public String returnCustomIndexes(){
		return "{\"key\":["+this.custom_indexes+"]}";
	}
		
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + this.username + "]";
	}

}
