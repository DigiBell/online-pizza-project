package com.mycompany.onlinepizzaproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController  {
    @FXML private CustomerHomeController customerHomeController;
    @FXML private CreateAccountController createAccountController;
    @FXML private ManagerHomeController managerHomeController;
    @FXML private MainController mainController;
    @FXML private TextField text_email;
    @FXML private TextField text_password;
    @FXML private Pane main_frame_pane;

    private Alert alert;
    private Stage stage;
    private Parent root;
    //private FXMLLoader loader;

    @FXML
    private void initialize(){
        mainController = MainController.getMainControllerInstance();
    }

    /**
     * Is called when "login" button is pressed.
     * If user has user access level is 1, user is redirected to "user home" page.
     * If user has user access level is 2, user is redirected to "employee home" page.
     * If user has user access level is 3, user is redirected to "manager home" page.
     *
     * @param event that triggered method call.
     * @throws Exception if scene cant be fount.
     */
    @FXML
    private void login_into_system(ActionEvent event) throws Exception {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        String userAccessLevel = checkUser(text_email.getText(),text_password.getText());
        System.out.println( "" + userAccessLevel);

        if(userAccessLevel.equals(MainController.CUSTOMER_ACCESS_LEVEL)){
            changeToCustomerHomeView();
        }else if(userAccessLevel.equals(MainController.EMPLOYEE_ACCESS_LEVEL)){

        }else if(userAccessLevel.equals(MainController.MANAGER_ACCESS_LEVEL)){
            alert = new Alert(Alert.AlertType.ERROR, "Choose login way", ButtonType.OK, ButtonType.CANCEL );
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Manager");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Customer");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                changeToManagerHomeView();
            }else{
                changeToCustomerHomeView();
            }
        }
    }

    /**
     * Is called when "create new account" button is pressed.
     * Account is redirected to "create new user account" page.
     * @param event that triggered method call.
     * @throws Exception if scene cant be fount.
     */
    @FXML
    private void changeToCreateAccountView(ActionEvent event) throws Exception {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAccountView.fxml"));
        root = loader.load();
        createAccountController = loader.getController();
        stage.setScene(new Scene(root, MainController.MAIN_STAGE_WIDTH, MainController.MAIN_STAGE_HEIGHT));
        root.requestFocus();
        stage.show();
    }

    /**
     * Load scene UserHomeView into existing stage.
     * @throws Exception if scene cant be fount.
     */
    private void changeToCustomerHomeView()throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerHomeView.fxml"));
        root = loader.load();
        customerHomeController = loader.getController();
        stage.setScene(new Scene(root, MainController.MAIN_STAGE_WIDTH, MainController.MAIN_STAGE_HEIGHT));
        root.requestFocus();
        stage.show();
    }

    /**
     * Load scene UserHomeView into existing stage.
     * @throws Exception if scene cant be fount.
     */
    private void changeToManagerHomeView()throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerHomeView.fxml"));
        root = loader.load();
        managerHomeController = loader.getController();
        stage.setScene(new Scene(root, MainController.MAIN_STAGE_WIDTH, MainController.MAIN_STAGE_HEIGHT));
        root.requestFocus();
        stage.show();
    }


    /**
     * Checks if an account with provided account already exist.
     * If it exist, than password from input and password from database are compared.
     * On match, confirmation message is shown and user is redirected to UserHomeView.
     * On mismatch, error message is shown.
     * If email does not exist, than an error message is shown.
     * @param email from input.
     * @param password from input.
     * @return user access level, 1 for customer, 2 for employee, 3 for manager, 0 on fail.
     */
    private String checkUser(String email, String password){
        if(!email.isEmpty() && !password.isEmpty()){
            if(!mainController.findAccountByEmailPassword(email, password)){ //         -----//CALL TO DATABASE//-----
                alert = new Alert(Alert.AlertType.ERROR, "Email or password is incorrect.", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    main_frame_pane.requestFocus();
                    text_email.clear();
                    text_password.clear();
                }
            }else{
                return mainController.getLoginAccount().getAccess();
            }
        }else{
            alert = new Alert(Alert.AlertType.ERROR, "Fields can't be empty.", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                main_frame_pane.requestFocus();
            }
        }
        return "0"; // account does not exist
    }
}
