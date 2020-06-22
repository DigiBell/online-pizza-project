package com.mycompany.onlinepizzaproject.Model;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class OrderLine {
    @BsonProperty(value="product_id")
    private Integer productId;
    private String productName;
    private String productCategory;
    private String details;
    private String comment;
    private Double totalPrice;

    public OrderLine() { }

    public OrderLine(Integer productId, String productName, String productCategory, String details, String comment, Double totalPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.details = details;
        this.comment = comment;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        String str ="{ id=" + productId +
                ", name='" + productName + '\'' +
                ", category='" + productCategory + '\'' +
                ", details='" + details + '\'' ;
        if(comment != null){
            str = str + ", comment='" + comment + '\'';
        }
        str = str + ", price=" + totalPrice + "kr }";
        return str;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
