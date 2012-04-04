package org.quittheprogram.benzin;

public class Filling {
	private int odometer;
	private int amount;
	private int previousOdometer;
	private int previousAmount;
	
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
}
