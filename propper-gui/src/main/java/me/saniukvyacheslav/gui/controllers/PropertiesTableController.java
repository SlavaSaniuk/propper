package me.saniukvyacheslav.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import me.saniukvyacheslav.core.properties.PropertiesServiceFactory;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.models.PropertiesTable;
import me.saniukvyacheslav.gui.views.PropertiesTableView;
import me.saniukvyacheslav.prop.Property;
import me.saniukvyacheslav.services.PropertiesService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PropertiesTableController implements Initializable, Observer {

    // Embedded nodes:
    @FXML private GridPane propertiesTable;
    // Class variables:
    private PropertiesTable propertiesTableModel;
    private PropertiesService propertiesService; // Properties service:

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Construct model object:
        this.propertiesTableModel = new PropertiesTable(this.propertiesTable);
        // Construct view object and stylize properties table:
        new PropertiesTableView(this.propertiesTable).stylize();
    }

    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {

        switch (event.getCode()) {
            case 101:
                File propertiesFile = ((File) arguments[0]);
                this.loadPropertiesFile(propertiesFile);
                break;
        }

    }

    public void loadPropertiesFile(File aPropertiesFile) {
        if (this.propertiesService != null) this.propertiesService = null;
        this.propertiesService = PropertiesServiceFactory.fileService(aPropertiesFile);

        // Load properties from properties file:
        List<Property> loadedProperties;
        try {
            loadedProperties = this.propertiesService.list();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Load in model:
        this.propertiesTableModel.loadProperties(loadedProperties);
    }
}