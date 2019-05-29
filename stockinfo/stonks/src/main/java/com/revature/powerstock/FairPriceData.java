package com.revature.powerstock;

public class FairPriceData {

	private double stockPeRatio;
	private double sectorPeRatio;
	public double getStockPeRatio() {
		return stockPeRatio;
	}
	public void setStockPeRatio(double stockPeRatio) {
		this.stockPeRatio = stockPeRatio;
	}
	public double getSectorPeRatio() {
		return sectorPeRatio;
	}
	public void setSectorPeRatio(double sectorPeRatio) {
		this.sectorPeRatio = sectorPeRatio;
	}
	public String getBuyindicator() {
		return buyindicator;
	}
	public void setBuyindicator(String buyindicator) {
		this.buyindicator = buyindicator;
	}
	private String buyindicator;
	
}
