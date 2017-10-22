package com.evgeniyavakarina.gmail.namesapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class NamesApp extends Application{
    private DBHandler dbHandler;


    @FXML private TextField inputName, inputId;
    @FXML private Button saveButton, findButton;
    @FXML private Label saveNameSuccess, findNameSuccess;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view.fxml"));
        Scene scene = new Scene(root);

        //elements
        inputName = (TextField) scene.lookup("#enter_a_name_text_field");
        saveButton = (Button) scene.lookup("#save_name_button");
        saveButton.setOnAction(this::onSaveName);
        saveNameSuccess = (Label) scene.lookup("#save_success_label");

        //db
        dbHandler = new DBHandler();

        primaryStage.setTitle("Names");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onSaveName(ActionEvent e) {
        String name = inputName.getText();
        if (dbHandler.putName(name)) {
            saveNameSuccess.setText("saved " + name);
        }
    }

    public void findName(ActionEvent e) {

    }

    @Override
    public void stop() throws Exception {
        dbHandler.close();
    }
}
