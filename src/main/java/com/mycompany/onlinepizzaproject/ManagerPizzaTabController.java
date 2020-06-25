package com.mycompany.onlinepizzaproject;

import com.mycompany.onlinepizzaproject.backend.API;
import com.mycompany.onlinepizzaproject.backend.Pizza;

//import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ManagerPizzaTabController {
    @FXML private MainController mainController;
    @FXML private TableView<Pizza> pizzaTable;
    @FXML private TableColumn<Pizza, String> pizza_name_column;    
    @FXML private TableColumn<Pizza, String> pizza_descriptionSv_column;
    @FXML private TableColumn<Pizza, String> pizza_descriptionEn_column;
    @FXML private TableColumn<Pizza, String> pizza_ingredients_column;
    @FXML private TableColumn<Pizza, Integer> pizza_price_column;
    @FXML private TextField pizza_name_search_textfield;

    @FXML
    private void initialize(){
        mainController = MainController.getMainControllerInstance();
        pizza_name_column.setCellValueFactory(new PropertyValueFactory<>("Name"));
        pizza_descriptionSv_column.setCellValueFactory(new PropertyValueFactory<>("DescriptionSv"));
        pizza_descriptionEn_column.setCellValueFactory(new PropertyValueFactory<>("DescriptionEn"));
        pizza_ingredients_column.setCellValueFactory(new PropertyValueFactory<>("Ingredients"));
        pizza_price_column.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }
    
    @FXML
    private void addPizza(ActionEvent event)throws Exception{
    	showAddPizzaView();
    }
    
    @FXML
    private void deletePizza(ActionEvent event){
    	Pizza pizzaSelected = pizzaTable.getSelectionModel().getSelectedItem();
    	if(pizzaSelected != null) {
    		//Delete pizza from database		
    		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete pizza: " + pizzaSelected.getName(), ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
            	API.deletePizza(pizzaSelected);
            }
    		
    	}
    }
    
    @FXML
    private void editPizza(ActionEvent event)throws Exception{
    	Pizza pizzaSelected = pizzaTable.getSelectionModel().getSelectedItem();
    	if(pizzaSelected != null) {
    		mainController.setPizzaToChange(pizzaSelected);
    		showEditPizzaView();
    	}
    }
    
    @FXML
    private void showAll(ActionEvent event){
    	pizzaTable.getItems().clear();
        // mainController.getProductsFromDatabase();
        //productsTable.getItems().addAll(mainController.getProductList());
    	pizzaTable.getItems().addAll(API.getPizzas());
    }
    
    @FXML
    private void search(ActionEvent event){
    	String name = pizza_name_search_textfield.getText();
    	if(!name.trim().isEmpty()) {
    		//Find pizza by name in database
    		try {
//    			Pizza pizza = API.getPizza(name);
//    			pizzaTable.getItems().clear();
//    			pizzaTable.getItems().add(pizza);
    			
    			pizzaTable.getItems().clear();
    			pizzaTable.getItems().addAll(API.searchPizzas(name));
    			
    		}catch (Exception e) {
    			Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
    		}
    	}
    }
    
    
    private void showAddPizzaView() throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("ManagerAddPizzaView.fxml"));
        Stage smallStage = new Stage();
        smallStage.setTitle("Add pizza");
        smallStage.setScene(new Scene(root, 600, 400));//width, height
        root.requestFocus();
        smallStage.showAndWait();
    }
    
    private void showEditPizzaView() throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("ManagerEditPizzaView.fxml"));
        Stage smallStage = new Stage();
        smallStage.setTitle("Edit pizza");
        smallStage.setScene(new Scene(root, 600, 400));
        root.requestFocus();
        smallStage.showAndWait();
    }
}

