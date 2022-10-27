package com.example.project3gym;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GymManagerController {
    @FXML
    private Label tempText;

    @FXML
    protected void onAddButtonClick() {
        tempText.setText("Enter in the following to register: ");
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