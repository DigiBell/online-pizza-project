package com.mycompany.onlinepizzaproject.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bson.Document;

public class Pizza {
	
	public enum Size{
		S20,
		S30,
		S40,
		SGF30
	}
	
	private String name;
	private int price20cm;
	private int price30cm;
	private int price40cm;
	private int priceGf30cm;
	private String descriptionSv;
	private String descriptionEn;
	private HashMap<String, Measurement> ingredients;
	
	public Pizza() {
		this.ingredients = new HashMap<String, Measurement>();
	}
	
	public Pizza(String name, int price20cm, int price30cm, int price40cm, String descriptionSv, String descriptionEn) {
		this.name = name;
		this.price20cm = price20cm;
		this.price30cm = price30cm;
		this.price40cm = price40cm;
		this.priceGf30cm = 139;
		this.descriptionSv = descriptionSv;
		this.descriptionEn = descriptionEn;
		this.ingredients = new HashMap<String, Measurement>();
	}
	
	public Pizza(String name, int price20cm, int price30cm, int price40cm, int priceGf30cm, String descriptionSv, String descriptionEn, HashMap<String, Measurement> ingredients) {
		this.name = name;
		this.price20cm = price20cm;
		this.price30cm = price30cm;
		this.price40cm = price40cm;
		this.priceGf30cm = priceGf30cm;
		this.descriptionSv = descriptionSv;
		this.descriptionEn = descriptionEn;
		this.ingredients = ingredients;
	}

	public Pizza(Document doc) {
		this.name = doc.getString("name");
		this.price20cm = doc.getInteger("price20cm");
		this.price30cm = doc.getInteger("price30cm");;
		this.price40cm = doc.getInteger("price40cm");;
		this.priceGf30cm = doc.getInteger("priceGf30cm");;
		this.descriptionSv = doc.getString("descriptionSv");;
		this.descriptionEn = doc.getString("descriptionEn");;
		
		@SuppressWarnings("unchecked")
		List<Document> ingr = doc.get("ingredients", List.class);
		this.ingredients = new HashMap<String, Measurement>();
		for (Document document : ingr) {
			addIngredient(document.getString("ingredient"), new Measurement(document.getString("amount")));
		}
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice20cm() {
		return price20cm;
	}

	public void setPrice20cm(int price20cm) {
		this.price20cm = price20cm;
	}

	public int getPrice() {
		return price30cm;
	}

	public void setPrice30cm(int price30cm) {
		this.price30cm = price30cm;
	}

	public int getPrice40cm() {
		return price40cm;
	}

	public void setPrice40cm(int price40cm) {
		this.price40cm = price40cm;
	}

	public int getPriceGf30cm() {
		return priceGf30cm;
	}

	public void setPriceGf30cm(int priceGf30cm) {
		this.priceGf30cm = priceGf30cm;
	}

	public int getPriceForSize(Size s) {
		switch (s) {
		case S20:
			return price20cm;
		case S30:
			return price30cm;
		case S40:
			return price40cm;
		case SGF30:
			return priceGf30cm;
		default:
			return 0;
		}
	}
	
	public static String getSizeString(Size s) {
		switch (s) {
		case S20:
			return "20cm";
		case S30:
			return "30cm";
		case S40:
			return "40cm";
		case SGF30:
			return "Glutenfri 30cm";
		default:
			return "";
		}
	}
	
	public static Size getSizeFromString(String s) {
		switch (s) {
		case "20cm":
			return Size.S20;
		case "30cm":
			return Size.S30;
		case "40cm":
			return Size.S40;
		case "Glutenfri 30cm":
			return Size.SGF30;
		default:
			return null;
		}
	}
	
	
	public String getDescriptionSv() {
		return descriptionSv;
	}

	public void setDescriptionSv(String descriptionSv) {
		this.descriptionSv = descriptionSv;
	}

	public String getDescriptionEn() {
		return descriptionEn;
	}

	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}

	public HashMap<String, Measurement> getIngredients() {
		return ingredients;
	}

	public void setIngredients(HashMap<String, Measurement> ingredients) {
		this.ingredients = ingredients;
	}

	public Measurement getIngredientAmount(String name) {
		return ingredients.get(name);
	}
	
	public void addIngredient(String name, Measurement m) {
		ingredients.put(name, m);
	}
	
	public Document toDocument() {
		Document doc = new Document("name", name)
				.append("price20cm", price20cm)
				.append("price30cm", price30cm)
				.append("price40cm", price40cm)
				.append("priceGf30cm", priceGf30cm)
				.append("descriptionSv", descriptionSv)
				.append("descriptionEn", descriptionEn);
			
		// TODO: The ingredient amounts will be different for the different sizes
		List<Document> ingr = new ArrayList<>();
		
		for (String key : ingredients.keySet()) {
			ingr.add(new Document("ingredient", key).append("amount", ingredients.get(key).toString()) );
		}
		
		doc.append("ingredients", ingr);
		
		return doc;
	}
	
}
