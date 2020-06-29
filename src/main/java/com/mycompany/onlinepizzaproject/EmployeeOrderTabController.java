package com.mycompany.onlinepizzaproject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mycompany.onlinepizzaproject.backend.Order;
import com.mycompany.onlinepizzaproject.backend.Order.Status;
import com.mycompany.onlinepizzaproject.backend.API;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class EmployeeOrderTabController {
	@FXML private MainController mainController;
    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, String> orders_order_id_column;
    @FXML private TableColumn<Order, String> orders_date_column;
    @FXML private TableColumn<Order, String> orders_user_email_column;
    @FXML private TableColumn<Order, String> orders_total_price_column;
    @FXML private TableColumn<Order, String> orders_status_column;
    @FXML private DatePicker orders_from_date_picker;
    @FXML private DatePicker orders_to_date_picker;
    @FXML private ChoiceBox<Object> orders_category_choicebox = new ChoiceBox<Object>();


    @FXML
    private void initialize(){
        mainController = MainController.getMainControllerInstance();
        
        orders_category_choicebox.setItems(FXCollections.observableArrayList("All", new Separator(), "Placed", "In progress"));	
        orders_category_choicebox.getSelectionModel().selectFirst();

        orders_order_id_column.setCellValueFactory(new PropertyValueFactory<>("Id"));
        orders_date_column.setCellValueFactory(new PropertyValueFactory<>("Date"));
        orders_user_email_column.setCellValueFactory(new PropertyValueFactory<>("Customer"));
        orders_total_price_column.setCellValueFactory(new PropertyValueFactory<>("TotalPrice"));
        orders_status_column.setCellValueFactory(new PropertyValueFactory<>("Status"));
        
    }

    /**
     * Shows all Orders that has status "placed".
     * @param event
     */
    @FXML
    private void showAll(ActionEvent event) {
        LocalDate localDateFrom = orders_from_date_picker.getValue();
        LocalDate localDateTo = orders_to_date_picker.getValue();
        
        ordersTable.getItems().clear();
        
        String category = (String) orders_category_choicebox.getSelectionModel().getSelectedItem();
        
        Date from = null;
        Date to = null;
        
 		if(localDateFrom != null) {
 			
 			from = Date.from(orders_from_date_picker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
 			
 			if(localDateTo != null) {
 				to = Date.from(orders_to_date_picker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
 			} else {
 				to = new Date();
 				orders_to_date_picker.setValue(to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
 			}         			
 		}
        
		switch (category) {
		case "All":
			if (from != null && to != null) {
				ordersTable.getItems().addAll(API.getOpenOrders(from, to));
			} else {
				ordersTable.getItems().addAll(API.getOpenOrders());
			}		
			break;
		case "Placed":
			if (from != null && to != null) {
				ordersTable.getItems().addAll(API.getOrders(Status.placed, from, to));
			} else {
				ordersTable.getItems().addAll(API.getOrders(Status.placed));
			}		
			break;
		case "In progress":
			if (from != null && to != null) {
				ordersTable.getItems().addAll(API.getOrders(Status.inProgress, from, to));
			} else {
				ordersTable.getItems().addAll(API.getOrders(Status.inProgress));
			}		
			break;
		}       
        
    }
    
    @FXML
    private void clearDates(ActionEvent event) {
    	orders_from_date_picker.setValue(null);
    	orders_to_date_picker.setValue(null);
    }
    
    /**
     * Is called when button Order details is clicked.
     * Detects selected order and based on result shows warning or opens new window with order details.
     * @param event
     * @throws Exception
     */
    @FXML
    private void showOrderDetails(ActionEvent event)throws Exception{
    	Order order = ordersTable.getSelectionModel().getSelectedItem();
        if(order == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Choose an order from table.", ButtonType.OK);
            alert.showAndWait();
        }else{
        	mainController.setOrderToChange(order);
        	showOrderDetailsView();
        }
    }
    
    /**
     * Opens a window that shows Order details.
     * Where employee or manager can change Order status.
     * @throws Exception
     */
    private void showOrderDetailsView()throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("OrderDetailsView.fxml"));
        Stage smallStage = new Stage();
        smallStage.setTitle("Order details");
        smallStage.setScene(new Scene(root, 600, 600));
        root.requestFocus();
        smallStage.showAndWait();
    }
}
