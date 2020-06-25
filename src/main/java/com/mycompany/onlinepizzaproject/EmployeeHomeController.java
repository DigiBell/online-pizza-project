package com.mycompany.onlinepizzaproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EmployeeHomeController {
	@FXML
	private MainController mainController;
	@FXML
	private LoginController loginController;
	@FXML
	private Label employee_welcome_label;

	@FXML
	private void initialize() {
		mainController = MainController.getMainControllerInstance();
		employee_welcome_label.setText("Welcome, " + mainController.getLoginAccount().getFirstName() + "!");
	}

	/**
	 * Method is called when "log out" button is clicked. Changes scene to
	 * loginView.
	 * 
	 * @param event that triggered method call.
	 * @throws Exception if the resource is null.
	 */
	@FXML
	private void homeLogOut(ActionEvent event) throws Exception {
		mainController.clearDataManager();
		Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root, MainController.MAIN_STAGE_WIDTH, MainController.MAIN_STAGE_HEIGHT));
		root.requestFocus();
		stage.show();
	}
}
