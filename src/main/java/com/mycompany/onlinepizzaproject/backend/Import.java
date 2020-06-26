package com.mycompany.onlinepizzaproject.backend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.Document;

import com.mycompany.onlinepizzaproject.backend.Measurement.Unit;
import com.mycompany.onlinepizzaproject.backend.Product.Category;


public class Import {

	
	private static List<String> getLines(String file) {
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(Paths.get("./text/" + file));		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lines;
	}
	
	public static void pizzasAndIngredients() {
		System.out.println("IMPORTING PIZZAS AND INGREDIENTS");
		List<String> lines = getLines("pizza.txt");
				
		int counter = 0;
		
		Pizza pizza = null;
		
		Random random = new Random();
				
		List<Document> pizzas = new ArrayList<>();
		List<String> ingredients = new ArrayList<>();
		
		for (String line : lines) {
			switch (counter) {
			case 0:
				pizza = new Pizza();
				pizza.setName(line);
				break;
			case 1:
				pizza.setDescriptionSv(line);
				break;
			case 2:
				pizza.setDescriptionEn(line.substring(1, line.length()-1));
				
				// INGREDIENTS
				String[] ingreds = pizza.getDescriptionEn().split(",|and");
				
				for (String str : ingreds) {
					str = str.trim().toLowerCase();
					
					if(!ingredients.contains(str)) {
						ingredients.add(str);
					}
					
					pizza.addIngredient(str, new Measurement(random.nextInt(90)+10, Unit.g));			
				}
				
				break;
			case 3:
				pizza.setPrice20cm(Integer.parseInt(line));
				break;
			case 4:
				pizza.setPrice30cm(Integer.parseInt(line));
				break;
			case 5:
				pizza.setPrice40cm(Integer.parseInt(line));
				break;
			case 6:
				pizza.setPriceGf30cm(Integer.parseInt(line));
				
				pizzas.add(pizza.toDocument());
				
				counter = -1;
				break;
			default:
				break;
			}
			
			counter++;
		}
		
		API.addPizzas(pizzas);
		
		List<Document> ingredientDocs = new ArrayList<>();
		
		for (String string : ingredients) {
			ingredientDocs.add(new Ingredient(string, random.nextInt(200)+20, new Measurement(random.nextInt(45)+5, Unit.kg)).toDocument());
		}
		
		API.addIngredients(ingredientDocs);
		
		System.out.println("PIZZAS AND INGREDIENTS IMPORTED");
	}
	
	public static void importBeverages() {
		List<String> lines = getLines("beverages.txt");
		
		int counter = 0;
		
		String name = "";
		int price = 0;
		
		List<Document> products = new ArrayList<>();
		
		Random random = new Random();
		
		for (String line : lines) {
			System.out.println("counter: " + counter);
			switch (counter) {
			case 0:
				name = line;
				break;
			case 2:
				price = Integer.parseInt(line.substring(0, line.indexOf(':')));
				
				Product p = new Product(name, Category.beverage, price, random.nextInt(90)+10);
				
				System.out.println(p.toDocument().toJson());
				
				products.add(p.toDocument());
				
				counter = -1;
				break;
			default:
				break;
			}
			
			counter++;
		}
		
		API.addProducts(products);
	}

}