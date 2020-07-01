package com.mycompany.onlinepizzaproject.backend;

import org.bson.Document;

public class Product {
	
	public enum Category{
		iceCream,
		beverage,
		sauce,
		sideDish
	}
	
	private String name;
	private Category category;
	private int price;
	private int stock;
	
	public Product(String name, Category category, int price, int stock) {
		this.name = name;
		this.category = category;
		this.price = price;
		this.stock = stock;
	}
	
	public Product(Document doc) {
		this.name = doc.getString("name");
		this.category = Category.valueOf(doc.getString("category"));
		this.price = doc.getInteger("price");
		this.stock = doc.getInteger("stock");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public void increaseStock(int amount) {
		stock += amount;
	}
	
	public void decreaseStock(int amount) {
		stock -= amount;
		if(stock < 0) {
			System.out.println("Stock can not be negative");
			stock = 0;
		}
	}
	
	public Document toDocument() {
		Document doc = new Document("name", name)
				.append("category", category.toString())
				.append("price", price)
				.append("stock", stock);
		
		return doc;
	}
	
}
