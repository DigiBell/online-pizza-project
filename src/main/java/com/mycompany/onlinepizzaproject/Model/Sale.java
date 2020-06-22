package com.mycompany.onlinepizzaproject.Model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import java.util.Date;

public class Sale {
    private ObjectId id;
    @BsonProperty(value="product_id")
    private Integer productId;
   @BsonProperty(value="order_id")
    private Integer orderId;
    private String productName;
    private String productCategory;
    private Date date;
    private Integer quantity;
    private Double price;

    public Sale(){}

    public Sale(Integer productId, Integer orderId, String productName, String productCategory, Date date, Integer quantity, Double price) {
        this.productId = productId;
        this.orderId = orderId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.date = date;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", productId=" + productId +
                ", orderId=" + orderId +
                ", productName='" + productName + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", date=" + date +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
