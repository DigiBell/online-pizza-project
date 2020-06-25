package com.mycompany.onlinepizzaproject;

import com.mycompany.onlinepizzaproject.backend.Product;
import com.mycompany.onlinepizzaproject.backend.Product.Category;
import com.mycompany.onlinepizzaproject.backend.API;

import javafx.collections.FXCollections;
//import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ManagerProductsTabController {
    @FXML private MainController mainController;

    @FXML private TableView<Product> productsTable;
    // @FXML private TableColumn<Product, Integer> products_product_id_column;
    @FXML private TableColumn<Product, String> products_category_column;
    @FXML private TableColumn<Product, String> products_name_column;
    // @FXML private TableColumn<Product, String> products_size_column;
    @FXML private TableColumn<Product, Integer> products_quantity_column;
    @FXML private TableColumn<Product, Integer> products_unit_price_column;
    @FXML private ChoiceBox<String> product_category_choicebox = new ChoiceBox<String>();
    @FXML private TextField product_name_textfield;
    

    @FXML
    private void initialize(){
        mainController = MainController.getMainControllerInstance();
        product_category_choicebox.setItems(FXCollections.observableArrayList("Select category", "Beverage", "Ice-cream", "Sause", "Side dish"));
        product_category_choicebox.getSelectionModel().selectFirst();
        // products_product_id_column.setCellValueFactory(new PropertyValueFactory<>("ProductId"));
        products_category_column.setCellValueFactory(new PropertyValueFactory<>("Category"));
        products_name_column.setCellValueFactory(new PropertyValueFactory<>("Name"));
        // products_size_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getSize()+ p.getValue().getUnits()));
        products_quantity_column.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        products_unit_price_column.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

    @FXML
    private void addProduct(ActionEvent event)throws Exception{
    	showAddProductView();
    }
    
    @FXML
    private void deleteProduct(ActionEvent event){
    	Product productSelected = productsTable.getSelectionModel().getSelectedItem();
    	//Delete product from database
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete product: " + productSelected.getName(), ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
        	API.deleteProduct(productSelected);
        }
    }
    
    @FXML
    private void editProduct(ActionEvent event)throws Exception{
    	Product productSelected = productsTable.getSelectionModel().getSelectedItem();
    	if(productSelected != null) {
    		mainController.setProductToChange(productSelected);
    		showEditProductView();
    	}
    }
    
    @FXML
    private void showAll(ActionEvent event){
        productsTable.getItems().clear();
        // mainController.getProductsFromDatabase();
        //productsTable.getItems().addAll(mainController.getProductList());
        productsTable.getItems().addAll(API.getProducts());
    }
    
    /**
     * Search for product by name or category, or both.
     * @param event
     */
    @FXML
	private void search(ActionEvent event) {
		String category = (String) product_category_choicebox.getSelectionModel().getSelectedItem();
		String name = product_name_textfield.getText();	
		switch (category) {
		case "Select category":
			if (!name.trim().isEmpty()) {
				// Find product by name
				productsTable.getItems().clear();
				productsTable.getItems().addAll(API.searchProducts(name));
			}
			break;
		case "Beverage":
			if (!name.trim().isEmpty()) {
				// Find product by name and category
				productsTable.getItems().clear();
				productsTable.getItems().addAll(API.searchProducts(name, Category.beverage));
			} else {
				// Find products by category
				productsTable.getItems().clear();
				productsTable.getItems().addAll(API.getProducts(Category.beverage));
			}
			break;
		case "Ice-cream":
			if (!name.trim().isEmpty()) {
				// Find product by name and category
				productsTable.getItems().clear();
				productsTable.getItems().addAll(API.searchProducts(name, Category.iceCream));
			} else {
				// Find products by category
				productsTable.getItems().clear();
				productsTable.getItems().addAll(API.getProducts(Category.iceCream));
			}
			break;
		case "Sause":
			if (!name.trim().isEmpty()) {
				// Find product by name and category
				productsTable.getItems().clear();
				productsTable.getItems().addAll(API.searchProducts(name, Category.sause));
			} else {
				// Find products by category
				productsTable.getItems().clear();
				productsTable.getItems().addAll(API.getProducts(Category.sause));
			}
			break;
		case "Side dish":
			if (!name.trim().isEmpty()) {
				// Find product by name and category
				productsTable.getItems().clear();
				productsTable.getItems().addAll(API.searchProducts(name, Category.sideDish));
			} else {
				// Find products by category
				productsTable.getItems().clear();
				productsTable.getItems().addAll(API.getProducts(Category.sideDish));
			}
			break;
		}
	}
    
    
    private void showAddProductView() throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("ManagerAddProductView.fxml"));
        Stage smallStage = new Stage();
        smallStage.setTitle("Add pizza");
        smallStage.setScene(new Scene(root, 600, 600));
        root.requestFocus();
        smallStage.showAndWait();
    }
    
    private void showEditProductView() throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("ManagerEditProductView.fxml"));
        Stage smallStage = new Stage();
        smallStage.setTitle("Edit pizza");
        smallStage.setScene(new Scene(root, 600, 600));
        root.requestFocus();
        smallStage.showAndWait();
    }
}

