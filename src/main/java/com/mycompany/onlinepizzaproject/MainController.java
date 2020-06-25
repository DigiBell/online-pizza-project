package com.mycompany.onlinepizzaproject;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.mycompany.onlinepizzaproject.backend.*;
import javafx.collections.ObservableList;

// SINGLETON
public class MainController {
    public final static int MAIN_STAGE_HEIGHT = 700;
    public final static int MAIN_STAGE_WIDTH = 900;
    public final static String CUSTOMER_ACCESS_LEVEL = "customer";
    public final static String EMPLOYEE_ACCESS_LEVEL = "employee";
    public final static String MANAGER_ACCESS_LEVEL = "manager";
    
    private User loginUser;

    //CUSTOMER VARIABLES
    
    //MANAGER VARIABLES
    
    private Ingredient ingredientToChange;
    private Pizza pizzaToChange;
    private Product productToChange;
    private Order orderToChange;
    private List<Document> topSales = new ArrayList<>();
    private ObservableList<String> topSalesLineList;


    private final static MainController MAIN_CONTROLLER_INSTANCE = new MainController();


    public static MainController getMainControllerInstance() {
        return MAIN_CONTROLLER_INSTANCE;
    }

    ////////////////////////////////////////////----GENERATE METHODS----///////////////////////////////////

   
    //////////////////////////////////////////---DATABASE METHODS---///////////////////////////////////

  
    //////////////////////////////////////////-----HELP METHODS-----///////////////////////////////////

    public void clearDataManager(){
    	setLoginUser(null);
    }

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}

	public Ingredient getIngredientToChange() {
		return ingredientToChange;
	}

	public void setIngredientToChange(Ingredient ingredientToChange) {
		this.ingredientToChange = ingredientToChange;
	}

	public Pizza getPizzaToChange() {
		return pizzaToChange;
	}

	public void setPizzaToChange(Pizza pizzaToChange) {
		this.pizzaToChange = pizzaToChange;
	}

	public Product getProductToChange() {
		return productToChange;
	}

	public void setProductToChange(Product productToChange) {
		this.productToChange = productToChange;
	}

	public Order getOrderToChange() {
		return orderToChange;
	}

	public void setOrderToChange(Order orderToChange) {
		this.orderToChange = orderToChange;
	}

	public List<Document> getTopSales() {
		return topSales;
	}

	public void setTopSales(List<Document> topSales) {
		this.topSales = topSales;
	}

	public ObservableList<String> getTopSalesLineList() {
		return topSalesLineList;
	}

	public void setTopSalesLineList(ObservableList<String> topSalesLineList) {
		this.topSalesLineList = topSalesLineList;
	}

    //////////////////////////////////////////----GETTERS & SETTERS----//////////////////////////////////
    
    
    
}
