package com.mycompany.onlinepizzaproject;

import com.mycompany.onlinepizzaproject.Model.Ingredient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManagerIngredientEditController {
    @FXML private MainController mainController;
    @FXML private ListView<String> ingredient_edit_list_view;
    @FXML private TextField ingredient_edit_quantity_field;


    private Stage stage;

    @FXML private void initialize(){
        mainController = MainController.getMainControllerInstance();
        Ingredient ingredient = mainController.getIngredientToChange();
        ObservableList<String> ingredientLines = FXCollections.observableArrayList();
        ingredientLines.add("Ingredient id: " + ingredient.getId());
        ingredientLines.add("Ingredient name: " + ingredient.getName());
        ingredientLines.add("Description: " + ingredient.getDescription());
        ingredientLines.add("Quantity: " + ingredient.getQuantity());
        ingredientLines.add("Units: " + ingredient.getUnits());
        ingredient_edit_list_view.setItems(ingredientLines);
    }

    @FXML
    private void confirmChange(ActionEvent event){

        if(!ingredient_edit_quantity_field.getText().isEmpty()){
            String str = ingredient_edit_quantity_field.getText();

            if(mainController.updateIngredient(Integer.valueOf(str))){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update successful.", ButtonType.OK);
                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Update failed.", ButtonType.OK);
                alert.showAndWait();
            }
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
