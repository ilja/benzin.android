package org.quittheprogram.benzin;

import java.util.Calendar;

public class Filling {
	private int id;
	private int odometer;
	private int amount;
	private int previousOdometer;
	private int previousAmount;
	private double price;
	private Calendar calendar = Calendar.getInstance();
	
	public Filling(int id, int odometer, int amount, double price, long dateInMillis) {
		super();
		this.id = id;
		this.odometer = odometer;
		this.amount = amount;
		this.price = price;
		
		calendar.setTimeInMillis(dateInMillis);
	}

	public Filling(int odometer, int amount) {
		super();
		this.setOdometer(odometer);
		this.setAmount(amount);
	}
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getOdometer() {
		return odometer;
	}

	public void setOdometer(int odometer) {
		this.odometer = odometer;
	}

	public void setPreviousAmount(int previousAmount) {
		this.previousAmount = previousAmount;		
	}
	
	public int getPreviousAmount(){
		return previousAmount;
	}

	public void setPreviousOdometer(int previousOdometer) {
		this.previousOdometer = previousOdometer;
	}	

	public Double calculateLitresPerKilometer() {
		if (previousAmount == 0)
			return 0.0;
				
		double value = getDistance() / (double)previousAmount;		
		return (double)Math.round(value * 100) / 100;
	}

	public int getDistance() { 
		if(previousOdometer == 0)
			return 0;
		
		return odometer - previousOdometer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getFormattedDate(){
		return new StringBuilder()
			.append(calendar.get(Calendar.DAY_OF_MONTH)).append('-')
			.append(calendar.get(Calendar.MONTH) + 1).append('-')
			.append(calendar.get(Calendar.YEAR)).toString();
	}

	public void setCalendar(Calendar date) {
		this.calendar = date;
	}	
}
