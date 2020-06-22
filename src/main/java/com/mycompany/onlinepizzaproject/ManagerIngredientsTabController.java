package com.mycompany.onlinepizzaproject;

import com.mycompany.onlinepizzaproject.Model.Ingredient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ManagerIngredientsTabController {
    @FXML private MainController mainController;
    @FXML private TableView<Ingredient> ingredientsTable;
    @FXML private TableColumn<Ingredient, String> ingredients_ingredient_id_column;
    @FXML private TableColumn<Ingredient, String> ingredients_name_column;
    @FXML private TableColumn<Ingredient, String> ingredients_description_column;
    @FXML private TableColumn<Ingredient, String> ingredients_quantity_column;
    @FXML private TableColumn<Ingredient, String> ingredients_units_column;
    private Alert alert;

    @FXML
    private void initialize(){
        mainController = MainController.getMainControllerInstance();
        ingredients_ingredient_id_column.setCellValueFactory(new PropertyValueFactory<>("IngredientId"));
        ingredients_name_column.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ingredients_description_column.setCellValueFactory(new PropertyValueFactory<>("Description"));
        ingredients_quantity_column.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        ingredients_units_column.setCellValueFactory(new PropertyValueFactory<>("Units"));
    }

    @FXML
    private void showAll(ActionEvent event){
        ingredientsTable.getItems().clear();
        mainController.getIngredientsFromDatabase();
        ingredientsTable.getItems().addAll(mainController.getIngredientList());
    }

    @FXML
    private void edit(ActionEvent event) throws Exception{
        Ingredient ingredient = ingredientsTable.getSelectionModel().getSelectedItem();
        if(ingredient == null){
            alert = new Alert(Alert.AlertType.WARNING, "Choose an ingredient from table.", ButtonType.OK);
            alert.showAndWait();
        }else{
            mainController.setIngredientToChange(ingredient);
            Parent root = FXMLLoader.load(getClass().getResource("ManagerIngredientEditView.fxml"));
            Stage smallStage = new Stage();
            smallStage.setTitle("Ingredient details");
            smallStage.setScene(new Scene(root, 600, 600));
            root.requestFocus();
            smallStage.showAndWait();
        }
    }
}
