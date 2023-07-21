package me.saniukvyacheslav.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
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
    private Parent topMenu;
    @FXML
    private GridPane propertiesTable;
    // Views:
    PropertiesTableView propertiesTableView;
    // Inner controllers:
    @FXML @Getter
    private TopMenuController topMenuController;
    // Class variables:
    private PropertiesService propertiesService; // Properties service;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Draw empty properties table:
        this.propertiesTableView = new PropertiesTableView(this.propertiesTable);
        this.propertiesTableView.draw();

        // Configure top menu controller:
        System.out.println(this.topMenu.getScene());
        System.out.println(this.propertiesTable.getScene());
        this.topMenuController.subscribe(this, TopMenuEvents.OPEN_FILE);

    }

    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {
        System.out.println(arguments.length);
        System.out.println("Update action!");
        File propertiesFile = (File) arguments[0];
        System.out.println(propertiesFile.getPath());

        if (event == TopMenuEvents.OPEN_FILE)
            this.loadPropertiesFile((File) arguments[0]);
    }

    public void loadPropertiesFile(File aPropertiesFile) {
        // Initialize properties service:
        if (this.propertiesService != null) this.propertiesService = null;
        this.propertiesService = PropertiesServiceFactory.fileService(aPropertiesFile);

        // Load properties from file:
        try {
            List<Property> loadedProperties = this.propertiesService.list();

            // Add properties to properties table:
            for (int i=1; i<loadedProperties.size(); i++) {
                // Add property key:
                this.propertiesTable.add(new Label(loadedProperties.get(i-1).getPropertyKey()), 0, i);
                // Add property value:
                this.propertiesTable.add(new Label(loadedProperties.get(i-1).getPropertyValue()), 1, i);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
