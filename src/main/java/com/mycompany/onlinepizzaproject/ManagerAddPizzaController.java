package com.mycompany.onlinepizzaproject;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.onlinepizzaproject.backend.API;
import com.mycompany.onlinepizzaproject.backend.Ingredient;
import com.mycompany.onlinepizzaproject.backend.Pizza;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jdk.nashorn.api.tree.ForInLoopTree;

public class ManagerAddPizzaController {
	@FXML private MainController mainController;
	@FXML private TextField pizza_name_textfield;
	@FXML private TextField pizza_description_sv_textfield;
	@FXML private TextField pizza_description_en_textfield;
	@FXML private TextField pizza_price_textfield;
	@FXML private ChoiceBox<String> add_pizza_ingredients_choicebox = new ChoiceBox<String>();
	@FXML private ChoiceBox<String> add_pizza_ingredient_amount_units_choicebox = new ChoiceBox<String>();
	@FXML private TextField add_pizza_ingredient_amount_textfield;
	@FXML private TextArea add_pizza_ingredients_textarea;
	
	
	private Stage stage;
	
	 @FXML
	 private void initialize(){
	        mainController = MainController.getMainControllerInstance();
	        add_pizza_ingredient_amount_units_choicebox.setItems(FXCollections.observableArrayList("Units", "g", "kg"));
	        add_pizza_ingredient_amount_units_choicebox.getSelectionModel().selectFirst();
	        
	        add_pizza_ingredients_choicebox.setItems(FXCollections.observableArrayList("Select ingredient"));
	        List<Ingredient> ingredients = API.getIngredients();
	        List<String> ingredientNameList = new ArrayList<>();
	        ingredientNameList.add("Select ingredient");
	        for(Ingredient i: ingredients) {
	        	ingredientNameList.add(i.getName());
	        }
	        add_pizza_ingredients_choicebox.setItems(FXCollections.observableArrayList(ingredientNameList));
	        add_pizza_ingredients_choicebox.getSelectionModel().selectFirst();
	 }
	 
	 @FXML
	 private void addIngredient(ActionEvent event) {	
		 //check if ingredient choosen correctly
		 //check if amount is correct
		 //check if units choosen correctly
		 //show ingredient in textfield
		 //
	 }
	
	/**
	 * Create new pizza and add it to database.
	 * @param event
	 */
	@FXML
	 private void addPizza(ActionEvent event) {	
		String name = pizza_name_textfield.getText();
		String descriptionSv = pizza_description_sv_textfield.getText();
		String descriptionEn = pizza_description_en_textfield.getText();
		int price30cm = Integer.valueOf(pizza_price_textfield.getText());
		Pizza pizza = new Pizza(name, 0, price30cm, 0, descriptionSv, descriptionEn);	
		
		//Add new pizza to database addPizza(pizza);
		try {
			API.addPizza(pizza);
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
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
