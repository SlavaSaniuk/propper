package me.saniukvyacheslav;

import javafx.application.Application;

import javafx.stage.Stage;
import me.saniukvyacheslav.gui.GUI;


public class Main extends Application {

    public static final String APPLICATION_NAME = "propper-gui";
    public static final String APPLICATION_VERSION = "0.0.1";

    private static Stage primaryStage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        GUI.runGUI(primaryStage);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
