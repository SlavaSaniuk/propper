package me.saniukvyacheslav.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import me.saniukvyacheslav.gui.controllers.menu.TopMenuController;
import me.saniukvyacheslav.gui.controllers.menu.events.FileMenuEvents;
import me.saniukvyacheslav.gui.controllers.props.PropertyChangesController;
import me.saniukvyacheslav.gui.controllers.props.PropertyEvents;
import me.saniukvyacheslav.gui.controllers.table.PropertiesTableController;

import java.net.URL;
import java.util.ResourceBundle;


public class PrimaryNodeController implements Initializable {

    // Inner controllers:
    @FXML private TopMenuController topMenuController;
    @FXML private StatusLineController statusLineController;
    @FXML private PropertiesTableController propertiesTableController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Subscribe observers:
        this.subscribeObservers();

        // On PropertyChangesController:
        PropertyChangesController.getInstance().subscribe(this.statusLineController, PropertyEvents.PROPERTY_UPDATE_EVENT);
    }

    /**
     * Subscribe observer to controllers.
     * Subscribe {@link PropertiesTableController} on FileMenu {@link FileMenuEvents} (NEW, OPEN, SAVE, CLOSE) events.
     */
    private void subscribeObservers() {
        // +++ Top menu:
        // ++++++ FileMenu:
        this.topMenuController.subscribeOnFileMenuEvents(this.propertiesTableController, FileMenuEvents.NEW_FILE_EVENT, FileMenuEvents.OPEN_FILE_EVENT, FileMenuEvents.CLOSE_FILE_EVENT);
        this.topMenuController.subscribeOnFileMenuEvents(this.statusLineController, FileMenuEvents.OPEN_FILE_EVENT, FileMenuEvents.CLOSE_FILE_EVENT);
    }



}
