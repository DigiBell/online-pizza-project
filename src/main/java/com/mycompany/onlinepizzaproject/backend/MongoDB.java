package com.mycompany.onlinepizzaproject.backend;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;

import com.mongodb.client.result.UpdateResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the connection to MongoDB
 * 
 * @author Andr� Hansson
 */
public class MongoDB {

	private static final String DATABASE = "testDB";
	
	public enum Collection {
		User,
		Customer,
		Employee,
		Order,
		Product,
		Pizza,
		Ingredient
	}
	
	private static MongoDB instance = null;
	
	private MongoClient mongoClient;
	private MongoDatabase database;

	private MongoDB() {

		mongoClient = MongoClients.create("mongodb+srv://dbAdmin:9tchJRNYFeoMBAV8@cluster0-7jc29.mongodb.net/test?retryWrites=true&w=majority");
		database = mongoClient.getDatabase(DATABASE);

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			mongoClient.close();
		}));
		
	}
	
	public static MongoDB getInstance() {
		if (instance == null) {
			instance = new MongoDB();
		}
		return instance;
	}
	
	public void createIndexes(Collection col) {
		MongoCollection<Document> collection = database.getCollection(col.toString());
		collection.createIndex(Indexes.text());
	}
	
	/**
	 * Adds a document to the collection
	 * @param doc The Document
	 * @param col The Collection
	 * @return _id of the inserted document if available, otherwise null
	 */
	public String insertDocument(Document doc, Collection col) {
		MongoCollection<Document> collection = database.getCollection(col.toString());
		InsertOneResult result = collection.insertOne(doc);		
		
		if(result.wasAcknowledged()) {
			return result.getInsertedId().asObjectId().getValue().toString();
		} else {
			return null;
		}		
	}
	
	public int insertManyDocuments(List<Document> documents, Collection col) {
		MongoCollection<Document> collection = database.getCollection(col.toString());
		InsertManyResult result = collection.insertMany(documents);
		
		if(result.wasAcknowledged()) {
			return result.getInsertedIds().size();
		} else {
			return 0;
		}
	}
	
	public void updateDocument(String key, String value, Document doc, Collection col) {
		MongoCollection<Document> collection = database.getCollection(col.toString());		
		UpdateResult result = collection.updateOne(eq(key, value), new Document("$set", doc));
		System.out.println(result);
	}
	
	
	
	
	/**
	 * Gets all the documents that are in the collection
	 * @param col The collection
	 * @return An ArrayList of the documents
	 */
	public ArrayList<Document> getAllInCollection(Collection col) {			
		MongoCollection<Document> collection = database.getCollection(col.toString());
		return collection.find().into(new ArrayList<Document>());
	}
	
	/**
	 * Gets all the documents that are in the collection
	 * @param col The collection
	 * @return An ArrayList of the JSON data in the documents
	 */
	public ArrayList<String> getAllInCollectionJSON(Collection col) {
		
		ArrayList<String> docs = new ArrayList<String>();
		
		MongoCollection<Document> collection = database.getCollection(col.toString());
		
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
		    while (cursor.hasNext()) {
		        docs.add(cursor.next().toJson());
		    }
		} finally {
		    cursor.close();
		}
		
		return docs;
	}
	
	public Document findFirst(String key, String value, Collection col) {
		
		MongoCollection<Document> collection = database.getCollection(col.toString());
		
		return collection.find(eq(key, value)).first();
	}
	
	public ArrayList<Document> findAll(String key, String value, Collection col) {		
		MongoCollection<Document> collection = database.getCollection(col.toString());
		return collection.find(eq(key, value)).into(new ArrayList<Document>());	
	}
	
	public ArrayList<String> findAllJSON(String key, String value, Collection col){		
		
		ArrayList<String> docs = new ArrayList<String>();
		
		MongoCollection<Document> collection = database.getCollection(col.toString());
		
		MongoCursor<Document> cursor = collection.find(eq(key, value)).iterator();
		try {
		    while (cursor.hasNext()) {
		        docs.add(cursor.next().toJson());
		    }
		} finally {
		    cursor.close();
		}
		
		return docs;		
	}
	
	public ArrayList<Document> searchText(String text, Collection col){
		MongoCollection<Document> collection = database.getCollection(col.toString());
		return collection.find(Filters.text(text)).into(new ArrayList<Document>());
	}
	
	public ArrayList<Document> search(String key, String value, String text, Collection col){
		MongoCollection<Document> collection = database.getCollection(col.toString());
		return collection.find( and( eq(key, value), Filters.text(text) ) ).into(new ArrayList<Document>());
	}

	public boolean delete(String key, String value, Collection col) {
		MongoCollection<Document> collection = database.getCollection(col.toString());		
		return collection.deleteOne(eq(key, value)).wasAcknowledged();
	}
	
}
