package com.mycompany.onlinepizzaproject;

import com.mycompany.onlinepizzaproject.Model.Ingredient;
import com.mycompany.onlinepizzaproject.backend.Pizza;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
        pizza = mainController.getPizzaToEdit();
        ObservableList<String> pizzaLines = FXCollections.observableArrayList();
        pizzaLines.add("Ingredient name: " + pizza.getName());
        pizzaLines.add("DescriptionSv: " + pizza.getDescriptionSv());
        pizzaLines.add("DescriptionEn: " + pizza.getDescriptionEn());
        pizzaLines.add("Price: " + pizza.getPrice());
        pizza_edit_list_view.setItems(pizzaLines);
    }

    @FXML
    private void editPizza(ActionEvent event){
    	if(!pizza_edit_price_field.getText().isEmpty()){
        	String price = pizza_edit_price_field.getText();
        	if(!price.trim().isEmpty()) {
        		int price30cm = Integer.valueOf(pizza_edit_price_field.getText());
        		
        		//Change price for pizza in database. Use pizza.
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
