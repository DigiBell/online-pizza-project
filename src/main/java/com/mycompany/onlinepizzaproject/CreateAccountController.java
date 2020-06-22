package com.mycompany.onlinepizzaproject;

import com.mycompany.onlinepizzaproject.Model.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

public class CreateAccountController {
    @FXML private MainController mainController;
    @FXML private LoginController loginController;
    @FXML private TextField create_email_field;
    @FXML private TextField create_password_field;
    @FXML private TextField create_first_name_field;
    @FXML private TextField create_last_name_field;
    @FXML private TextField create_country_field;
    @FXML private TextField create_city_field;
    @FXML private TextField create_street_field;
    @FXML private TextField create_postal_code_field;
    @FXML private TextField create_phone_number_field;
    @FXML private List<TextField> form;
    @FXML private Pane main_frame_pane;

    private Alert alert;
    private Stage stage;
    private FXMLLoader loader;
    private Account newAccount = new Account();

    @FXML
    private void initialize(){
        mainController = MainController.getMainControllerInstance();
    }

    /**
     * Is called when "create account" button is clicked.
     * On successful creation, the scene is changed to UserHomeView/ManagerHomeView.
     *
     * @param event that triggered method call.
     * @throws Exception if scene cant be found.
     */
    public void createNewAccount(ActionEvent event)throws Exception{
        if(getUserInput()){
            if(mainController.findAccountByEmail(newAccount.getEmail())){
                alert = new Alert(Alert.AlertType.ERROR, "Account with this email already exist", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    create_email_field.clear();
                    create_email_field.requestFocus();
                }
            }else{
                newAccount.setAccess(MainController.CUSTOMER_ACCESS_LEVEL);
                if(mainController.sendAccountToDatabase(newAccount)){
                    alert = new Alert(Alert.AlertType.CONFIRMATION, "Account has been successfully created", ButtonType.OK);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.OK) {
                        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        changeToLoginScene();
                    }
                }else{
                    alert = new Alert(Alert.AlertType.ERROR, "Account could not be created", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        }else{
            alert = new Alert(Alert.AlertType.WARNING, "All fields must be filled.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Is called when "cancel" button is clicked, the scene is then changed to LoginView.
     * @param event that triggered method call.
     * @throws Exception if scene cant be found.
     */
    public void cancelAccountCreation(ActionEvent event)throws Exception {
        //open login window
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        changeToLoginScene();
    }

    /**
     * Load a new scene into existing stage based on parameters.
     * @throws Exception if scene cant be found.
     */
    private void changeToLoginScene() throws Exception {
        loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        Parent root = loader.load();
        loginController = loader.getController();
        stage.setScene(new Scene(root, MainController.MAIN_STAGE_WIDTH, MainController.MAIN_STAGE_HEIGHT));
        root.requestFocus();
        stage.show();
    }

    /**
     * Read data from text fields and saves it to newUser instance in MainController.
     */
    private boolean getUserInput(){
        if (create_email_field.getText().isEmpty()){ return false;
        }else{ newAccount.setEmail(create_email_field.getText()); }
        if (create_password_field.getText().isEmpty()){ return false;
        }else{ newAccount.setPassword(create_password_field.getText()); }
        if (create_first_name_field.getText().isEmpty()){ return false;
        }else{ newAccount.setFirstName(create_first_name_field.getText()); }
        if (create_last_name_field.getText().isEmpty()){ return false;
        }else{ newAccount.setLastName(create_last_name_field.getText()); }
        if (create_country_field.getText().isEmpty()){ return false;
        }else{ newAccount.setCountry(create_country_field.getText()); }
        if (create_city_field.getText().isEmpty()){ return false;
        }else{ newAccount.setCity(create_city_field.getText()); }
        if (create_street_field.getText().isEmpty()){ return false;
        }else{ newAccount.setStreet(create_street_field.getText()); }
        if (create_postal_code_field.getText().isEmpty()){ return false;
        }else{ newAccount.setPostCode(create_postal_code_field.getText()); }
        if (create_phone_number_field.getText().isEmpty()){ return false;
        }else{ newAccount.setPhoneNumber(create_phone_number_field.getText()); }
        return true;
    }
}
