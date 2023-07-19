package me.saniukvyacheslav.gui.controllers;

import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.File;

public class MainSceneController {

    @Setter
    private Stage stage;

    @Setter
    private VBox rootPane;

    public void selectPropertiesFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose properties file:");
        File propertiesFile = fileChooser.showOpenDialog(this.stage);
        System.out.println(propertiesFile.getPath());
    }

}
