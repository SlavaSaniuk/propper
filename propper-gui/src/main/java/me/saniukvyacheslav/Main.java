package me.saniukvyacheslav;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.saniukvyacheslav.gui.PropertiesTable;
import me.saniukvyacheslav.gui.controllers.MainSceneController;

public class Main extends Application {

    private static final String APPLICATION_NAME = "propper-gui";
    private static final String APPLICATION_VERSION = "0.0.1";

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set application title:
        primaryStage.setTitle(String.format("%s %s", APPLICATION_NAME, APPLICATION_VERSION));

        // Load XML configuration:
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/main_scene.fxml"));
        VBox rootPane = fxmlLoader.load();

        MainSceneController mainSceneController = fxmlLoader.getController();
        mainSceneController.setStage(primaryStage);
        mainSceneController.setRootPane(rootPane);
        // Properties table:
        PropertiesTable table = new PropertiesTable();

        rootPane.getChildren().add(table.drawPropertiesTable());




        primaryStage.setScene(new Scene(rootPane, 800, 600));
        primaryStage.show();
    }
}
