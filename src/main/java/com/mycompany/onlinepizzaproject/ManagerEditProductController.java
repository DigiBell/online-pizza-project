package com.mycompany.onlinepizzaproject;

import com.mycompany.onlinepizzaproject.backend.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManagerEditProductController {
	@FXML private MainController mainController;
	@FXML private ListView<String> product_edit_list_view;
	@FXML private TextField product_edit_price_textfield;
	@FXML private TextField product_edit_quantity_textfield;
	
	private Stage stage;
	private Product product;

    @FXML private void initialize(){
        mainController = MainController.getMainControllerInstance();
        product = mainController.getProductToEdit();
        ObservableList<String> productLines = FXCollections.observableArrayList();
        productLines.add("Product name: " + product.getName());
        productLines.add("Category: " + product.getCategory());
        productLines.add("Price: " + product.getPrice());
        productLines.add("Quantity: " + product.getStock());
        product_edit_list_view.setItems(productLines);
    }

    @FXML
    private void editProduct(ActionEvent event){
    	if(!product_edit_price_textfield.getText().trim().isEmpty() && !product_edit_quantity_textfield.getText().trim().isEmpty()){
        	//change both price and quantity. Use product.
        }else if(!product_edit_price_textfield.getText().trim().isEmpty()) {
        	//change price. Use product.
        }else if(!product_edit_quantity_textfield.getText().trim().isEmpty()) {
        	//change quantity. Use product.
        }else {
        	//both empty. do nothing
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
