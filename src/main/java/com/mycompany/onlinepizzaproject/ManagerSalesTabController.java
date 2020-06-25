package com.mycompany.onlinepizzaproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.bson.Document;

public class ManagerSalesTabController {
    @FXML private MainController mainController;
    @FXML private ListView<String> manager_top_sales_list;
    @FXML private DatePicker sales_from_date_picker;
    @FXML private DatePicker sales_to_date_picker;
   
    @FXML
    private void initialize(){
        mainController = MainController.getMainControllerInstance();
    }

    @FXML
    private void showTopProducts(ActionEvent event)throws Exception{
        LocalDate dateFrom = sales_from_date_picker.getValue();
        LocalDate dateTo = sales_to_date_picker.getValue();
        if(dateFrom != null && dateTo != null){
            mainController.getTopSalesFromDatabase(Date.from(dateFrom.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    Date.from(dateTo.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            showTopSales();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Choose from and to date.", ButtonType.OK);
            alert.showAndWait();
        }
    }
    
    /**
     * Creates lines based on information in Documents and shows it in a listview.
     */
    private void showTopSales(){
    	manager_top_sales_list.getItems().clear();
        mainController = MainController.getMainControllerInstance();
        List<Document> topSaletList = mainController.getTopSales();
        mainController.setTopSalesLineList(manager_top_sales_list.getItems());
        for (Document d: topSaletList) {
            String line = "[Product id: " + d.get("product_id") + "], [Product name: " + d.get("productName") + "], [Quantity sold: " + d.get("quantity") + "]";
            mainController.getTopSalesLineList().add(line);
        }
        manager_top_sales_list.setItems(mainController.getTopSalesLineList());
    }
}

