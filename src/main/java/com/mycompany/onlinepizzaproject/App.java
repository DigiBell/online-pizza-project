package com.mycompany.onlinepizzaproject;


import com.mycompany.onlinepizzaproject.backend.API;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        primaryStage.setTitle("Online Pizza");
        primaryStage.setScene(new Scene(root, MainController.MAIN_STAGE_WIDTH, MainController.MAIN_STAGE_HEIGHT));
        root.requestFocus();
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.out.println("Main");
       
        //API.rebuildDB();
        
        API.createIndexes();
        
        launch(args);
    }
}
