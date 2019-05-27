package com.revature.boot.domain;

import java.util.ArrayList;
import java.util.List;

public class CustomIndex {
	private String key = "index";
	private ArrayList<StockIndex> value;
	
	public CustomIndex(){
		this.value = new ArrayList<StockIndex>();
	}
	
	public CustomIndex(ArrayList<StockIndex> stockIndexes){
		this.value = stockIndexes;
	}
	
	public String returnCustomIndex(){
		String retString = "{\""+this.key+"\":"+"[";
		for(int i=0; i<this.value.size();i++){
			retString =retString+this.value.get(i).returnIndex();
			if(i != this.value.size()-1){
				retString=retString+",";
			}
		}
		retString=retString+"]}";
		return retString;
	}
	
	
	
}
