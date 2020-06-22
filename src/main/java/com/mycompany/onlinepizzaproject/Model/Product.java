package com.mycompany.onlinepizzaproject.Model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Product {
    private ObjectId id;
    @BsonProperty(value="product_id")
    private Integer productId;
    private String category;
    private String name;
    private String description;
    private String size;
    private String units;
    private Integer price;
    private Boolean available;
    private Integer quantity;

    public Product(){}

    public Product(Integer productId, String category, String name, String description, String size, String units,
                   Integer price, Boolean available, Integer quantity) {
        this.productId = productId;
        this.category = category;
        this.name = name;
        this.description = description;
        this.size = size;
        this.units = units;
        this.price = price;
        this.available = available;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productId=" + productId +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", size=" + size +
                ", units='" + units + '\'' +
                ", price=" + price +
                ", available=" + available +
                ", quantity=" + quantity +
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        if(size == null){
            return "";
        }else{
            return size;
        }
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUnits() {
        if(units == null){
            return "";
        }else{
            return units;
        }
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
