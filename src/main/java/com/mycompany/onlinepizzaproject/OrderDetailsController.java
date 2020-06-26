package com.mycompany.onlinepizzaproject;

import com.mycompany.onlinepizzaproject.backend.API;
import com.mycompany.onlinepizzaproject.backend.Order;
import com.mycompany.onlinepizzaproject.backend.Order.Status;

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
    private  Order order;

    @FXML private void initialize(){
        mainController = MainController.getMainControllerInstance();
        order_status_choicebox.setItems(FXCollections.observableArrayList("Select status", "In progress", "Done", "Cancelled"));
        order_status_choicebox.getSelectionModel().selectFirst();
        order= mainController.getOrderToChange();
        ObservableList<String> orderLines = FXCollections.observableArrayList();
        orderLines.add("Order id: " + order.getId());
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
    		this.order.setStatus(Status.inProgress);
    		API.modifyOrderStatus(this.order);
    		break;
    	case "Done":
    		this.order.setStatus(Status.done);
    		API.modifyOrderStatus(this.order);
        	//delete from orders collection 
        	//add to orders history collection
    		break;
    	case "Cancelled":
    		this.order.setStatus(Status.cancelled);
    		API.modifyOrderStatus(this.order);
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
