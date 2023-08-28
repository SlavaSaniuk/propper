package me.saniukvyacheslav.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.core.RootConfiguration;
import me.saniukvyacheslav.core.repo.PropertiesRepository;
import me.saniukvyacheslav.core.repo.RepositoryEvents;
import me.saniukvyacheslav.gui.GuiConfiguration;
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

/**
 * Controller for {@link PropertiesTableModel}.
 * This controller handler {@link me.saniukvyacheslav.core.repo.RepositoryEvents) repository events.
 * JavaFx automatically create instance of this controller and initialize it {@link PropertiesTableController#initialize()}
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PropertiesTableController implements Observer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesTableController.class); // Logger;
    private PropertiesTableModel tableModel; // Properties table model;
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
            case 550: // REPOSITORY_OPENED event:
                LOGGER.debug("Event code: [550] - REPOSITORY_OPENED event.");
                this.onRepositoryOpenedEvent();
                break;
            case 551: // REPOSITORY_CHANGES_SAVED event:
                LOGGER.debug("Event code: [551] - REPOSITORY_CHANGES_SAVED event.");
                this.onRepositoryChangesSavedEvent();
                break;
            case 552: // REPOSITORY_CLOSED event:
                LOGGER.debug(String.format("[%d: %s] event. Clear this properties table:",
                        RepositoryEvents.REPOSITORY_CLOSED.getCode(), RepositoryEvents.REPOSITORY_CLOSED.name()));
                this.onRepositoryClosedEvent();
                break;
            default:
                LOGGER.warn(String.format("Event [eventCode: %d] is not supported;", event.getCode()));
        }

    }

    /**
     * Action on [550] REPOSITORY_OPENED event.
     * Method load properties from repository in PropertiesTable.
     */
    public void onRepositoryOpenedEvent() {
        LOGGER.debug("REPOSITORY_OPENED [550] event. Try to load properties from RootConfiguration#properitesRepository:");
        this.loadPropertiesFromRepository();
    }

    /**
     * Load properties from repository and insert them into PropertiesTable model.
     */
    public void loadPropertiesFromRepository() {
        PropertiesRepository aRepository = RootConfiguration.getInstance().getPropertiesRepository();
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

    /**
     * Action on [551] REPOSITORY_CHANGES_SAVED event.
     * Method remove "updated" css pseudo class from properties models in table.
     */
    public void onRepositoryChangesSavedEvent() {
        LOGGER.debug("REPOSITORY_REPOSITORY_CHANGES_SAVED [551] event. Remove [UPDATED] css pseudo class from properties in table:");

        // Remove "updated" css class from all property views in table:
        this.tableModel.clearUpdatedClass();

        // Clear updates map in PropertyChangesController:
        GuiConfiguration.getInstance().getPropertyChangesController().clear();

        // Unset "isUnsaved" state in table:
        this.tableModel.setUnsavedState(false);

        // Reload properties:
        try {
            this.tableModel.loadIntoTable(RootConfiguration.getInstance().getPropertiesRepository().list());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Action on [552] REPOSITORY_CLOSE event.
     * Reset inner properties table model.
     */
    private void onRepositoryClosedEvent() {
        LOGGER.debug("Reset properties table model and draw default layout:");
        this.tableModel.resetTable();
    }

    /**
     * Check for unsaved changes in PropertiesTable model.
     * @return - true if table has unsaved changes.
     */
    public boolean isTableHasUnsavedChanges() {
        LOGGER.debug("Check for unsaved changes in PropertiesTable model:");
        return this.tableModel.isUnsavedChanged();
    }





}
