package me.saniukvyacheslav.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import me.saniukvyacheslav.core.properties.PropertiesServiceFactory;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.events.topmenu.TopMenuEvents;
import me.saniukvyacheslav.gui.views.PropertiesTableView;
import me.saniukvyacheslav.prop.Property;
import me.saniukvyacheslav.services.PropertiesService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class PrimaryNodeController implements Initializable, Observer {

    // Inner nodes:
    @FXML
    private GridPane propertiesTable;
    // Inner controllers:
    @FXML @Getter
    private TopMenuController topMenuController;
    @FXML
    private StatusLineController statusLineController;
    // Class variables:
    private PropertiesService propertiesService; // Properties service;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Configure top menu controller:
        // Subscribe observers:
        this.topMenuController.subscribe(this, TopMenuEvents.OPEN_FILE);
        this.topMenuController.subscribe(this.statusLineController, TopMenuEvents.OPEN_FILE);
    }

    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {
        if (event == TopMenuEvents.OPEN_FILE)
            this.loadPropertiesFile((File) arguments[0]);
    }

    /**
     *  Load properties from specified properties file and add it to properties table.
     * @param aPropertiesFile - properties file.
     */
    public void loadPropertiesFile(File aPropertiesFile) {
        // Initialize properties service:
        if (this.propertiesService != null) this.propertiesService = null;
        this.propertiesService = PropertiesServiceFactory.fileService(aPropertiesFile);

        // Load properties from file:
        try {
            List<Property> loadedProperties = this.propertiesService.list();

            // Create empty table view:
            new PropertiesTableView(this.propertiesTable).drawEmpty();

            // Add properties to properties table:
            for (int i=1; i<loadedProperties.size(); i++) {
                // Add property key:
                this.propertiesTable.add(PropertiesTableView.createPropertyCellNode(loadedProperties.get(i-1).getPropertyKey(), false), 0, i);
                // Add property value:
                this.propertiesTable.add(PropertiesTableView.createPropertyCellNode(loadedProperties.get(i-1).getPropertyValue(), true), 1, i);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
