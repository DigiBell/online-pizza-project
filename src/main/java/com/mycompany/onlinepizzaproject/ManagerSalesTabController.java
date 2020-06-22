package com.mycompany.onlinepizzaproject;

import com.mycompany.onlinepizzaproject.Model.Sale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ManagerSalesTabController {
    @FXML private MainController mainController;
    @FXML private TableView<Sale> salesTable;
    @FXML private TableColumn<Sale, Integer> sales_product_id_column;
    @FXML private TableColumn<Sale, String> sales_product_name_column;
    @FXML private TableColumn<Sale, Integer> sales_order_id_column;
    @FXML private TableColumn<Sale, String> sales_date_column;
    @FXML private TableColumn<Sale, Integer> sales_quantity_column;
    @FXML private TableColumn<Sale, Integer> sales_price_column;
    @FXML private DatePicker sales_from_date_picker;
    @FXML private DatePicker sales_to_date_picker;

    private Alert alert;
   

    @FXML
    private void initialize(){
        mainController = MainController.getMainControllerInstance();
        sales_product_id_column.setCellValueFactory(new PropertyValueFactory<>("ProductId"));
        sales_product_name_column.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        sales_order_id_column.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        sales_date_column.setCellValueFactory(new PropertyValueFactory<>("Date"));
        sales_quantity_column.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        sales_price_column.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

    @FXML
    private void showAll(ActionEvent event){//show all
        LocalDate dateFrom = sales_from_date_picker.getValue();
        LocalDate dateTo = sales_to_date_picker.getValue();
        salesTable.getItems().clear();
        if(dateFrom != null && dateTo != null){
            mainController.getSalesFromDatabase(Date.from(dateFrom.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    Date.from(dateTo.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }else{
            mainController.getSalesFromDatabase();
        }
        salesTable.getItems().addAll(mainController.getSaleList());
    }

    @FXML
    private void showTopProducts(ActionEvent event)throws Exception{
        LocalDate dateFrom = sales_from_date_picker.getValue();
        LocalDate dateTo = sales_to_date_picker.getValue();
        salesTable.getItems().clear();
        if(dateFrom != null && dateTo != null){
            mainController.getTopSalesFromDatabase(Date.from(dateFrom.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    Date.from(dateTo.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            //Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            changeToTopProductSales();
        }else{
            alert = new Alert(Alert.AlertType.WARNING, "Choose from and to date.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void changeToTopProductSales() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ManagerTopProductSales.fxml"));
        Stage smallStage = new Stage();
        smallStage.setTitle("Top Sales");
        smallStage.setScene(new Scene(root, 600, 600));
        root.requestFocus();
        smallStage.showAndWait();
    }
}
