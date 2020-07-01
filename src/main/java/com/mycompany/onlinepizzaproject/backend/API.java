package com.mycompany.onlinepizzaproject.backend;

import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mycompany.onlinepizzaproject.backend.MongoDB.Collection;
import com.mycompany.onlinepizzaproject.backend.Order.PizzaOrder;
import com.mycompany.onlinepizzaproject.backend.Order.ProductOrder;
import com.mycompany.onlinepizzaproject.backend.Order.Status;
import com.mycompany.onlinepizzaproject.backend.Product.Category;
import com.mycompany.onlinepizzaproject.backend.User.AccessLevel;

/**
 * API used to communicate with the backend
 * 
 * @author André Hansson
 */
public class API {

	private static MongoDB mongo = MongoDB.getInstance();
	
	public static void createIndexes() {
		mongo.createIndexes(Collection.Pizza);
		mongo.createIndexes(Collection.Product);
	}
	
	public static void rebuildDB() {
		mongo.dropCollection(Collection.Pizza);
		mongo.dropCollection(Collection.Ingredient);
		Import.pizzasAndIngredients();
		mongo.dropCollection(Collection.Product);
		Import.products();
	}
	
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
	
	public static String createEmployee(String email, String password, String firstName, String lastName, String country, String city, String street, String postcode, String phoneNumber) throws Exception {
		return createUser(email, password, AccessLevel.Employee, firstName, lastName, country, city, street, postcode, phoneNumber);
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
		
		if(email.isBlank()) {
			throw new Exception("Email cannot be empty");
		}
		
		if(password.isBlank()) {
			throw new Exception("Password cannot be empty");
		}
		
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
		return new User(login(email, password));
	}
	
	public static String loginUserJSON(String email, String password) throws Exception {
		return login(email, password).toJson();
	}
	
	private static Document login(String email, String password) throws Exception {
		
		if(email.isBlank()) {
			throw new Exception("Email cannot be empty");
		}
		
		if(password.isBlank()) {
			throw new Exception("Password cannot be empty");
		}
		
		Document doc = mongo.findFirst("email", email, Collection.User);
		
		if(doc == null) {
			throw new Exception("Wrong email or password");
		}
		
		boolean correctPassword = Password.validatePassword(password, doc.getString("password"));
		
		if(!correctPassword) {
			throw new Exception("Wrong email or password");
		} else {
			return doc;
		}
	}
	
	
	// *************
	// *** Pizza ***
	// *************
	
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
		
