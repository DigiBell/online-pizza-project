package com.mycompany.onlinepizzaproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.bson.Document;

import java.util.List;

public class ManagerTopProductSalesController {
    @FXML
    private MainController mainController;
    @FXML private ListView manager_top_sales_listview;

    @FXML private void initialize(){
        mainController = MainController.getMainControllerInstance();
        mainController.setTopSalesLineList(manager_top_sales_listview.getItems());
        List<Document> topSaletList = mainController.getTopSales();

        for (Document d: topSaletList) {
            String line = "Product id: " + d.get("productId") + ", Product name: " + d.get("productName") + ", Quantity sold: " + d.get("quantity");
            mainController.getTopSalesLineList().add(line);
        }

        manager_top_sales_listview.setItems(mainController.getTopSalesLineList());
    }

    @FXML
    private void close(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
