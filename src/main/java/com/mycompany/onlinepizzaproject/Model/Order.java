package com.mycompany.onlinepizzaproject.Model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import java.util.Date;
import java.util.List;


public class Order {
    private ObjectId id;
    @BsonProperty(value="order_id")
    private Integer orderId;
    @BsonProperty(value="user_id")
    private Integer userId; //or Account user with all user information
    private Date date;
    private List<OrderLine> orderLines;
    private Double totalPrice;

    public Order() { }

    public Order(Integer orderId, Integer userId, Date date, List<OrderLine> orderLines, Double totalPrice) {
        this.orderId = orderId;
        this.userId = userId;
        this.date = date;
        this.orderLines = orderLines;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", date=" + date +
                ", orderLines=" + orderLines +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
