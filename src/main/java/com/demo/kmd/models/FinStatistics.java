package com.demo.kmd.models;

import java.util.Date;

public class  FinStatistics {
	
	private double amount;
	private String  date ;
	
	public FinStatistics(String date, double amount) {		
		this.date = date;
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String  getDate() {
		return date;
	}

	public void setDate(String  date) {
		this.date = date;
	}
	
	
	
}
