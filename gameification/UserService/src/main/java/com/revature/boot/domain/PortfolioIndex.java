package com.revature.boot.domain;


public class PortfolioIndex {

	private String stock = "stock";
	private String ticker;
	private String amount = "amount";
	private int value;
	
	public PortfolioIndex(){
		this.ticker = null;
		this.value = 0;
	}
	
	public PortfolioIndex(String ticker, int value){
		this.ticker = ticker;
		this.value = value;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String returnIndex(){
		return "{\""+this.stock+"\":\""+this.ticker+"\",\""+this.amount+"\":"+this.value+"}";
	}
}
