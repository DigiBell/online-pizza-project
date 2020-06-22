package com.mycompany.onlinepizzaproject.Model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Ingredient {
    private ObjectId id;
    @BsonProperty(value="ingredient_id")
    private Integer ingredientId;
    private String name;
    private String description;
    private Integer quantity;
    private String units;

    public Ingredient() { }

    public Ingredient(Integer ingredientId, String name, String description, Integer quantity, String units) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.units = units;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", ingredientId=" + ingredientId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", units='" + units + '\'' +
                '}';
    }
}
