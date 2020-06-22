package com.mycompany.onlinepizzaproject;

import com.mycompany.onlinepizzaproject.backend.Product;
import com.mycompany.onlinepizzaproject.backend.API;

//import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManagerProductsTabController {
    @FXML private MainController mainController;

    @FXML private TableView<Product> productsTable;
    // @FXML private TableColumn<Product, Integer> products_product_id_column;
    @FXML private TableColumn<Product, String> products_category_column;
    @FXML private TableColumn<Product, String> products_name_column;
    // @FXML private TableColumn<Product, String> products_size_column;
    @FXML private TableColumn<Product, Integer> products_quantity_column;
    @FXML private TableColumn<Product, Integer> products_unit_price_column;

    @FXML
    private void initialize(){
        mainController = MainController.getMainControllerInstance();
        // products_product_id_column.setCellValueFactory(new PropertyValueFactory<>("ProductId"));
        products_category_column.setCellValueFactory(new PropertyValueFactory<>("Category"));
        products_name_column.setCellValueFactory(new PropertyValueFactory<>("Name"));
        // products_size_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getSize()+ p.getValue().getUnits()));
        products_quantity_column.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        products_unit_price_column.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

    @FXML
    private void showAll(ActionEvent event){
        productsTable.getItems().clear();
        // mainController.getProductsFromDatabase();
        //productsTable.getItems().addAll(mainController.getProductList());
        productsTable.getItems().addAll(API.getProducts());
    }

    @FXML
    private void showDetails(ActionEvent event){
        //show choosen item as text in a popup window
        //or add a listView on the side
    }
}
