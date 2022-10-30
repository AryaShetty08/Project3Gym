package com.example.project3gym;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class GymManagerController {
    @FXML
    private Label tempText;
    private Button addButton;
    public TextArea fName;

    @FXML
    protected void onAddButtonClick() {
        tempText.setText("Successfully Registered, name is" + fName.getText());

    }

    @FXML
    protected void onRemoveButtonClick() {
        tempText.setText("Enter in the following to cancel membership: ");
    }

    @FXML
    protected void onQuitButtonClick() {
        System.exit(0);
    }

    @FXML
    protected void onDisplayButtonClick() {

    }

}