		return pizzas;
	}
	
	public static ArrayList<String> getPizzasJSON() {
		return mongo.getAllInCollectionJSON(Collection.Pizza);
	}
	
	public static List<Pizza> searchPizzas(String text) {
		ArrayList<Document> docs = mongo.searchText(text, Collection.Pizza);
		
		List<Pizza> pizzas = new ArrayList<Pizza>();
		
		for (Document doc : docs) {
			pizzas.add(new Pizza(doc));
		}
		
		return pizzas;
	}
	
	public static String addPizza(Pizza pizza) throws Exception {
		Document p = mongo.findFirst("name", pizza.getName(), Collection.Pizza);
		
		if(p != null) {
			throw new Exception("Pizza already exists");
		}
		
		return mongo.insertDocument(pizza.toDocument(), Collection.Pizza);
	}
	
	public static void addPizzas(List<Document> pizzas) {		
		mongo.insertManyDocuments(pizzas, Collection.Pizza);
	}
	
	public static void deletePizza(Pizza pizza) {
		mongo.delete("name", pizza.getName(), Collection.Pizza);
	}
	
	public static void updatePizzaPrice(Pizza pizza, int newPrice) {
		mongo.updateDocument("name", pizza.getName(), new Document("price30cm", newPrice), Collection.Pizza);
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
	
	public static void addIngredients(List<Document> ingredients) {
		mongo.insertManyDocuments(ingredients, Collection.Ingredient);
	}
	
	public static List<Ingredient> getIngredients(){
		ArrayList<Document> docs = mongo.getAllInCollection(Collection.Ingredient);
		
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		for (Document doc : docs) {
			ingredients.add(new Ingredient(doc));
		}
		
		return ingredients;
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
	
	public static void updateIngredient(Ingredient ingredient) {
		mongo.updateDocument("name", ingredient.getName(), new Document("stock", ingredient.getStock().toString()), Collection.Ingredient);
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
		
		return products;
	}
	
	public static ArrayList<String> getProductsJSON(){
		return mongo.getAllInCollectionJSON(Collection.Product);
	}
	
	public static List<Product> getProducts(Category category) {
		ArrayList<Document> docs = mongo.findAll("category", category.toString(), Collection.Product);

		List<Product> products = new ArrayList<Product>();
		
		for (Document doc : docs) {
			products.add(new Product(doc));
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
	
	public static List<Product> searchProducts(String text) {
		ArrayList<Document> docs = mongo.searchText(text, Collection.Product);
		
		List<Product> products = new ArrayList<Product>();
		
		for (Document doc : docs) {
			products.add(new Product(doc));
		}
		
		return products;
	}
	
	public static List<Product> searchProducts(String text, Category category) {
		ArrayList<Document> docs = mongo.search("category", category.toString(), text, Collection.Product);
		
		List<Product> products = new ArrayList<Product>();
		
		for (Document doc : docs) {
			products.add(new Product(doc));
		}
		
		return products;
	}
	
	public static void addProducts(List<Document> products) {		
		mongo.insertManyDocuments(products, Collection.Product);
	}
	
	public static void deleteProduct(Product product) {
		mongo.delete("name", product.getName(), Collection.Product);
	}
	
	public static void updateProductPrice(Product product, int newPrice) {
		mongo.updateDocument("name", product.getName(), new Document("price", newPrice), Collection.Product);
	}
	
	public static void updateProductStock(Product product, int newStock) {
		mongo.updateDocument("name", product.getName(), new Document("stock", newStock), Collection.Product);
	}
	
	public static void updateProductPriceAndStock(Product product, int newPrice, int newStock) {
		mongo.updateDocument("name", product.getName(), new Document("price", newPrice).append("stock", newStock), Collection.Product);
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
	
		
	public static void modifyOrderStatus(Order order) {
		mongo.updateDocument(new ObjectId(order.getId()), new Document("status",order.getStatus().toString()), Collection.Order);		
	}
	
	public static List<Order> getOrders() {
		ArrayList<Document> docs = mongo.getAllInCollection(Collection.Order);
		
		ArrayList<Order> orders = new ArrayList<Order>();
		
		for (Document doc : docs) {
			orders.add(new Order(doc));			
		}
		
		return orders;
	}
	
	public static List<Order> getOrders(Date from, Date to) {
		ArrayList<Document> docs = mongo.findAll(from, to, Collection.Order);
		
		ArrayList<Order> orders = new ArrayList<Order>();
		
		for (Document doc : docs) {
			orders.add(new Order(doc));			
		}
		
		return orders;
	}
	
	public static List<Order> getOrders(Status status) {
		ArrayList<Document> docs = mongo.findAll("status", status.toString(), Collection.Order);

		List<Order> orders = new ArrayList<Order>();
		
		for (Document doc : docs) {
			orders.add(new Order(doc));
		}
		
		return orders;
	}
	
	public static List<Order> getOrders(Status status, Date from, Date to) {
		ArrayList<Document> docs = mongo.findAll("status", status.toString(), from, to, Collection.Order);

		List<Order> orders = new ArrayList<Order>();
		
		for (Document doc : docs) {
			orders.add(new Order(doc));
		}
		
		return orders;
	}
	
	public static List<Order> getOpenOrders() {
		Bson filter = or(eq("status", Status.placed.toString()), eq("status", Status.inProgress.toString()));
		ArrayList<Document> docs = mongo.findAll(filter, Collection.Order);

		List<Order> orders = new ArrayList<Order>();
		
		for (Document doc : docs) {
			orders.add(new Order(doc));
		}
		
		return orders;
	}
	
	public static List<Order> getOpenOrders(Date from, Date to) {
		Bson filter = or(eq("status", Status.placed.toString()), eq("status", Status.inProgress.toString()));
		ArrayList<Document> docs = mongo.findAll(filter, from, to, Collection.Order);

		List<Order> orders = new ArrayList<Order>();
		
		for (Document doc : docs) {
			orders.add(new Order(doc));
		}
		
		return orders;
	}
	
	public static List<Order> getClosedOrders() {
		Bson filter = or(eq("status", Status.done.toString()), eq("status", Status.cancelled.toString()));
		ArrayList<Document> docs = mongo.findAll(filter, Collection.Order);

		List<Order> orders = new ArrayList<Order>();
		
		for (Document doc : docs) {
			orders.add(new Order(doc));
		}
		
		return orders;
	}
	
	public static List<Order> getClosedOrders(Date from, Date to) {
		Bson filter = or(eq("status", Status.done.toString()), eq("status", Status.cancelled.toString()));
		ArrayList<Document> docs = mongo.findAll(filter, from, to, Collection.Order);

		List<Order> orders = new ArrayList<Order>();
		
		for (Document doc : docs) {
			orders.add(new Order(doc));
		}
		
		return orders;
	}
	
	public static List<Order> getOrders(String customer) {
		ArrayList<Document> docs = mongo.findAll("customer", customer, Collection.Order);
		
		ArrayList<Order> orders = new ArrayList<Order>();
		
		for (Document doc : docs) {
			orders.add(new Order(doc));
		}
		
		return orders;
	}
	
	public static ArrayList<String> getOrdersJSON(String customer){
		return mongo.findAllJSON("customer", customer, Collection.Order);
	}
	
	
	public static List<String> getTopSales() {
		return countTopSales(API.getOrders(Status.done));
	}
	
	public static List<String> getTopSales(Date from, Date to){
		return countTopSales(API.getOrders(Status.done, from, to));
	}
	
	private static List<String> countTopSales(List<Order> orders) {
		HashMap<String, Integer> top = new HashMap<String, Integer>();
		
		for (Order order : orders) {
			
			for (PizzaOrder pizzaOrder : order.getPizzas()) {
				String name = pizzaOrder.pizzaName;
				int count = top.containsKey(name) ? top.get(name) : 0;
				top.put(name, count + pizzaOrder.amount);
			}
			
			for (ProductOrder productOrder : order.getProducts()) {
				String name = productOrder.productName;
				int count = top.containsKey(name) ? top.get(name) : 0;
				top.put(name, count + productOrder.amount);
			}
			
		}
		
		List<String> topList = new ArrayList<>();
		
		for (Entry<String, Integer> entry : top.entrySet()) {
			topList.add(entry.getValue() + " : " + entry.getKey());
		}
		
		topList.sort(Comparator.comparing(s -> Integer.valueOf(s.substring(0, s.indexOf(' '))), Comparator.reverseOrder()));
		
		return topList;
	}
		
}