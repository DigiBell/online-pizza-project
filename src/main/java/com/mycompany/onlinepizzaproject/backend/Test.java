package com.mycompany.onlinepizzaproject.backend;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;

import com.mycompany.onlinepizzaproject.backend.Measurement.Unit;
import com.mycompany.onlinepizzaproject.backend.Order.PizzaOrder;
import com.mycompany.onlinepizzaproject.backend.Order.ProductOrder;
import com.mycompany.onlinepizzaproject.backend.Product.Category;


public class Test {
	
	public static void addPizzas() {
		HashMap<String, Measurement> ingredients = new HashMap<String, Measurement>();
		ingredients.put("Cheese", new Measurement(50, Unit.g));
		ingredients.put("Tomato sause", new Measurement(75, Unit.g));
		
		try {
			API.addPizza(new Pizza("01 DEN ENKLA", 49, 79, 119, 139, "Ost, tomats�s � och bara det!", "Cheese and tomato sauce � and nothing else!", ingredients));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		ingredients = new HashMap<>();
		ingredients.put("Cheese", new Measurement(50, Unit.g));
		ingredients.put("Tomato sause", new Measurement(75, Unit.g));
		ingredients.put("Ham", new Measurement(40, Unit.g));
		ingredients.put("Mushroom", new Measurement(40, Unit.g));
				
		try {
			API.addPizza(new Pizza("02 SVINGOD", 59, 99, 155, 139, "Ost, tomats�s, skinka och champinjoner", "Cheese, tomato sauce, ham and mushroom", ingredients));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void addProducts() {
		
		Product[] products = {new Product("Ben and jerry's Half Baked", Category.iceCream, 79, 12),
				new Product("Ben and jerry's Cone Together", Category.iceCream, 79, 10),
				new Product("COCA COLA 33 CL", Category.beverage, 15, 50),
				new Product("COKE ZERO 33 CL", Category.beverage, 15, 40),
				new Product("Kebab", Category.sause, 8, 27),
				new Product("Chili bearnaise", Category.sause, 8, 19),
				new Product("Kycklingklubbor 6 st", Category.sideDish, 69, 8),
				new Product("Kycklingklubbor 3 st", Category.sideDish, 39, 19)};
		
		for (Product product : products) {
			try {
				API.addProduct(product);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}	
	}
	
	public static void placeOrder() {
		
		ArrayList<PizzaOrder> pizzas = new ArrayList<>();
		pizzas.add(new PizzaOrder(new Document("pizza", "01 DEN ENKLA").append("size", "20cm").append("amount", 2)));
		pizzas.add(new PizzaOrder(new Document("pizza", "02 SVINGOD").append("size", "30cm").append("amount", 1)));
		
		ArrayList<ProductOrder> products = new ArrayList<>();
		products.add(new ProductOrder(new Document("product", "COCA COLA 33 CL").append("amount", 3)));
		products.add(new ProductOrder(new Document("product", "Ben and jerry's Half Baked").append("amount", 1)));
				
		API.placeOrder("test@mau.se", pizzas, products, "Test comment");
		
	}
	
	public static void ingredientsBelow() {
		ArrayList<Ingredient> ingredients = API.getIngredientsWithStockBelow(new Measurement(12, Unit.kg));
		
		for (Ingredient ingredient : ingredients) {
			System.out.println(ingredient.toDocument().toJson());
		}
	}

	public static void main(String[] args) {
		ingredientsBelow();
	}
	
}
