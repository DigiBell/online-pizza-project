package com.mycompany.onlinepizzaproject;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ManagerAddProductController {
	@FXML private MainController mainController;
	@FXML private TextField product_name_textfield;
	@FXML private TextField product_description_sv_textfield;
	@FXML private TextField product_description_en_textfield;
	@FXML private TextField product_size_textfield;
	@FXML private TextField product_price_textfield;
	@FXML private final ChoiceBox<String> product_category_choicebox = new ChoiceBox<String>(FXCollections.observableArrayList("Ice-Cream", "Bevareges", "Side dish", "Extras"));
	@FXML private final ChoiceBox<String> product_units_coicebox = new ChoiceBox<String>(FXCollections.observableArrayList("g", "kg", "L", "CL", "cm", "pcs", "None"));
	@FXML private final ChoiceBox<String> product_availability_choicebox = new ChoiceBox<String>(FXCollections.observableArrayList("Available", "Not available"));
	
	 @FXML
	    private void initialize(){
	        mainController = MainController.getMainControllerInstance();
	       
	    }
}
