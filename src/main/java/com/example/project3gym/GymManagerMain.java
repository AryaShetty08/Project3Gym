package com.example.project3gym;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Driver class to run Project 3
 * @author Arya Shetty, John Greaney-Cheng
 */
public class GymManagerMain extends Application {
    /**
     * Initializes stage and scene for Gym Manager GUI
     * Defines stage qualities such as:
     *  - size of window
     *  - title
     *  - not resizable
     * @param stage the window that contains Gym Manager GUI scene
     * @throws IOException throws IOException to check fxmlLoader exceptions
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GymManagerMain.class.getResource("GymManagerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 425*2, 330*2);
        stage.setTitle("GymManagerMain!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches JavaFX Application
     * @param args Standard main parameter
     */
    public static void main(String[] args) {
        launch();
    }
}