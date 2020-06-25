package com.mycompany.onlinepizzaproject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import com.mycompany.onlinepizzaproject.Model.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class EmployeeOrderTabController {
	@FXML private MainController mainController;
    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, String> orders_order_id_column;
    @FXML private TableColumn<Order, String> orders_user_id_column;
    @FXML private TableColumn<Order, String> orders_date_column;
    @FXML private TableColumn<Order, String[]> orders_products_column;
    @FXML private TableColumn<Order, String> orders_total_price_column;
    @FXML private DatePicker orders_from_date_picker;
    @FXML private DatePicker orders_to_date_picker;


    @FXML
    private void initialize(){
        mainController = MainController.getMainControllerInstance();
        orders_order_id_column.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        orders_user_id_column.setCellValueFactory(new PropertyValueFactory<>("UserId"));
        orders_date_column.setCellValueFactory(new PropertyValueFactory<>("Date"));
        orders_products_column.setCellValueFactory(new PropertyValueFactory<>("OrderLines"));
        orders_total_price_column.setCellValueFactory(new PropertyValueFactory<>("TotalPrice"));
    }

    /**
     * Shows all Orders that has status "placed".
     * @param event
     */
    @FXML
    private void showAll(ActionEvent event){ // NEED A CHANGE, GET ORDERS WITH STATUS PLACED FROM DATABASE, OR SORT ON CLIENT SIDE.
        LocalDate dateFrom = orders_from_date_picker.getValue();
        LocalDate dateTo = orders_to_date_picker.getValue();
        ordersTable.getItems().clear();
        if(dateFrom != null && dateTo != null){
            mainController.getOrdersFromDatabase(Date.from(dateFrom.atStartOfDay(ZoneId.systemDefault()).toInstant()),
            Date.from(dateTo.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }else{
            mainController.getOrdersFromDatabase();
        }
        ordersTable.getItems().addAll(mainController.getOrderList());
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
            //mainController.setIngredientToChange(order);
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
