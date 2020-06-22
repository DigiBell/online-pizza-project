package com.mycompany.onlinepizzaproject.backend;

import static spark.Spark.*;

import org.bson.Document;

import spark.Spark;

/**
 * REST API using Spark
 * 
 * @author André Hansson
 */
public class REST {

	public REST() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			Spark.stop();
		}));
	}

	public void start() {
		staticFileLocation("/www");
		port(4567);
		ipAddress("localhost");//changed to localhost from 0.0.0.0 lukas
		after((request, response) -> {
			response.header("Access-Control-Allow-Methods", "*"); // Does not matter ?
			response.header("Access-Control-Allow-Origin", "*");
			//this has to be set individually for every route since not all is json lukas
			//response.header("Content-Type", "application/json");
		});


		post("/customer","application/json", (req, res) -> {
			res.header("Content-Type", "application/json");
			// Using Document to parse JSON
			Document doc = Document.parse(req.body());
			try {
				String result = API.createCustomer(doc.get("email").toString(), doc.get("password").toString(),
						doc.get("firstName").toString(), doc.get("lastName").toString(), doc.get("country").toString(),
						doc.get("city").toString(), doc.get("street").toString(), doc.get("postcode").toString(),
						doc.get("phoneNumber").toString());
							
				if(result != null) {
					res.status(201);
				} else {
					res.status(500);
				}
				
				return result;
			} catch (Exception e) {
				res.status(409);
				return e.getMessage();
			}
			
		});
		
		get("/login/:email/:password", (req, res) -> {
			res.header("Content-Type", "application/json");

			String user = "";
			try {
				user = API.loginUserJSON(req.params(":email"), req.params(":password")); //.toDocument();				
			} catch (Exception e2) {
				res.status(401);
				return e2.getMessage();
			}
			
			res.status(200);
			return user;
		});
		
		
		get("/pizza","application/json", (req, res) -> {
			res.header("Content-Type", "application/json");
			return API.getPizzasJSON();
		});
		
		get("/product","application/json", (req, res) -> {
			res.header("Content-Type", "application/json");
			return API.getProductsJSON();
		});
		
		get("/product/:category","application/json", (req, res) -> {
			res.header("Content-Type", "application/json");
			try {
				return API.getProductsJSON(req.params(":category"));
			} catch (IllegalArgumentException e) {
				res.status(400);
				return e.getMessage();
			}
					
		});

		//added this for root webpage Lukas
		get("/","text/html", (req, res) -> {
			return "OK";
		});
		
	
		post("/order", "application/json", (req, res) -> {
			res.header("Content-Type", "application/json");
			return API.placeOrder(req.body());
		});
		
		get("/orders/:customer", (req, res) -> {
			res.header("Content-Type", "application/json");
			return API.getOrdersJSON(req.params(":customer"));
		});

	}

	
	public static void main(String[] args) {
		new REST().start();
	}

}