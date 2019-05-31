package com.revature.boot.controller;

public class JsonRet {
private String key;

public JsonRet(String key) {
	super();
	this.key = key;
}

public String getMessage() {
	return key;
}

public void setMessage(String key) {
	this.key = key;
}


}

