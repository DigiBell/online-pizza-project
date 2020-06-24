package com.mycompany.onlinepizzaproject;

import java.util.Date;

import com.mycompany.onlinepizzaproject.Model.Order;
import com.mycompany.onlinepizzaproject.Model.OrderLine;
import com.mycompany.onlinepizzaproject.Model.Product;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CustomerHomeController {
    @FXML private MainController mainController;

    @FXML private Label welcome_lable;
    @FXML private ListView<String> cart_list_view;
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> category_column;
    @FXML private TableColumn<Product, String> name_column;
    @FXML private TableColumn<Product, String> description_column;
    @FXML private TableColumn<Product, String> size_column;
    @FXML private TableColumn<Product, String> price_column;

    private Alert alert;

    /**
     * Initialise layout.
     * Set welcome label to show Account name
     * Initialise columns in tableView
     * Calls method parseProductList() to get data for table.
     */
    @FXML
    private void initialize(){
        mainController = MainController.getMainControllerInstance();
        welcome_lable.setText("Welcome, " + mainController.getLoginAccount().getFirstName() + "!");
        category_column.setCellValueFactory(new PropertyValueFactory<>("Category"));
        name_column.setCellValueFactory(new PropertyValueFactory<>("Name"));
        description_column.setCellValueFactory(new PropertyValueFactory<>("Description"));
        //size_column.setCellValueFactory(new PropertyValueFactory<>("Size"));
        size_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getSize() + " " + p.getValue().getUnits()));
        price_column.setCellValueFactory(new PropertyValueFactory<>("Price"));
        mainController.getProductsFromDatabase();
        productTable.getItems().addAll(mainController.getProductListCustomer());
    }

    /**
     * Method is called when "add to cart" button is clicked.
     * Adds chosen product to cart.
     * @param event that triggered method call.
     */
    @FXML
    private void add(ActionEvent event)throws Exception{
        System.out.println("\nadd product to cart");
        Product productSelected = productTable.getSelectionModel().getSelectedItem();
        mainController.setProductSelected(productSelected);
        if(productSelected.getCategory().equals("Pizza")){
            changeToCustomPizzaView();
        }else{
            addNonPizzaToCart();
        }
        addLineToCart();
    }

    private void changeToCustomPizzaView()throws Exception{
        System.out.println("\ncustomize pizza");
        Parent root = FXMLLoader.load(getClass().getResource("CustomerCustomPizzaView.fxml"));
        Stage smallStage = new Stage();
        smallStage.setScene(new Scene(root, 500, 400));
        root.requestFocus();
        smallStage.showAndWait();
    }

    private void addNonPizzaToCart( ){
        Product productSelected = mainController.getProductSelected();
        OrderLine orderLine = new OrderLine();
        orderLine.setProductId(productSelected.getProductId());
        orderLine.setProductName(productSelected.getName());
        orderLine.setProductCategory(productSelected.getCategory());
        if(productSelected.getSize().trim().isEmpty() && productSelected.getUnits().trim().isEmpty()){
            orderLine.setDetails(null);
        }else{
            orderLine.setDetails(" (" + productSelected.getSize() + productSelected.getUnits() + ") ");
        }
        orderLine.setComment(null);
        orderLine.setTotalPrice(Double.valueOf(productSelected.getPrice()));

        String cartLine = "" + orderLine.getProductName() + orderLine.getDetails() + "price: " + orderLine.getTotalPrice();

        mainController.setOrderLine(orderLine);
        mainController.getOrderLineList().add(orderLine);
        mainController.setCartLine(cartLine);
    }

    /**
     * Adds a line with product details along with the total price to cart listView.
     */
    private void addLineToCart(){
        String cartLine = mainController.getCartLine();
        System.out.println("cartline:" + cartLine);
        double price = mainController.getOrderLine().getTotalPrice();
        double totalPrice = mainController.getTotalPrice();
        totalPrice = totalPrice + price;
        mainController.setTotalPrice(totalPrice);
        String totalPriceLine = "Total price: " + mainController.getTotalPrice() + " kr";
        int size;
        //MAYBE CREATE AN OBSERVABLE LIST TO SKIP CALLING getCartLineList MANY TIMES ?
        mainController.setCartLineList(cart_list_view.getItems());
        if(!(cart_list_view.getItems().isEmpty())){
            size = mainController.getCartLineList().size();
            mainController.getCartLineList().remove(size-1);
            mainController.getCartLineList().add(size-1, cartLine);
        }else{
            mainController.getCartLineList().add(cartLine);
        }
        mainController.getCartLineList().add(totalPriceLine);
        cart_list_view.setItems(mainController.getCartLineList());
    }

    /**
     * Method is called when "log out" button is clicked.
     * Changes scene to loginView.
     * @param event that triggered method call.
     * @throws Exception if the resource is null.
     */
    @FXML
    private void homeLogOut(ActionEvent event)throws Exception{
        productTable.getItems().clear();
        mainController.clearDataCustomer();
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, mainController.MAIN_STAGE_WIDTH, mainController.MAIN_STAGE_HEIGHT));
        root.requestFocus();
        stage.show();
    }

    /**
     * Method is called when "buy" button is clicked.
     * Changes scene to confirmOrderView.
     * @param event that triggered method call.
     * @throws Exception if the resource is null.
     */
    @FXML
    private void placeOrder(ActionEvent event)throws Exception{
    	throw new Exception("FIX placeOrder() in CustomerHomeCOntroller");
//        Order order = new Order();
//        order.setUserId(mainController.getLoginAccount().getUserId());
//        order.setDate(new Date());
//        order.setOrderLines(mainController.getOrderLineList());
//        order.setTotalPrice(mainController.getTotalPrice());
//        if(!order.getOrderLines().isEmpty()){
//            if(mainController.sendOrderToDatabase(order)){
//                cart_list_view.getItems().clear();
//                mainController.clearCart();
//                alert = new Alert(Alert.AlertType.CONFIRMATION, "Order is sent", ButtonType.OK);
//                alert.showAndWait();
//            }else{
//                alert = new Alert(Alert.AlertType.ERROR, "Order is not sent", ButtonType.OK);
//                alert.showAndWait();
//            }
//        }
    }

    /**
     * Method is called when "cancel" button is clicked.
     * Empties cart and set total price to 0.
     * @param event that triggered method call.
     */
    @FXML
    private void cancelOrder(ActionEvent event){ //cancel button
        cart_list_view.getItems().clear();
        mainController.clearCart();
    }
}
