package me.saniukvyacheslav.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.Getter;
import me.saniukvyacheslav.core.controller.ErrorsController;
import me.saniukvyacheslav.core.controller.RepositoryController;
import me.saniukvyacheslav.core.repo.RepositoryErrors;
import me.saniukvyacheslav.core.repo.RepositoryEvents;
import me.saniukvyacheslav.gui.controllers.menu.FileMenuController;
import me.saniukvyacheslav.gui.controllers.menu.MenuErrors;
import me.saniukvyacheslav.gui.controllers.menu.TopMenuController;
import me.saniukvyacheslav.gui.controllers.menu.events.FileMenuEvents;
import me.saniukvyacheslav.gui.controllers.props.PropertyChangesController;
import me.saniukvyacheslav.gui.controllers.props.PropertyEvents;
import me.saniukvyacheslav.gui.controllers.statusline.StatusLineController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;


public class PrimaryNodeController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrimaryNodeController.class); // Logger;
    // Inner controllers:
    @FXML private TopMenuController topMenuController;
    @FXML private StatusLineController statusLineController;
    @FXML @Getter private PropertiesTableController propertiesTableController; // PropertiesTableController controller;
    private final RepositoryController repositoryController = RepositoryController.getInstance(); // Repository controller;
    private final ErrorsController errorsController = ErrorsController.getInstance(); // Errors controller;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LOGGER.debug("Init222222222222222222222222");

        // Subscribe observers:
        this.subscribeObservers();

        // On PropertyChangesController:
        PropertyChangesController.getInstance().subscribe(this.statusLineController, PropertyEvents.PROPERTY_UPDATE_EVENT);
    }

    /**
     * Subscribe observer to controllers.
     * Subscribe {@link PropertiesTableController} on FileMenu {@link FileMenuEvents} (NEW, OPEN, SAVE, CLOSE) events.
     * Subscribe {@link StatusLineController} on FileMenu {@link FileMenuEvents} (NEW, OPEN, CLOSE) events.
     */
    private void subscribeObservers() {
        // +++ Top menu:
        // ++++++ FileMenu:
        this.topMenuController.subscribeOnFileMenuEvents(this.repositoryController, FileMenuEvents.OPEN_FILE_EVENT, FileMenuEvents.SAVE_FILE_EVENT);
        this.topMenuController.subscribeOnFileMenuEvents(this.errorsController, MenuErrors.FILE_CREATION_ERROR, MenuErrors.FILE_REWRITING_ERROR);

        // +++ RepositoryController:
        this.repositoryController.subscribe(this.errorsController, RepositoryErrors.REPOSITORY_TYPE_NOT_SUPPORTED, RepositoryErrors.REPOSITORY_OPENING_ERROR);
        this.repositoryController.subscribe(this.propertiesTableController, RepositoryEvents.REPOSITORY_OPENED, RepositoryEvents.REPOSITORY_CHANGES_SAVED);
        this.repositoryController.subscribe(this.statusLineController, RepositoryEvents.REPOSITORY_OPENED);
        this.repositoryController.subscribe(FileMenuController.getInstance(), RepositoryEvents.REPOSITORY_OPENED);
    }



}
