package me.saniukvyacheslav.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;
import me.saniukvyacheslav.gui.controllers.menu.TopMenuController;
import me.saniukvyacheslav.gui.controllers.statusline.StatusLineController;

/**
 * Main JavaFx controller.
 */
public class PrimaryNodeController {

    // Inner controllers:
    @FXML @Getter private TopMenuController topMenuController;
    @FXML @Getter private StatusLineController statusLineController;
    @FXML @Getter private PropertiesTableController propertiesTableController; // PropertiesTableController controller;
    @Getter @Setter private Node primaryNode; // Primary node;

}
