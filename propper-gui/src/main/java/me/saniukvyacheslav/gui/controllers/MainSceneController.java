package me.saniukvyacheslav.gui.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainSceneController {


    private final Stage primaryStage;
    private VBox mainNode;

    private GridPane propertiesTable;

    public MainSceneController(Stage aPrimaryStage) {
        this.primaryStage = aPrimaryStage;
    }

    public MainSceneController(Stage aPrimaryStage, VBox aMainNode) {
        this(aPrimaryStage);
        this.mainNode = aMainNode;

        // Create empty properties table and display it:
        this.propertiesTable = this.createEmptyPropertiesTable();
        this.mainNode.getChildren().add(1, this.propertiesTable);

    }

    private GridPane createEmptyPropertiesTable() {
        // New grid pane:
        GridPane gridPane = new GridPane();

        // Column constraints:
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(50);
        columnConstraints.setHalignment(HPos.CENTER);
        gridPane.getColumnConstraints().add(0, columnConstraints);
        gridPane.getColumnConstraints().add(1, columnConstraints);

        // Create table title row:
        gridPane.add(this.createColumnTitle("Key"), 0,0);
        gridPane.add(this.createColumnTitle("Value"), 1, 0);

        // Set grid visible:
        gridPane.setGridLinesVisible(true);
        return gridPane;
    }

    private Label createColumnTitle(String aTitle) {
        Label label = new Label(aTitle);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 12));

        return label;
    }

    public void selectPropertiesFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose properties file:");
        File propertiesFile = fileChooser.showOpenDialog(this.primaryStage);
        System.out.println(propertiesFile.getPath());
    }

}
