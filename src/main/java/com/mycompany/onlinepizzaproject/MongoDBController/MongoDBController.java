package com.mycompany.onlinepizzaproject.MongoDBController;

import com.mycompany.onlinepizzaproject.MainController;
import com.mycompany.onlinepizzaproject.Model.*;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Updates.*;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoDBController {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MainController mainController;
    private final static MongoDBController mongoDBController = new MongoDBController();

    private MongoDBController(){ initializeDBConnection(); }

    public static MongoDBController getMongoDBControllerInstance() {
        return mongoDBController;
    }

    private static void initializeDBConnection() {
        mainController = MainController.getMainControllerInstance();
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
        String connectStr = "mongodb+srv://Anna_a:mongodbtest20@clusterproject-2hdjy.mongodb.net/test?retryWrites=true&w=majority";
        ConnectionString connectionString = new ConnectionString(connectStr);
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        mongoClient = MongoClients.create(clientSettings);
        database = mongoClient.getDatabase("OnlinePizza");
    }


    public void initializeDatabase(){
        deleteCollections();
        createCollections();

        sendProducts();
        sendIngredients();
        //sendAccount(mainController.generateManager());
    }

    //DELETE COLLECTIONS
    private static void deleteCollections() {
        mongoClient.getDatabase("OnlinePizza").getCollection("Accounts").drop();
        mongoClient.getDatabase("OnlinePizza").getCollection("Ingredients").drop();
        mongoClient.getDatabase("OnlinePizza").getCollection("Products").drop();
        mongoClient.getDatabase("OnlinePizza").getCollection("Sales").drop();
        mongoClient.getDatabase("OnlinePizza").getCollection("Orders").drop();
    }

    //CREATE COLLECTIONS
    private static void createCollections() {
        mongoClient.getDatabase("OnlinePizza").createCollection("Accounts");
        mongoClient.getDatabase("OnlinePizza").createCollection("Products");
        mongoClient.getDatabase("OnlinePizza").createCollection("Ingredients");
        mongoClient.getDatabase("OnlinePizza").createCollection("Sales");
        mongoClient.getDatabase("OnlinePizza").createCollection("Orders");
    }

    //SEND PRODUCTS
    private static void sendProducts() {
        MongoCollection<Product> products = database.getCollection("Products", Product.class);
        try{
            //products.insertMany(mainController.generateProductCollection());//Products
        }catch (Exception e){ }
    }

    //GET PRODUCTS
    public List<Product> getProducts() {
        MongoCollection<Product> products = database.getCollection("Products", Product.class);
        try {
            List<Product> productList = products.find().into(new ArrayList<>());
            return productList;
        }catch(NullPointerException n){
            return null;
        }
    }

    //SEND INGREDIENTS
    private static void sendIngredients() {
        MongoCollection<Ingredient> ingredients = database.getCollection("Ingredients", Ingredient.class);
        //ingredients.insertMany(mainController.generateIngredientCollection());
    }

    //GET INGREDIENTS
    public List<Ingredient> getIngredients() {
        MongoCollection<Ingredient> ingredients = database.getCollection("Ingredients", Ingredient.class);
        try {
            List<Ingredient> ingredientList = ingredients.find().into(new ArrayList<>());
            return ingredientList;
        }catch(NullPointerException n){
            return null;
        }
    }

    //UPDATE INGREDIENT
    public boolean updateIngredinet(Ingredient ingredient, int quantity){
        if(ingredient != null){
            MongoCollection<Ingredient> ingredients = database.getCollection("Ingredients", Ingredient.class);
            UpdateResult updateResult = ingredients.updateOne(eq("ingredient_id", ingredient.getIngredientId()), set("quantity", quantity));
            System.out.println("\nResult : " + updateResult);
            return true;
        }else {
            System.out.println("Ingredient is null.");
            return false;
        }
    }

    //SEND ACCOUNT DOCUMENT
    public static boolean sendAccount(Account account) {
        MongoCollection<Account> accounts = database.getCollection("Accounts", Account.class);
        InsertOneResult result = accounts.insertOne(account);
        if(result.wasAcknowledged()){
            return true;
        }else{
            return false;
        }
    }

    //GET ACCOUNT DOCUMENT BY EMAIL AND PASSWORD
    public Account getAccount(String email, String password) {
        MongoCollection<Account> accounts = database.getCollection("Accounts", Account.class);
        Account account;
        try{
            account = accounts.find(and(eq("email", email), eq("password", password))).first();
            return account;
        }catch(NullPointerException n){
            return null;
        }
    }

    //GET ACCOUNT DOCUMENT BY EMAIL
    public boolean getAccount(String email) {
        MongoCollection<Account> accounts = database.getCollection("Accounts", Account.class);
        Account account = accounts.find(eq("email", email)).first();
        if(account == null){
            return false;
        }else{
            return true;
        }
    }

    //SEND ORDER DOCUMENT
    public boolean sendOrder(Order order) {
        if(order != null){
            MongoCollection<Order> orders = database.getCollection("Orders", Order.class);
            InsertOneResult result = orders.insertOne(order);
            if(result.wasAcknowledged()){
                return true;
            }
        }
        return false;
    }

    //PART OF SEND ORDER DOCUMENT
    public boolean sendSales(List<Sale> saleList){
        if(!saleList.isEmpty()){
            MongoCollection<Sale> sales = database.getCollection("Sales", Sale.class);
            InsertManyResult result = sales.insertMany(saleList);
            if(result.wasAcknowledged()){
                return true;
            }
        }
        return false;
    }

    //PART OF SEND ORDER DOCUMENT
    public void updateProduct(List<Sale> productsToUpdate){
        if(!productsToUpdate.isEmpty()){
            MongoCollection<Product> products = database.getCollection("Products", Product.class);
            for(Sale s: productsToUpdate) {
                if(!s.getProductCategory().equals("Pizza")){
                    UpdateResult updateResult = products.updateOne(eq("product_id", s.getProductId()), inc("quantity", -(s.getQuantity())));
                    System.out.println("\nResult : " + updateResult);
                }
            }
        }else {
            System.out.println("List is empty");
        }
    }

    //GET ORDERS
    public List<Order> getOrders(){
        MongoCollection<Order> orders = database.getCollection("Orders", Order.class);
        try {
            List<Order> ordertList = orders.find().into(new ArrayList<>());
            return ordertList;
        }catch(NullPointerException n){
            return null;
        }
    }

    //GET ORDERS FROM DATE TO DATE
    public List<Order> getOrders(Date from, Date to){
        MongoCollection<Order> orders = database.getCollection("Orders", Order.class);
        try {
            List<Order> ordertList = orders.find(and(lt("date", to),
                    gt("date", from))).into(new ArrayList<>());
            return ordertList;
        }catch(NullPointerException n){
            return null;
        }
    }

    //GET SALES
    public List<Sale> getSales(){
        MongoCollection<Sale> sales = database.getCollection("Sales", Sale.class);
        try {
            List<Sale> saletList = sales.find().into(new ArrayList<>());
            return saletList;
        }catch(NullPointerException n){
            return null;
        }
    }

    //GET SALES FROM DATE TO DATE
    public List<Sale> getSales(Date from, Date to){
        MongoCollection<Sale> sales = database.getCollection("Sales", Sale.class);
        try {
            List<Sale> saletList = sales.find(and(lt("date", to),
                    gt("date", from))).into(new ArrayList<>());
            return saletList;
        }catch(NullPointerException n){
            return null;
        }
    }

    //GET TOP SALES FROM DATE TO DATE
    public List<Document> getTopSales(Date from, Date to){
        MongoCollection<Document> sales = database.getCollection("Sales");
        try {
            Bson match = match(and(lt("date", to), gt("date", from)));
            Bson group = group("$product_id", sum("quantity", "$quantity"), Accumulators.first("productName", "$productName"), Accumulators.first("productName", "$productName"));
            Bson project = project(fields(computed("product_id", "$_id"), include("productName", "quantity")));
            Bson sort = sort(descending("quantity"));
            List<Document> saleDocumentList = sales.aggregate(Arrays.asList(match, group, project, sort)).into(new ArrayList<>());

            return saleDocumentList;
        }catch(NullPointerException n){
            return null;
        }
    }

}

