package com.mycompany.onlinepizzaproject.backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import com.mycompany.onlinepizzaproject.backend.Pizza.Size;


public class Order {
	
	public enum Status{
		placed,
		inProgress,
		done
	}

	public static class PizzaOrder{
		public Pizza pizza;
		public Size size;
		public int amount;
		
		public PizzaOrder(Pizza pizza, Size size, int amount) {
			this.pizza = pizza;
			this.size = size;
			this.amount = amount;
		}
		
		public PizzaOrder(Document doc) {
			try {
				this.pizza = API.getPizza(doc.getString("pizza"));
			} catch (Exception e) {
				//e.printStackTrace();
				// This pizza no longer exists
				this.pizza = new Pizza(doc.getString("pizza"), 0, 0, 0, "", "");
			}
			this.size = Pizza.getSizeFromString(doc.getString("size"));
			this.amount = doc.getInteger("amount");
		}
		
		public Document toDocument() {
			return new Document("pizza", pizza.getName()).append("size", Pizza.getSizeString(size)).append("amount", amount);
		}
		
		@Override
		public String toString() {
			return pizza.getName() + ": " + amount;
		}
	}
	
	public static class ProductOrder{
		public Product product;
		public int amount;
		
		public ProductOrder(Product product, int amount) {
			this.product = product;
			this.amount = amount;
		}
		
		public ProductOrder(Document doc) {
			try {
				this.product = API.getProduct(doc.getString("product"));
			} catch (Exception e) {
				//e.printStackTrace();
				// Product does not exist
				this.product = new Product(doc.getString("product"), null, 0, 0);
			}
			this.amount = doc.getInteger("amount");
		}
		
		public Document toDocument() {
			return new Document("product", product.getName()).append("amount", amount);
		}
		
		@Override
		public String toString() {
			return product.getName() + ": " + amount;
		}
	}
	
	private String customer;
	private Date date;
	private double totalPrice;
	private ArrayList<PizzaOrder> pizzas;
	private ArrayList<ProductOrder> products;
	private String comment;
	private Status status;
	private String id;
	

	public Order(String customer, ArrayList<PizzaOrder> pizzas, ArrayList<ProductOrder> products, String comment) {
		this.customer = customer;
		this.date = new Date();
		this.pizzas = pizzas;
		this.products = products;
		this.comment = comment;
		this.status = Status.placed;
		this.totalPrice = calculateTotalPrice();
	}
	
	public Order(String customer, Document pizzas, Document products, String comment) {
		this.customer = customer;
		this.date = new Date();
		
		//this.pizzas = pizzas;
		@SuppressWarnings("unchecked")
		List<Document> pizzaDocs = pizzas.get("pizzas", List.class);
		this.pizzas = new ArrayList<PizzaOrder>();
		for (Document document : pizzaDocs) {
			this.pizzas.add(new PizzaOrder(document));
		}
		
		//this.products = products;
		@SuppressWarnings("unchecked")
		List<Document> productDocs = products.get("products", List.class);
		this.products = new ArrayList<ProductOrder>();
		for (Document document : productDocs) {
			this.products.add(new ProductOrder(document));
		}
		
		this.comment = comment;
		this.status = Status.placed;
		this.totalPrice = calculateTotalPrice();
	}
	
	public Order(String customer, Date date, double totalPrice, ArrayList<PizzaOrder> pizzas, ArrayList<ProductOrder> products, String comment, Status status) {
		this.customer = customer;
		this.date = date;
		this.totalPrice = totalPrice;
		this.pizzas = pizzas;
		this.products = products;
		this.comment = comment;
		this.status = status;
	}
	
	public Order(Document doc) {
		this.customer = doc.getString("customer");
		this.date = doc.getDate("date");
		this.totalPrice = doc.getDouble("totalPrice");
		this.id = doc.get("_id").toString();
		//this.pizzas = pizzas;
		@SuppressWarnings("unchecked")
		List<Document> pizzaDocs = doc.get("pizzas", List.class);
		this.pizzas = new ArrayList<PizzaOrder>();
		for (Document document : pizzaDocs) {
			pizzas.add(new PizzaOrder(document));
		}
		
		//this.products = products;
		@SuppressWarnings("unchecked")
		List<Document> productDocs = doc.get("products", List.class);
		this.products = new ArrayList<ProductOrder>();
		for (Document document : productDocs) {
			products.add(new ProductOrder(document));
		}
		
		this.comment = doc.getString("comment");
		this.status = Status.valueOf(doc.getString("status"));
	}
	
	public Order(String json) {
		Document doc = Document.parse(json);
		
		this.customer = doc.getString("customer");
		this.date = new Date();
		
		//this.pizzas = pizzas;
		@SuppressWarnings("unchecked")
		List<Document> pizzaDocs = doc.get("pizzas", List.class);
		this.pizzas = new ArrayList<PizzaOrder>();
		for (Document document : pizzaDocs) {
			pizzas.add(new PizzaOrder(document));
		}
		
		//this.products = products;
		@SuppressWarnings("unchecked")
		List<Document> productDocs = doc.get("products", List.class);
		this.products = new ArrayList<ProductOrder>();
		for (Document document : productDocs) {
			products.add(new ProductOrder(document));
		}
		
		this.totalPrice = calculateTotalPrice();
		
		this.comment = doc.getString("comment");
		this.status = Status.placed;
	}

	public String getId() {
		return this.id;
	}
	
	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public double calculateTotalPrice() {
		double total = 0;
		
		for (PizzaOrder p : pizzas) {
			total += p.pizza.getPriceForSize(p.size) * p.amount;
		}
		
		for (ProductOrder p : products) {
			total += p.product.getPrice() * p.amount;
		}
		
		return total;
	}

	
	public ArrayList<PizzaOrder> getPizzas() {
		return pizzas;
	}

	public void setPizzas(ArrayList<PizzaOrder> pizzas) {
		this.pizzas = pizzas;
	}

	public void addPizza(Pizza pizza, Size size, int amount) {
		pizzas.add(new PizzaOrder(pizza, size, amount));
	}
	
	
	public ArrayList<ProductOrder> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<ProductOrder> products) {
		this.products = products;
	}

	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	public Document toDocument() {
		Document doc = new Document("customer", customer)
				.append("_id",id)
				.append("date", date)
				.append("totalPrice", totalPrice);
		
		ArrayList<Document> pizzaDocs = new ArrayList<>();
		for (PizzaOrder p : pizzas) {
			pizzaDocs.add(p.toDocument());
		}
		doc.append("pizzas", pizzaDocs);
		
		ArrayList<Document> productDocs = new ArrayList<>();
		for (ProductOrder p : products) {
			productDocs.add(p.toDocument());
		}
		doc.append("products", productDocs);
		
		doc.append("comment", comment)
		.append("status", status.toString());
		
		return doc;
	}
	
}