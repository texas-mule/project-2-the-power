package com.revature.boot.domain;

import javax.persistence.Column;
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
	private double priorprofitmargin;
	private double currentprofitmargin;
	public User(){
		super();
	}
	public User(Long id, String name) {
		this.id=id;
		this.username=name;
	}

	public User(Long id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.funds = 0;
		this.priorprofitmargin = 0;
		this.currentprofitmargin = 0;
	}

	
	public User(Long id, String username, String password, double funds, double priorProfitMargin, double currentProfitMargin) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.funds = funds;
		this.priorprofitmargin = priorProfitMargin;
		this.currentprofitmargin = currentProfitMargin;
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


	public double getPriorProfitMargin() {
		return priorprofitmargin;
	}


	public void setPriorProfitMargin(double priorProfitMargin) {
		this.priorprofitmargin = priorProfitMargin;
	}


	public double getCurrentProfitMargin() {
		return currentprofitmargin;
	}


	public void setCurrentProfitMargin(double currentProfitMargin) {
		this.currentprofitmargin = currentProfitMargin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Artist [id=" + id + ", name=" + this.username + "]";
	}

}