package com.mycompany.onlinepizzaproject.Model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import java.util.Date;



public class Account {
    private ObjectId id;
    @BsonProperty(value="user_id")
    private Integer userId;
    private String email;
    private String password;
    private String access;
    private Date dateCreated;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String street;
    private String postCode;
    private String phoneNumber;


    public Account(){ }

    public Account(Integer userId, String email, String password, String access, Date dateCreated, String firstName,
                   String lastName, String country, String city, String street, String postCode, String phoneNumber) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.access = access;
        this.dateCreated = dateCreated;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.city = city;
        this.street = street;
        this.postCode = postCode;
        this.phoneNumber = phoneNumber;
    }

    public Account(Account anotherAccount){
        this.userId = anotherAccount.userId;
        this.email = anotherAccount.email;
        this.password = anotherAccount.password;
        this.access = anotherAccount.access;
        this.dateCreated = anotherAccount.dateCreated;
        this.firstName = anotherAccount.firstName;
        this.lastName = anotherAccount.lastName;
        this.country = anotherAccount.country;
        this.city = anotherAccount.city;
        this.street = anotherAccount.street;
        this.postCode = anotherAccount.postCode;
        this.phoneNumber = anotherAccount.phoneNumber;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", access='" + access + '\'' +
                ", dateCreated=" + dateCreated +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", postCode='" + postCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
