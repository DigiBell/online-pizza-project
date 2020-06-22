package com.mycompany.onlinepizzaproject.backend;

public class Measurement implements Comparable<Measurement>{
	
	public enum Unit{
		g,
		kg
	}
	
	private double amount;
	private Unit unit;
	
	public Measurement() {
		this.amount = 0;
		this.unit = Unit.g;
	}
	
	public Measurement(double amount, Unit unit) {
		this.amount = amount;
		this.unit = unit;
	}
	
	public Measurement(String string) {
		String[] args = string.split(" ");
		this.amount = Double.valueOf(args[0]);
		this.unit = Unit.valueOf(args[1]);
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public double toKg() {
		if(unit == Unit.g) {
			return amount / 1000;
		} else {
			return amount;
		}
	}
	
	public double toG() {
		if(unit == Unit.kg) {
			return amount * 1000;
		} else {
			return amount;
		}
	}
	
	public void increase(Measurement m) {
		if(unit == m.unit) {
			amount += m.amount;
		} else if(unit == Unit.g && m.unit == Unit.kg){
			amount += m.toG();
		} else {
			amount += m.toKg();
		}
	}
	
	public void decrease(Measurement m) throws NegativeNumberException {
		if(unit == m.unit) {
			amount -= m.amount;
		} else if(unit == Unit.g && m.unit == Unit.kg){
			amount -= m.toG();
		} else {
			amount -= m.toKg();
		}
		
		if(amount < 0) {
			throw new NegativeNumberException();
		}
	}
	
	@Override
	public String toString() {
		return amount + " " + unit;
	}
	
	public class NegativeNumberException extends Exception{
		private static final long serialVersionUID = 1L;
		
	}

	@Override
	public int compareTo(Measurement m) {
		if(toG() < m.toG()) {
			return -1;
		} else if (toG() > m.toG()) {
			return 1;
		} else {
			return 0;
		}		
	}
	
}


