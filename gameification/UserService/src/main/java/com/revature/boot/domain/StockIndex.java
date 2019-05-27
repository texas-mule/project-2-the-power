package com.revature.boot.domain;

import javax.validation.constraints.NotBlank;

public class StockIndex {
	
	@NotBlank
	private String key = "stock";
	private String ticker;
	
	public StockIndex(){
		this.ticker=null;
	}
	
	public StockIndex(String ticker){
		this.ticker = ticker;
	}

	public String getKey() {
		return key;
	}
	//Key is static as stock
//	public void getKey(String key) {
//		this.key = key;
//	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	public String returnIndex(){
		return "{\""+this.key+"\":\""+this.ticker+"\"}";
	}

}
