package com.mycompany.onlinepizzaproject;

import com.mycompany.onlinepizzaproject.backend.Order;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class OrderDetailsController {
	@FXML private MainController mainController;
    @FXML private ListView<String> order_details_list_view;
    @FXML private ChoiceBox<String> order_status_choicebox = new ChoiceBox<String>();


    private Stage stage;

    @FXML private void initialize(){
        mainController = MainController.getMainControllerInstance();
        order_status_choicebox.setItems(FXCollections.observableArrayList("In progress", "Done"));
        order_status_choicebox.setTooltip(new Tooltip("Select status"));
        //Order order = mainController.getOrderLineList();
        ObservableList<String> orderLines = FXCollections.observableArrayList();
        orderLines.add("Order id: "  );
        orderLines.add("Order name: ");
        orderLines.add("Products: " );
        orderLines.add("Total price: " );
        orderLines.add("Status: " );
        order_details_list_view.setItems(orderLines);
    }

    @FXML
    private void confirmChange(ActionEvent event){
    	String category = (String) order_status_choicebox.getSelectionModel().getSelectedItem();
    	System.out.println("Category: " + category);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelChange(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

}
