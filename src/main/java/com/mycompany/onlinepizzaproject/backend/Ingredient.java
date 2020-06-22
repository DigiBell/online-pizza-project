package com.mycompany.onlinepizzaproject.backend;

import org.bson.Document;

import com.mycompany.onlinepizzaproject.backend.Measurement.NegativeNumberException;

public class Ingredient {
	
	private String name;
	private double pricePerKg;
	private Measurement stock;
	
	public Ingredient(String name, double pricePerKg, Measurement stock) {
		this.name = name;
		this.pricePerKg = pricePerKg;
		this.stock = stock;
	}
	
	public Ingredient(Document doc) {
		this.name = doc.getString("name");
		this.pricePerKg = doc.getDouble("pricePerKg");
		this.stock = new Measurement(doc.getString("stock"));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPricePerKg() {
		return pricePerKg;
	}

	public void setPricePerKg(double price) {
		this.pricePerKg = price;
	}

	public Measurement getStock() {
		return stock;
	}

	public void setStock(Measurement stock) {
		this.stock = stock;
	}
	
	public void increaseStock(Measurement m) {
		stock.increase(m);
	}
	
	public void decreaseStock(Measurement m) {
		try {
			stock.decrease(m);
		} catch (NegativeNumberException e) {
			System.out.println("Stock can not be negative");
			setStock(new Measurement());
		}
	}
	
	public Document getStockDocument() {
		return new Document("stock", stock.toString());
	}
	
	public Document toDocument() {
		Document doc = new Document("name", name)
				.append("pricePerKg", pricePerKg)
				.append("stock", stock.toString());
		
		return doc;
	}
	
}
