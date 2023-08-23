package me.saniukvyacheslav.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.core.RootConfiguration;
import me.saniukvyacheslav.core.repo.PropertiesRepository;
import me.saniukvyacheslav.gui.dialogs.ApplicationDialogs;
import me.saniukvyacheslav.gui.events.Observable;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.models.PropertiesTableModel;
import me.saniukvyacheslav.prop.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Controller for {@link PropertiesTableModel}.
 * This controller handler {@link me.saniukvyacheslav.core.repo.RepositoryEvents) repository events.
 * JavaFx automatically create instance of this controller and initialize it {@link PropertiesTableController#initialize()}
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PropertiesTableController implements Observer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesTableController.class); // Logger;
    private PropertiesTableModel tableModel; // Properties table model;
    private String currentPropertiesFilePath; // Path to current properties file;
    private boolean IS_NEW = false; // Is properties file is new - flag;
    private boolean IS_OPENED = false; // Is properties file opened flag;
    @FXML private GridPane propertiesTable; // Embedded GridPane layout (JavaFx inject it in initialize());

    /**
     * Initialize this PropertiesTable controller.
     * JavaFx automatically inject {@link GridPane} propertiesTable instance.
     * Method create PropertiesTable model and view instances.
     */
    @FXML
    public void initialize() {
        LOGGER.debug("Try to initialize [PropertiesTableController] controller instance. Check parameter:");
        // Check parameter (JavaFx inject propertiesTable GridPane):
        if (this.propertiesTable == null)
            throw new RuntimeException("GridPane [propertiesTable] automatically injected by JavaFx must be not null.");
        else LOGGER.debug(String.format("GridPane [propertiesTable]: [%s];", this.propertiesTable));

        // Construct model object:
        this.tableModel = new PropertiesTableModel(this.propertiesTable);

        LOGGER.debug("Initialization of this [PropertiesTableController]: SUCCESSFUL;");
    }

    /**
     * Get current PropertiesTable model.
     * @return - model.
     */
    public PropertiesTableModel tableModel() {
        return this.tableModel;
    }

    /**
     * Do something on repository events.
     * @param event - {@link Observable} instance event.
     * @param arguments - event arguments.
     */
    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {

        switch (event.getCode()) {
            case 101: // NEW_FILE_EVENT event:
                this.newPropertiesFile();
                break;
            case 550: // REPOSITORY_OPENED event:
                LOGGER.debug("Event code: [550] - REPOSITORY_OPENED event.");
                this.onRepositoryOpenedEvent();
                break;
            case 551: // REPOSITORY_CHANGES_SAVED:
                this.onRepositorySavedEvent();
                break;
            case 105: // CLOSE_FILE_EVENT event:
                this.closePropertiesFile();
        }

    }

    /**
     * Action on [550] REPOSITORY_OPENED event.
     * Method load properties from repository in PropertiesTable.
     * See {@link PropertiesTableController#loadPropertiesFromRepository(PropertiesRepository)}.
     * Method get {@link PropertiesRepository} repository from {@link RootConfiguration} configuration.
     */
    public void onRepositoryOpenedEvent() {
        LOGGER.debug("REPOSITORY_OPENED [550] event. Try to load properties from RootConfiguration#properitesRepository:");
        this.loadPropertiesFromRepository(RootConfiguration.getInstance().getPropertiesRepository());
    }

    /**
     * Load properties from repository and insert them into PropertiesTable model.
     * @param aRepository - PropertiesRepository repository.
     */
    public void loadPropertiesFromRepository(PropertiesRepository aRepository) {
        LOGGER.debug("Try to load properties from repository. Check parameter:");
        Objects.requireNonNull(aRepository, "PropertiesRepository [aRepository] must be not null.");
        LOGGER.debug(String.format("PropertiesRepository: [%s];", aRepository));

        // Load properties from repository:
        List<Property> loadedList;
        try {
            loadedList = aRepository.list();
            LOGGER.debug(String.format("Loaded: [%d] properties;", loadedList.size()));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }

        // Load loaded properties in table model:
        LOGGER.debug("Insert loaded properties in table model:");
        this.tableModel.loadIntoTable(loadedList);
    }

    public void onRepositorySavedEvent() {
        // Remove "updated" css class from all property views in table:
        this.tableModel.removeUpdatedClassFromPropertiesModels();
    }

    /**
     * Initialize new empty properties table and set model state "isNew" to true.
     */
    public void newPropertiesFile() {
        // Check if other properties file is opened:
        if (this.IS_NEW || this.IS_OPENED) this.closePropertiesFile();

        // Initialize new empty table:
        //this.tableModel.newTable();

        // Set state:
        this.IS_NEW = true;
    }

    /**
     * Close current properties file.
     * Clear embedded properties table and show default layout.
     * If properties table has unsaved changes, then method offer save properties file to user.
     */
    public void closePropertiesFile() {

        // Check for unsaved changes in table model:
        if (this.tableModel.isUnsavedChanges()) {
            Optional<ButtonType> userAnswer = ApplicationDialogs.saveFileDialog(this.currentPropertiesFilePath).showAndWait();
            System.out.println(userAnswer);
        }

        // Clear table model:
        this.tableModel.closeTable();
        System.out.println("Properties file was closed;");

        // Set states:
        this.IS_OPENED = false;
        this.IS_NEW = false;
        // Zeroing vars:
        this.currentPropertiesFilePath = null;
    }

}
