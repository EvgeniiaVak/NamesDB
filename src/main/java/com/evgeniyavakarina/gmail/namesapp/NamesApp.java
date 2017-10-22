package com.evgeniyavakarina.gmail.namesapp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class NamesApp extends Application{
    private DBHandler dbHandler;

    @FXML private TextField inputName, inputId;
    @FXML private Button saveButton, findButton;
    @FXML private Label saveNameSuccess;
    @FXML private ListView<String> nameDataListView;
    @FXML private Spinner<Integer> beginSpinner, endSpinner;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view.fxml"));
        Scene scene = new Scene(root);
        dbHandler = new DBHandler();

        //save tab elements
        inputName = (TextField) scene.lookup("#enter_a_name_text_field");
        saveButton = (Button) scene.lookup("#save_name_button");
        saveButton.setOnAction(this::onSaveName);
        saveNameSuccess = (Label) scene.lookup("#save_success_label");

        //search tab elements
        inputId = (TextField) scene.lookup("#enter_id_text_field");
        findButton = (Button) scene.lookup("#find_name_button");
        findButton.setOnAction(this::findName);

        ObservableList<String> items = FXCollections.observableArrayList(dbHandler.getNames());
        nameDataListView = (ListView<String>) scene.lookup("#name_data_list_view");
        nameDataListView.setItems(items);

        beginSpinner = (Spinner<Integer>) scene.lookup("#begin_spinner");
        endSpinner = (Spinner<Integer>) scene.lookup("#end_spinner");
        updateListRange();

        primaryStage.setTitle("Names");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onSaveName(ActionEvent e) {
        String name = inputName.getText();
        if (dbHandler.putName(name)) {
            saveNameSuccess.setText("saved " + name);
            updateListRange();
        }
    }

    public void findName(ActionEvent e) {
        ArrayList<String> updatedList = null;

        if (inputId.getText().length() == 0) {
            updatedList = dbHandler.getNames(beginSpinner.getValue(), endSpinner.getValue());
        } else {
            updatedList = dbHandler.getNames(Integer.parseInt(inputId.getText()));
        }

        nameDataListView.setItems(FXCollections.observableArrayList(updatedList));
    }

    @Override
    public void stop() throws Exception {
        dbHandler.close();
    }

    private void updateListRange() {
        int max = dbHandler.getNumberOfNames();
        beginSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, max));
        beginSpinner.getValueFactory().setValue(1);
        endSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, max));
        endSpinner.getValueFactory().setValue(max);
        findName(null);
    }
}
