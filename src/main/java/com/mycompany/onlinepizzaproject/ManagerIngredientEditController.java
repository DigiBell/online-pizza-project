package com.mycompany.onlinepizzaproject;

import com.mycompany.onlinepizzaproject.backend.API;
import com.mycompany.onlinepizzaproject.backend.Ingredient;
import com.mycompany.onlinepizzaproject.backend.Measurement;
import com.mycompany.onlinepizzaproject.backend.Measurement.Unit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManagerIngredientEditController {
    @FXML private MainController mainController;
    @FXML private ListView<String> ingredient_edit_list_view;
    @FXML private TextField ingredient_edit_quantity_field;
    @FXML private ChoiceBox<String> measurement_unit_choicebox;


    private Stage stage;
    private Ingredient ingredient;

    @FXML private void initialize(){
        mainController = MainController.getMainControllerInstance();
        ingredient = mainController.getIngredientToChange();
        ObservableList<String> ingredientLines = FXCollections.observableArrayList();
        ingredientLines.add("Ingredient name: " + ingredient.getName());
        ingredientLines.add("Quantity: " + ingredient.getStock().toString());
        ingredientLines.add("Price/kg: " + ingredient.getPricePerKg());
        ingredient_edit_list_view.setItems(ingredientLines);
        
        measurement_unit_choicebox.setItems(FXCollections.observableArrayList("Kg", "G"));
        measurement_unit_choicebox.getSelectionModel().selectFirst();
    }

    @FXML
    private void confirmChange(ActionEvent event){
    	String quantity = ingredient_edit_quantity_field.getText();

        if(!quantity.trim().isEmpty()){
           
        	// change ingredient quantity editIngredient(ingredient, newQuantity)
        	
        	ingredient.setStock(new Measurement(Double.valueOf(quantity), Unit.valueOf( (String) measurement_unit_choicebox.getSelectionModel().getSelectedItem().toLowerCase() )));
        	API.updateIngredient(ingredient);
        	
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelChange(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
