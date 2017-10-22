package com.evgeniyavakarina.gmail.namesapp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NamesApp extends Application{
    private DBHandler dbHandler;


    @FXML private TextField inputName, inputId;
    @FXML private Button saveButton, findButton;
    @FXML private Label saveNameSuccess, findNameSuccess;
    @FXML private ListView<String> nameDataListView;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view.fxml"));
        Scene scene = new Scene(root);
        dbHandler = new DBHandler();

        //elements
        inputName = (TextField) scene.lookup("#enter_a_name_text_field");
        saveButton = (Button) scene.lookup("#save_name_button");
        saveButton.setOnAction(this::onSaveName);
        saveNameSuccess = (Label) scene.lookup("#save_success_label");

        inputId = (TextField) scene.lookup("#enter_id_text_field");
        findButton = (Button) scene.lookup("#find_name_button");
        findButton.setOnAction(this::findName);
        findNameSuccess = (Label) scene.lookup("#find_name_success_label");

        ObservableList<String> items = FXCollections.observableArrayList(dbHandler.getNames());
        nameDataListView = (ListView<String>) scene.lookup("#name_data_list_view");
        nameDataListView.setItems(items);

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
        if (inputId.getText().length() == 0) {
            findNameSuccess.setText("success");
        }
        findNameSuccess.setText(dbHandler.getName(Integer.parseInt(inputId.getText())));
    }

    @Override
    public void stop() throws Exception {
        dbHandler.close();
    }
}
