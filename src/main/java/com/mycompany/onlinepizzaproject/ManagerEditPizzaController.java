package com.mycompany.onlinepizzaproject;

import java.util.Map;

import com.mycompany.onlinepizzaproject.backend.API;
import com.mycompany.onlinepizzaproject.backend.Measurement;
import com.mycompany.onlinepizzaproject.backend.Pizza;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManagerEditPizzaController {
	@FXML private MainController mainController;
	@FXML private ListView<String> pizza_edit_list_view;
	@FXML private TextField pizza_edit_price_field;
	
	private Stage stage;
	private Pizza pizza;

    @FXML private void initialize(){
        mainController = MainController.getMainControllerInstance();
        pizza = mainController.getPizzaToChange();
        ObservableList<String> pizzaLines = FXCollections.observableArrayList();
        pizzaLines.add("Pizza name: " + pizza.getName());
        pizzaLines.add("DescriptionSv: " + pizza.getDescriptionSv());
        pizzaLines.add("DescriptionEn: " + pizza.getDescriptionEn());
        pizzaLines.add("Price: " + pizza.getPrice());
        
        pizzaLines.add("Ingredients:");
        
        for (Map.Entry<String, Measurement> entry : pizza.getIngredients().entrySet()) {
			pizzaLines.add(entry.getKey() + ": " + entry.getValue());
		}
        
        pizza_edit_list_view.setItems(pizzaLines);
    }

    @FXML
    private void editPizza(ActionEvent event){
    	if(!pizza_edit_price_field.getText().isEmpty()){
        	String price = pizza_edit_price_field.getText();
        	if(!price.trim().isEmpty()) {
        		int price30cm = Integer.valueOf(pizza_edit_price_field.getText());
        		
        		//Change price for pizza in database. changePizza(pizza, price30cm)
        		API.updatePizzaPrice(pizza, price30cm);
        	}
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancel(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
	
}
