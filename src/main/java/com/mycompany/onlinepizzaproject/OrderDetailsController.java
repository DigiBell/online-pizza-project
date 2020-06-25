package com.mycompany.onlinepizzaproject;

import com.mycompany.onlinepizzaproject.backend.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class OrderDetailsController {
	@FXML private MainController mainController;
    @FXML private ListView<String> order_details_list_view;
    @FXML private ChoiceBox<String> order_status_choicebox = new ChoiceBox<String>();
    private Stage stage;

    @FXML private void initialize(){
        mainController = MainController.getMainControllerInstance();
        order_status_choicebox.setItems(FXCollections.observableArrayList("Select status", "In progress", "Done"));
        order_status_choicebox.getSelectionModel().selectFirst();
        Order order = mainController.getOrderToChange();
        ObservableList<String> orderLines = FXCollections.observableArrayList();
        orderLines.add("Order id: "  );
        orderLines.add("Customer id: " + order.getCustomer());
        orderLines.add("Pizzas: " + order.getPizzas());
        orderLines.add("Products: " + order.getProducts());
        orderLines.add("Comment: " + order.getComment());
        orderLines.add("Total price: " + order.getTotalPrice());
        orderLines.add("Status: " + order.getStatus());
        order_details_list_view.setItems(orderLines);
    }

    @FXML
    private void confirmChange(ActionEvent event){
    	String status = (String) order_status_choicebox.getSelectionModel().getSelectedItem();
    	System.out.println("Category: " + status);
    	
    	switch(status) {
		case "Select status":
			break;
    	case "In progress":
    		//change order status 
    		break;
    	case "Done":
    		//change order status 
        	//delete from orders collection 
        	//add to orders history collection
    		break;
    	}
    	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelChange(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

}
