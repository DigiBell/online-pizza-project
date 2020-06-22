package com.mycompany.onlinepizzaproject.backend;

import org.bson.Document;

public class Address {

	private String country;
	private String city;
	private String street;
	private String postcode;

	public Address(String country, String city, String street, String postcode) {
		this.country = country;
		this.city = city;
		this.street = street;
		this.postcode = postcode;
	}
	
	public Address(Document doc) {
		this.country = doc.getString("country");
		this.city = doc.getString("city");
		this.street = doc.getString("street");
		this.postcode = doc.getString("postcode");
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Document toDocument() {
		Document doc = new Document("country", country)
				.append("city", city)
				.append("street", street)
				.append("postcode", postcode);

		return doc;
	}

	@Override
	public String toString() {
		String string = street + ", " + postcode + ", " + city + ", " + country;
		return string;
	}

}
