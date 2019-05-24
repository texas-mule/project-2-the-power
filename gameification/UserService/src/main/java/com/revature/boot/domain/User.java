package com.revature.boot.domain;

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
	@Id @GeneratedValue(generator="users_id_seq", strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Pattern(regexp="[a-zA-Z]+")
	@javax.validation.constraints.Size(min=2, max=15)
	@NotBlank
	private String username;
	
	@javax.validation.constraints.Size(min=4, max=15)
	@NotBlank
	private String password;
	private double funds;
	private double previousFunds;
	private double priorProfitMargin;
	private double currentProfitMargin;
	
	public User() {
		super();
	}

	public User(Long id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.funds = 10000;
		this.priorProfitMargin = 0;
		this.currentProfitMargin = 0;
	}

	
	public User(Long id, String username, String password, double funds, double priorProfitMargin, double currentProfitMargin) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.funds = funds;
		this.priorProfitMargin = priorProfitMargin;
		this.currentProfitMargin = currentProfitMargin;
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
		return previousFunds;
	}

	public void setPreviousFunds(double previousFunds) {
		this.previousFunds = previousFunds;
	}

	public double getPriorProfitMargin() {
		return priorProfitMargin;
	}

	public void setPriorProfitMargin(double priorProfitMargin) {
		this.priorProfitMargin = priorProfitMargin;
	}


	public double getCurrentProfitMargin() {
		return currentProfitMargin;
	}


	public void setCurrentProfitMargin(double currentProfitMargin) {
		this.currentProfitMargin = currentProfitMargin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double growthPercentage(){
		double currentDifference = this.currentProfitMargin - this.priorProfitMargin;
		return (currentDifference/this.priorProfitMargin)*100;
	}
	
	public double growthNormalizer(){
		double growthPercentage = this.growthPercentage();
		return growthPercentage/(this.funds/this.previousFunds);
	}
		
	@Override
	public String toString() {
		return "Artist [id=" + id + ", name=" + this.username + "]";
	}

}
