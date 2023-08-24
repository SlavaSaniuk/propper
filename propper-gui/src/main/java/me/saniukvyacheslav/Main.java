package me.saniukvyacheslav;

import javafx.application.Application;

import javafx.stage.Stage;
import me.saniukvyacheslav.core.PropperGui;
import me.saniukvyacheslav.core.error.ErrorsController;


public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Configure Propper GUI application:
        PropperGui application = PropperGui.getInstance();
        application.setErrorsController(ErrorsController.getInstance()); // Shared errors controller;

        // Start application:
        application.start(primaryStage);
    }
}
