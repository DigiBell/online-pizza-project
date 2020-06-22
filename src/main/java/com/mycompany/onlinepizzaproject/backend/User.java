package com.mycompany.onlinepizzaproject.backend;

import org.bson.Document;

public class User {

	public enum AccessLevel {
		Customer, 
		Employee, 
		Manager
	}

	private String email;
	private String password;
	private AccessLevel accessLevel;
	private String firstName;
	private String lastName;
	private Address address;
	private String phoneNumber;

	public User(String email, String password, AccessLevel accessLevel, String firstName, String lastName, Address address, String phoneNumber) {
		this.email = email;
		this.password = password;
		this.accessLevel = accessLevel;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
	
	public User(Document doc) {
		this.email = doc.getString("email");
		this.password = doc.getString("password");
		this.accessLevel = AccessLevel.valueOf(doc.getString("accessLevel"));
		this.firstName = doc.getString("firstName");
		this.lastName = doc.getString("lastName");
		this.address = new Address(doc.get("address", Document.class));
		this.phoneNumber = doc.getString("phoneNumber");
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AccessLevel getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(AccessLevel accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Document toDocument() {
		Document doc = new Document("email", email)
				.append("password", password)
				.append("accessLevel", accessLevel.toString())
				.append("firstName", firstName)
				.append("lastName", lastName)
				.append("address", address.toDocument())
				.append("phoneNumber", phoneNumber);
		
		return doc;
	}

}
