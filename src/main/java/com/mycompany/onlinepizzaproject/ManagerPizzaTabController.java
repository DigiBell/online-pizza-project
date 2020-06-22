package com.mycompany.onlinepizzaproject;


import com.mycompany.onlinepizzaproject.backend.API;
import com.mycompany.onlinepizzaproject.backend.Pizza;

//import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManagerPizzaTabController {
    // @FXML private MainController mainController;

    @FXML private TableView<Pizza> pizzaTable;

    @FXML private TableColumn<Pizza, String> pizza_name_column;    
    @FXML private TableColumn<Pizza, String> pizza_descriptionSv_column;
    @FXML private TableColumn<Pizza, String> pizza_descriptionEn_column;
    @FXML private TableColumn<Pizza, String> pizza_ingredients_column;
    @FXML private TableColumn<Pizza, Integer> pizza_price_column;

    @FXML
    private void initialize(){
        // mainController = MainController.getMainControllerInstance();

        pizza_name_column.setCellValueFactory(new PropertyValueFactory<>("Name"));
        pizza_descriptionSv_column.setCellValueFactory(new PropertyValueFactory<>("DescriptionSv"));
        pizza_descriptionEn_column.setCellValueFactory(new PropertyValueFactory<>("DescriptionEn"));
        pizza_ingredients_column.setCellValueFactory(new PropertyValueFactory<>("Ingredients"));
        pizza_price_column.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

    @FXML
    private void showAll(ActionEvent event){
    	pizzaTable.getItems().clear();
        // mainController.getProductsFromDatabase();
        //productsTable.getItems().addAll(mainController.getProductList());
    	pizzaTable.getItems().addAll(API.getPizzas());
    }

    @FXML
    private void showDetails(ActionEvent event){
        //show choosen item as text in a popup window
        //or add a listView on the side
    }
}
