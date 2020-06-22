package com.mycompany.onlinepizzaproject.backend;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mycompany.onlinepizzaproject.backend.MongoDB.Collection;
import com.mycompany.onlinepizzaproject.backend.Order.PizzaOrder;
import com.mycompany.onlinepizzaproject.backend.Order.ProductOrder;
import com.mycompany.onlinepizzaproject.backend.Product.Category;
import com.mycompany.onlinepizzaproject.backend.User.AccessLevel;

/**
 * API used to communicate with the backend
 * 
 * @author André Hansson
 */
public class API {

	private static MongoDB mongo = MongoDB.getInstance();
	
	/**
	 * Create a customer
	 * @param email
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param country
	 * @param city
	 * @param street
	 * @param postcode
	 * @param phoneNumber
	 * @return String The _id of the document in MongoDB or null if not successful 
	 * @throws Exception If the user already exists
	 */
	public static String createCustomer(String email, String password, String firstName, String lastName, String country, String city, String street, String postcode, String phoneNumber) throws Exception {
		return createUser(email, password, AccessLevel.Customer, firstName, lastName, country, city, street, postcode, phoneNumber);
	}
	
	/**
	 * Create a user
	 * @param email
	 * @param password
	 * @param accessLevel
	 * @param firstName
	 * @param lastName
	 * @param country
	 * @param city
	 * @param street
	 * @param postcode
	 * @param phoneNumber
	 * @return String The _id of the document in MongoDB or null if not successful 
	 * @throws Exception If the user already exists
	 */
	public static String createUser(String email, String password, AccessLevel accessLevel, String firstName, String lastName, String country, String city, String street, String postcode, String phoneNumber) throws Exception {
		
		//WTF- ??????????
		/*
		if(email.isBlank()) {
			throw new Exception("Email cannot be empty");
		}
		
		if(password.isBlank()) {
			throw new Exception("Password cannot be empty");
		}
		*/
		// Check if user already exists
		Document doc = mongo.findFirst("email", email, Collection.User);
		
		if(doc != null) {
			throw new Exception("User already exists");
		}
		
		String hashedPassword = Password.generatePasswordHash(password);
		
		User user = new User(email, hashedPassword, accessLevel, firstName, lastName, new Address(country, city, street, postcode), phoneNumber);
		return mongo.insertDocument(user.toDocument(), Collection.User);
	}
	
	/**
	 * Login a user
	 * @param email The users email
	 * @param password The users password
	 * @return The User instance
	 * @throws Exception When email or password is wrong
	 */
	public static User loginUser(String email, String password) throws Exception {
		
		Document doc = mongo.findFirst("email", email, Collection.User);
		
		if(doc == null) {
			throw new Exception("Wrong email or password");
		}
		
		boolean correctPassword = Password.validatePassword(password, doc.getString("password"));
		
		if(!correctPassword) {
			throw new Exception("Wrong email or password");
		} else {
			return new User(doc);
		}
	}
	
	public static String loginUserJSON(String email, String password) throws Exception {
		
		Document doc = mongo.findFirst("email", email, Collection.User);
		
		if(doc == null) {
			throw new Exception("Wrong email or password");
		}
		
		boolean correctPassword = Password.validatePassword(password, doc.getString("password"));
		
		if(!correctPassword) {
			throw new Exception("Wrong email or password");
		} else {
			return doc.toJson();
		}
	}
	
	
	// ***********
	// ** Pizza **
	// ***********
	
	public static Pizza getPizza(String pizzaName) throws Exception{	
		Document doc = mongo.findFirst("name", pizzaName, Collection.Pizza);	
		if(doc == null) {
			throw new Exception("No pizza with name: " + pizzaName);
		}	
		return new Pizza(doc);
	}
	
	public static List<Pizza> getPizzas() {
		ArrayList<Document> docs = mongo.getAllInCollection(Collection.Pizza);
		
		List<Pizza> pizzas = new ArrayList<Pizza>();
		
		for (Document doc : docs) {
			pizzas.add(new Pizza(doc));
		}
		
//		for (int i = 0; i < pizzas.length; i++) {
//			pizzas[i] = new Pizza(docs.get(i));
//		}
		
		return pizzas;
	}
	
	public static ArrayList<String> getPizzasJSON() {
		return mongo.getAllInCollectionJSON(Collection.Pizza);
	}
	
	public static String addPizza(Pizza pizza) throws Exception {
		Document p = mongo.findFirst("name", pizza.getName(), Collection.Pizza);
		
		if(p != null) {
			throw new Exception("Pizza already exists");
		}
		
		return mongo.insertDocument(pizza.toDocument(), Collection.Pizza);
	}
	
