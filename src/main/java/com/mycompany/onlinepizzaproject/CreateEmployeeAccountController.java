package com.mycompany.onlinepizzaproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.onlinepizzaproject.Model.Account;
import com.mycompany.onlinepizzaproject.backend.API;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateEmployeeAccountController {
    @FXML private MainController mainController;
    @FXML private TextField create_email_field;
    @FXML private TextField create_password_field;
    @FXML private TextField create_first_name_field;
    @FXML private TextField create_last_name_field;
    @FXML private TextField create_country_field;
    @FXML private TextField create_city_field;
    @FXML private TextField create_street_field;
    @FXML private TextField create_postal_code_field;
    @FXML private TextField create_phone_number_field;
    @FXML private ChoiceBox<String> create_country_choicebox = new ChoiceBox<String>();

    private Alert alert;
    private Stage stage;
    private Account newAccount = new Account();

    @FXML
    private void initialize(){
        mainController = MainController.getMainControllerInstance();
        
        File file = new File("C:\\Users\\akira\\eclipse-workspace\\online-pizza-project\\text\\countries.txt");
        BufferedReader br;
        List<String> countries = new ArrayList<>();
        countries.add("Select country");
		try {
			br = new BufferedReader(new FileReader(file));
			String str;
	        while ((str = br.readLine()) != null){
	        	System.out.println(str);
	        	countries.add(str);
	        }
	        br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        create_country_choicebox.setItems(FXCollections.observableArrayList(countries));
        create_country_choicebox.getSelectionModel().selectFirst();
    }

    @FXML
    private void createNewEmployeeAccount(ActionEvent event){
        if(getUserInput()){
        	
        	try {
        		String result = API.createEmployee(newAccount.getEmail(), newAccount.getPassword(), newAccount.getFirstName(), newAccount.getLastName(), newAccount.getCountry(), newAccount.getCity(), 
            			newAccount.getStreet(), newAccount.getPostCode(), newAccount.getPhoneNumber());
        		
        		if(result != null) {
        			alert = new Alert(Alert.AlertType.CONFIRMATION, "Account has been successfully created", ButtonType.OK);
        			alert.showAndWait();
        			if (alert.getResult() == ButtonType.OK) {
        				 stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        				 stage.close();
        			}
        		} else {
        			alert = new Alert(Alert.AlertType.ERROR, "Account could not be created", ButtonType.OK);
        			alert.showAndWait();
        		}
        		
			} catch (Exception e) {
				 alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
				 alert.showAndWait();
			}
        	
//            if(mainController.findAccountByEmail(newAccount.getEmail())){
//                alert = new Alert(Alert.AlertType.ERROR, "Account with this email already exist", ButtonType.OK);
//                alert.showAndWait();
//                if (alert.getResult() == ButtonType.OK) {
//                    create_email_field.clear();
//                    create_email_field.requestFocus();
//                }
//            }else{
//                newAccount.setAccess(MainController.EMPLOYEE_ACCESS_LEVEL);
//                if(mainController.sendAccountToDatabase(newAccount)){
//                    alert = new Alert(Alert.AlertType.CONFIRMATION, "Account has been successfully created", ButtonType.OK);
//                    alert.showAndWait();
//                    if (alert.getResult() == ButtonType.OK) {
//                        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
//                        stage.close();
//                    }
//                }else{
//                    alert = new Alert(Alert.AlertType.ERROR, "Account could not be created", ButtonType.OK);
//                    alert.showAndWait();
//                }
//            }
            
        }else{
            alert = new Alert(Alert.AlertType.WARNING, "All fields must be filled.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void cancelEmployeeAccountCreation(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
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
        if (create_country_choicebox.getSelectionModel().getSelectedIndex() == 0){ return false;
        }else{ newAccount.setCountry(create_country_choicebox.getSelectionModel().getSelectedItem()); }
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
