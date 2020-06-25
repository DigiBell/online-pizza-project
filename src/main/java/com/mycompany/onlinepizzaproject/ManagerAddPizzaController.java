package com.mycompany.onlinepizzaproject;

import com.mycompany.onlinepizzaproject.backend.API;
import com.mycompany.onlinepizzaproject.backend.Pizza;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManagerAddPizzaController {
	@FXML private MainController mainController;
	@FXML private TextField pizza_name_textfield;
	@FXML private TextField pizza_description_sv_textfield;
	@FXML private TextField pizza_description_en_textfield;
	@FXML private TextField pizza_price_textfield;
	private Stage stage;
	
	 @FXML
	 private void initialize(){
	        mainController = MainController.getMainControllerInstance();
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
