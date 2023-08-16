package me.saniukvyacheslav.gui.controllers.table;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import me.saniukvyacheslav.core.controller.RepositoryController;
import me.saniukvyacheslav.core.properties.PropertiesServiceFactory;
import me.saniukvyacheslav.core.repo.PropertiesRepository;
import me.saniukvyacheslav.core.repo.RepositoryTypes;
import me.saniukvyacheslav.gui.dialogs.ApplicationDialogs;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.models.table.PropertiesTableModel;
import me.saniukvyacheslav.gui.views.table.PropertiesTableView;
import me.saniukvyacheslav.prop.Property;
import me.saniukvyacheslav.services.PropertiesService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * PropertiesTable controller.
 * This controller handler {@link me.saniukvyacheslav.gui.controllers.menu.events.FileMenuEvents} FileMenu menu events.
 * It's an entry point for working with {@link PropertiesTableModel} model.
 */
public class PropertiesTableController implements Initializable, Observer {

    // Embedded nodes:
    @FXML private GridPane propertiesTable; // Embedded GridPane layout;
    // Class variables:
    private PropertiesTableModel propertiesTableModel; // Properties table model;
    private PropertiesRepository propertiesRepository; // Properties service;
    private String currentPropertiesFilePath; // Path to current properties file;
    // States:
    private boolean IS_NEW = false; // Is properties file is new - flag;
    private boolean IS_OPENED = false; // Is properties file opened flag;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Construct model object:
        this.propertiesTableModel = new PropertiesTableModel(this.propertiesTable);
        // Construct view object and stylize properties table:
        new PropertiesTableView(this.propertiesTable).stylize();
    }

    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {

        switch (event.getCode()) {
            case 101: // NEW_FILE_EVENT event:
                this.newPropertiesFile();
                break;
            case 550:
                // Parse arguments and load properties from repository:
                if(arguments[0] == RepositoryTypes.FileRepository) {
                    this.loadPropertiesFile((File) arguments[1]);
                }
                break;
            case 105: // CLOSE_FILE_EVENT event:
                this.closePropertiesFile();
        }

    }

    /**
     * Initialize new empty properties table and set model state "isNew" to true.
     */
    public void newPropertiesFile() {
        // Check if other properties file is opened:
        if (this.IS_NEW || this.IS_OPENED) this.closePropertiesFile();

        // Initialize new empty table:
        this.propertiesTableModel.newTable();

        // Set state:
        this.IS_NEW = true;
    }

    public void loadPropertiesFile(File aPropertiesFile) {
        // Check if other properties file is opened:
        if (this.IS_NEW || this.IS_OPENED) this.closePropertiesFile();

        if (this.propertiesRepository != null) this.propertiesRepository = null;
        this.propertiesRepository = RepositoryController.getInstance().getPropertiesRepository();

        // Load properties from properties file:
        List<Property> loadedProperties;
        try {
            loadedProperties = this.propertiesRepository.list();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Load in model:
        this.propertiesTableModel.loadProperties(loadedProperties);

        // Set state and path to opened file:
        this.IS_OPENED = true;
        this.currentPropertiesFilePath = aPropertiesFile.getAbsolutePath();
    }

    /**
     * Close current properties file.
     * Clear embedded properties table and show default layout.
     * If properties table has unsaved changes, then method offer save properties file to user.
     */
    public void closePropertiesFile() {

        // Check for unsaved changes in table model:
        if (this.propertiesTableModel.isUnsavedChanges()) {
            Optional<ButtonType> userAnswer = ApplicationDialogs.saveFileDialog(this.currentPropertiesFilePath).showAndWait();
            System.out.println(userAnswer);
        }

        // Clear table model:
        this.propertiesTableModel.closeTable();
        System.out.println("Properties file was closed;");

        // Set states:
        this.IS_OPENED = false;
        this.IS_NEW = false;
        // Zeroing vars:
        this.currentPropertiesFilePath = null;
    }

}
