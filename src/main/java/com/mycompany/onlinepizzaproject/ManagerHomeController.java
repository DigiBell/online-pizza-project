package com.mycompany.onlinepizzaproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ManagerHomeController {
    @FXML private MainController mainController;
    @FXML private LoginController loginController;
    @FXML private Label manager_welcome_label;
    private Stage stage;

    @FXML
    private void initialize(){
        mainController = MainController.getMainControllerInstance();
        manager_welcome_label.setText("Welcome, " + mainController.getLoginAccount().getFirstName() + "!");
    }

    /**
     * Is called when "create new account" button is pressed.
     * Account is redirected to "create new user account" page.
     * @param event that triggered method call.
     * @throws Exception if scene cant be fount.
     */
    @FXML
    private void createEmployeeAccount(ActionEvent event) throws Exception {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        changeToCreateEmployeeAccount();
    }

    /**
     * Method is called when "log out" button is clicked.
     * Changes scene to loginView.
     * @param event that triggered method call.
     * @throws Exception if the resource is null.
     */
    @FXML
    private void homeLogOut(ActionEvent event)throws Exception{
        mainController.clearDataManager();
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, mainController.MAIN_STAGE_WIDTH, mainController.MAIN_STAGE_HEIGHT));
        root.requestFocus();
        stage.show();
    }

    /**
     * Load stage CreateEmployeeAccountView.
     * @throws Exception
     */
    private void changeToCreateEmployeeAccount() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("CreateEmployeeAccountView.fxml"));
        Stage smallStage = new Stage();
        smallStage.setTitle("Create employee account");
        smallStage.setScene(new Scene(root, 600, 600));
        root.requestFocus();
        smallStage.showAndWait();
    }
}
