package me.saniukvyacheslav.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.saniukvyacheslav.Main;
import me.saniukvyacheslav.gui.controllers.PrimaryNodeController;

public class GUI {

    public static void runGUI(Stage aPrimaryStage) throws Exception {
        // Set application title:
        aPrimaryStage.setTitle(String.format("%s %s", Main.APPLICATION_NAME, Main.APPLICATION_VERSION));

        // Load XML configuration:
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/main_scene.fxml"));

        // Get main scene controller:
        Node primaryNode = fxmlLoader.load();
        PrimaryNodeController primaryNodeController = fxmlLoader.getController();

        // Create primary scene and set it to primary stage:
        aPrimaryStage.setScene(new Scene((Parent) primaryNode, 800, 600));
        aPrimaryStage.show();

    }


}
