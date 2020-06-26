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
		done,
		cancelled
	}

	public static class PizzaOrder{
		public String pizzaName;
		public Size size;
		public int amount;
		public int unitPrice;
		
		public PizzaOrder(Pizza pizza, Size size, int amount) {
			this.pizzaName = pizza.getName();
			this.size = size;
			this.amount = amount;
			this.unitPrice = pizza.getPriceForSize(size);
		}
		
		public PizzaOrder(Document doc, boolean getPizza) {
			
			this.pizzaName = doc.getString("pizza");
			this.size = Pizza.getSizeFromString(doc.getString("size"));
			this.amount = doc.getInteger("amount");
			
			if(getPizza) {
				try {	
					Pizza pizza = API.getPizza(pizzaName);
					this.unitPrice = pizza.getPriceForSize(size);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {				
				try {
					this.unitPrice = doc.getInteger("unitPrice");
				} catch (NullPointerException e) {
					// OLD Order. Ignore exception. 
				}
			}
			
//			try {
//				this.pizzaName = API.getPizza(doc.getString("pizza"));
//			} catch (Exception e) {
//				//e.printStackTrace();
//				// This pizza no longer exists
//				this.pizzaName = new Pizza(doc.getString("pizza"), 0, 0, 0, "", "");
//			}

//			this.pizzaName = doc.getString("pizza");
//			this.size = Pizza.getSizeFromString(doc.getString("size"));
//			this.amount = doc.getInteger("amount");
//			
//			try {
//				this.unitPrice = doc.getInteger("unitPrice");
//			} catch (NullPointerException e) {
//				// OLD Order. Ignore exception. 
//			}
			
		}
		
		public Document toDocument() {
			return new Document("pizza", pizzaName).append("size", Pizza.getSizeString(size)).append("amount", amount).append("unitPrice", unitPrice);
		}
		
		@Override
		public String toString() {
			return "Pizza: " + pizzaName + " " + Pizza.getSizeString(size) + ". Amount: " + amount + ". Unit price: " + unitPrice;
		}
	}
	
	public static class ProductOrder{
		public String productName;
		public int amount;
		public int unitPrice;
		
		public ProductOrder(Product product, int amount) {
			this.productName = product.getName();
			this.amount = amount;
			this.unitPrice = product.getPrice();
		}
		
		public ProductOrder(Document doc, boolean getProduct) {
//			try {
//				this.productName = API.getProduct(doc.getString("product"));
//			} catch (Exception e) {
//				//e.printStackTrace();
//				// Product does not exist
//				this.productName = new Product(doc.getString("product"), null, 0, 0);
//			}
			this.productName = doc.getString("product");
			this.amount = doc.getInteger("amount");
			
			if(getProduct) {
				try {
					Product product = API.getProduct(productName);
					this.unitPrice = product.getPrice();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					this.unitPrice = doc.getInteger("unitPrice");
				} catch (NullPointerException e) {
					// OLD Order. Ignore exception. 
				}
			}
			
				
			
		}
		
		public Document toDocument() {
			return new Document("product", productName).append("amount", amount).append("unitPrice", unitPrice);
		}
		
		@Override
		public String toString() {
			return "Product: " + productName + ". Amount: " + amount + ". Unit price: " + unitPrice;
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
			this.pizzas.add(new PizzaOrder(document, false));
		}
		
		//this.products = products;
		@SuppressWarnings("unchecked")
		List<Document> productDocs = products.get("products", List.class);
		this.products = new ArrayList<ProductOrder>();
		for (Document document : productDocs) {
			this.products.add(new ProductOrder(document, false));
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
			pizzas.add(new PizzaOrder(document, false));
		}
		
		//this.products = products;
		@SuppressWarnings("unchecked")
		List<Document> productDocs = doc.get("products", List.class);
		this.products = new ArrayList<ProductOrder>();
		for (Document document : productDocs) {
			products.add(new ProductOrder(document, false));
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
			pizzas.add(new PizzaOrder(document, true));
		}
		
		//this.products = products;
		@SuppressWarnings("unchecked")
		List<Document> productDocs = doc.get("products", List.class);
		this.products = new ArrayList<ProductOrder>();
		for (Document document : productDocs) {
			products.add(new ProductOrder(document, true));
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
			//total += p.pizzaName.getPriceForSize(p.size) * p.amount;
			total += p.unitPrice * p.amount;
		}
		
		for (ProductOrder p : products) {
			//total += p.productName.getPrice() * p.amount;
			total += p.unitPrice * p.amount;
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