	public static void addPizzas(List<Document> pizzas) {
		System.out.println("Pizzas: " + pizzas.size());
		
		int i = mongo.insertManyDocuments(pizzas, Collection.Pizza);
		
		System.out.println("inserted: " + i);
	}
	
	
	// ****************
	// ** Ingredient **
	// ****************
	
	public static Ingredient getIngredient(String ingredientName) throws Exception {
		Document doc = mongo.findFirst("name", ingredientName, Collection.Ingredient);
		if(doc == null) {
			throw new Exception("No ingredient with name: " + ingredientName);
		}
		return new Ingredient(doc);
	}
	
	public static String addIngredient(Ingredient ingredient) throws Exception {
		Document doc = mongo.findFirst("name", ingredient.getName(), Collection.Ingredient);
		
		if(doc != null) {
			throw new Exception("Ingredient already exist");
		}
		
		return mongo.insertDocument(ingredient.toDocument(), Collection.Ingredient);
	}
	
	public static ArrayList<Ingredient> getIngredientsWithStockBelow(Measurement measurement) {
		ArrayList<Ingredient> ingredients = new ArrayList<>();
		
		ArrayList<Document> docs = mongo.getAllInCollection(Collection.Ingredient);
		
		for (Document doc : docs) {
			Ingredient ingr = new Ingredient(doc);
			
			if(ingr.getStock().compareTo(measurement) <= 0) {
				ingredients.add(ingr);
			}
		}
		
		return ingredients;
	}
	
	// *************
	// ** Product **
	// *************
	
	public static Product getProduct(String productName) throws Exception {
		Document doc = mongo.findFirst("name", productName, Collection.Product);
		if(doc == null) {
			throw new Exception("No product with name: " + productName);
		}
		return new Product(doc);
	}
	
	public static List<Product> getProducts() {
		ArrayList<Document> docs = mongo.getAllInCollection(Collection.Product);
		
		List<Product> products = new ArrayList<Product>();
		
		for (Document doc : docs) {
			products.add(new Product(doc));
		}
		
//		for (int i = 0; i < products.length; i++) {
//			products[i] = new Product(docs.get(i));
//		}
		
		return products;
	}
	
	public static ArrayList<String> getProductsJSON(){
		return mongo.getAllInCollectionJSON(Collection.Product);
	}
	
	public static Product[] getProducts(Category category) {
		ArrayList<Document> docs = mongo.findAll("category", category.toString(), Collection.Product);
		
		Product[] products = new Product[docs.size()];
		
		for (int i = 0; i < products.length; i++) {
			products[i] = new Product(docs.get(i));
		}
		
		return products;
	}
	
	public static ArrayList<String> getProductsJSON(String categoryStr) throws IllegalArgumentException {
		Category category = Category.valueOf(categoryStr);		
		return mongo.findAllJSON("category", category.toString(), Collection.Product);
	}
		
	public static void addProduct(Product product) throws Exception {
		
		Document p = mongo.findFirst("name", product.getName(), Collection.Product);
		
		if(p != null) {
			throw new Exception("Product already exists");
		}
		
		
		mongo.insertDocument(product.toDocument(), Collection.Product);
	}
	
	public static void addProducts(List<Document> products) {
		System.out.println("Products: " + products.size());
		
		int i = mongo.insertManyDocuments(products, Collection.Product);
		
		System.out.println("inserted: " + i);
	}
	
	
	// ***********
	// ** Order **
	// ***********
	
	public static void placeOrder(String customer, ArrayList<PizzaOrder> pizzas, ArrayList<ProductOrder> products, String comment) {
		Order order = new Order(customer, pizzas, products, comment);
		mongo.insertDocument(order.toDocument(), Collection.Order);
	}
	
	public static String placeOrder(String json) {
		return mongo.insertDocument(new Order(json).toDocument(), Collection.Order);
	}
	
	public static Order[] getOrders() {
		ArrayList<Document> docs = mongo.getAllInCollection(Collection.Order);
		
		Order[] orders = new Order[docs.size()];
		
		for (int i = 0; i < orders.length; i++) {
			orders[i] = new Order(docs.get(i));
		}
		
		return orders;
	}
	
	public static Order[] getOrders(String customer) {
		ArrayList<Document> docs = mongo.findAll("customer", customer, Collection.Order);
		
		Order[] orders = new Order[docs.size()];
		
		for (int i = 0; i < orders.length; i++) {
			orders[i] = new Order(docs.get(i));
		}
		
		return orders;
	}
	
	public static ArrayList<String> getOrdersJSON(String customer){
		return mongo.findAllJSON("customer", customer, Collection.Order);
	}
	
}