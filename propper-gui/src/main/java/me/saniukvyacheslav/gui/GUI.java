package me.saniukvyacheslav.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.saniukvyacheslav.Main;
import me.saniukvyacheslav.gui.controllers.PrimaryNodeController;

import java.util.Objects;

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

        // Create primary scene and add css to it:
        Scene primaryScene = new Scene((Parent) primaryNode, 800, 600);

        String styleshet = Objects.requireNonNull(Main.class.getResource("/css/properties_table_styles.css")).toExternalForm();
        String statusLineStyles = Objects.requireNonNull(Main.class.getResource("/css/status_line_styles.css")).toExternalForm();
        primaryScene.getStylesheets().addAll(
                styleshet,
                statusLineStyles
        );

        // Initialize GUI Configuration:
        GuiConfiguration.getInstance().init(primaryNodeController);

        // Create primary scene and set it to primary stage:
        aPrimaryStage.setScene(primaryScene);
        aPrimaryStage.show();

    }


}
