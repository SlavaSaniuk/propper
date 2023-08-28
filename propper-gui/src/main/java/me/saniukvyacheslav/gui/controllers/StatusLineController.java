package me.saniukvyacheslav.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.core.repo.RepositoryEvents;
import me.saniukvyacheslav.core.repo.RepositoryTypes;
import me.saniukvyacheslav.gui.controllers.menu.TopMenuController;
import me.saniukvyacheslav.gui.controllers.props.PropertyEvents;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.models.StatusLineModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


/**
 * StatusLine controller updates embedded status line label text on different user actions.
 * This controller subscribed to {@link TopMenuController} and {@link  PrimaryNodeController} observable controllers.
 */
@NoArgsConstructor
public class StatusLineController implements Observer {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusLineController.class); // Logger;
    private StatusLineModel lineModel; // Status line model;
    @FXML private Node statusLine;
    @FXML private Label statusLineLabel; // Inner status line label;

    /**
     * Initialize this StatusLineController controller.
     * Initialize inner StatusLineModel [lineModel] field.
     */
    @FXML
    public void initialize() {
        LOGGER.debug("Try to initialize [StatusLineController] controller:");

        // Construct status line model:
        this.lineModel = new StatusLineModel(this.statusLine, this.statusLineLabel);
        LOGGER.debug(String.format("Status line model: [%s];" , this.lineModel));

        // End:
        LOGGER.debug("[StatusLineController] controller initialization: SUCCESS;");
    }

    /**
     * Update status line text on different user actions.
     * @param event - users action event.
     * @param arguments - action arguments.
     */
    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {
        switch (event.getCode()) {
            case 205: // PROPERTY_UPDATE_EVENT PropertyChangesController event:
                LOGGER.debug(String.format("[%d: %s] event. Update status line text:",
                        PropertyEvents.PROPERTY_UPDATE_EVENT.getCode(), PropertyEvents.PROPERTY_UPDATE_EVENT.name()));
                this.onPropertyChangeEvent((int) arguments[0]);
                break;
            case 550: // REPOSITORY_OPENED event:
                LOGGER.debug(String.format("[%d: %s] event. Update status line text:",
                        RepositoryEvents.REPOSITORY_OPENED.getCode(), RepositoryEvents.REPOSITORY_OPENED.name()));
                // Parse arguments:
                RepositoryTypes type = (RepositoryTypes) arguments[0];
                if (type == RepositoryTypes.FileRepository) this.onRepositoryOpenedEvent(String.format("File: %s;", ((File) arguments[1]).getAbsolutePath()));
                break;
            case 551: // REPOSITORY_CHANGES_SAVED event:
                LOGGER.debug(String.format("[%d: %s] event. Update status line text:",
                        RepositoryEvents.REPOSITORY_CHANGES_SAVED.getCode(), RepositoryEvents.REPOSITORY_CHANGES_SAVED.name()));
                this.onChangesSavedEvent();
                break;
            default:

        }
    }

    /**
     * Update status line text, when user update property key or value.
     * @param anUpdatesCount - whole updates count.
     */
    public void onPropertyChangeEvent(int anUpdatesCount) {
        // Set updates count in model, then set new text to label:
        this.lineModel.setUpdatesCount(anUpdatesCount);
        this.lineModel.updateText();
    }

    /**
     * Reset inserts, updates, deletes counters in model and update status line text.
     */
    public void onChangesSavedEvent() {
        this.lineModel.setUpdatesCount(0);
        this.lineModel.updateText();
    }


    /**
     * Update status line label text, when user open properties file.
     * Clear path, reset counters in model, set new path in it, and set new embedded label text.
     * @param aPathToPropertiesFile - path to properties file.
     */
    public void onRepositoryOpenedEvent(String aPathToPropertiesFile) {
        // Clear model, set path to properties file, and set new label text:
        this.lineModel.setRepositoryObj(aPathToPropertiesFile);
        this.lineModel.setUpdatesCount(0);
        this.lineModel.updateText();
    }






}
