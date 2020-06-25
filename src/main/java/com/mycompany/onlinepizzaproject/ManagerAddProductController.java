package com.mycompany.onlinepizzaproject;

import com.mycompany.onlinepizzaproject.backend.Pizza;
import com.mycompany.onlinepizzaproject.backend.Product;
import com.mycompany.onlinepizzaproject.backend.Product.Category;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class ManagerAddProductController {
	@FXML private MainController mainController;
	@FXML private TextField product_name_textfield;
	@FXML private TextField product_price_textfield;
	@FXML private TextField product_quantity_textfield;
	@FXML private ChoiceBox<String> product_category_choicebox = new ChoiceBox<String>();
	private Stage stage;
	
	 
	@FXML
	 private void initialize(){
		 mainController = MainController.getMainControllerInstance(); 
		 product_category_choicebox.setItems(FXCollections.observableArrayList("Select category", "Beverage", "Ice-cream", "Sause", "Side dish"));	
		 product_category_choicebox.getSelectionModel().selectFirst();
	 }
	 
	 @FXML
	 private void addProduct(ActionEvent event) {
		String name = product_name_textfield.getText();
		String category = (String) product_category_choicebox.getSelectionModel().getSelectedItem();
		String price = product_price_textfield.getText();
		String quantity = product_quantity_textfield.getText();
		Product product;
		
		if(name.trim().isEmpty() || category.trim().isEmpty() || price.trim().isEmpty() || quantity.trim().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "All fields must be filled.", ButtonType.OK);
            alert.showAndWait();
		}else {
			int intPrice = Integer.valueOf(price);
			int intQuantity = Integer.valueOf(quantity);
			switch(category) {
	     	case "Beverage":
	     		product = new Product(name, Category.beverage, intPrice, intQuantity);
	     	case "Ice-cream":
	     		product = new Product(name, Category.iceCream, intPrice, intQuantity);
	     	case "Sause":
	     		product = new Product(name, Category.sause, intPrice, intQuantity);
	     	case "Side dish":
	     		product = new Product(name, Category.sideDish, intPrice, intQuantity);
	     	}
		}
			
		 //Add new product to database 
		
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	     stage.close();
	 }
	 
	 
	 @FXML
	 private void cancel(ActionEvent event){
	     stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	     stage.close();
	 }
}

