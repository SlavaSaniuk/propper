package me.saniukvyacheslav.gui;

import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.Logger;
import me.saniukvyacheslav.annotation.pattern.Singleton;
import me.saniukvyacheslav.core.PropperGui;
import me.saniukvyacheslav.core.RootConfiguration;
import me.saniukvyacheslav.core.error.ErrorsController;
import me.saniukvyacheslav.core.logging.PropperLoggingConfiguration;
import me.saniukvyacheslav.core.repo.RepositoryEvents;
import me.saniukvyacheslav.gui.controllers.PrimaryNodeController;
import me.saniukvyacheslav.gui.controllers.PropertiesTableController;
import me.saniukvyacheslav.gui.controllers.menu.FileMenuController;
import me.saniukvyacheslav.gui.controllers.menu.PropertiesMenuController;
import me.saniukvyacheslav.gui.controllers.menu.TopMenuController;
import me.saniukvyacheslav.gui.controllers.props.PropertyChangesController;
import me.saniukvyacheslav.gui.controllers.props.PropertyEvents;
import me.saniukvyacheslav.gui.controllers.StatusLineController;
import me.saniukvyacheslav.gui.events.menu.PropertiesMenuEvents;


import java.util.Objects;


/**
 * Graphical user interface configuration class.
 */
@Singleton
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GuiConfiguration {

    public static GuiConfiguration INSTANCE; // Singleton instance;
    @Getter private boolean isInitialized; // "Init" flag;
    private static final Logger LOGGER = PropperLoggingConfiguration.getLogger(GuiConfiguration.class); // Logger;

    private FileMenuController fileMenuController; // FileMenuController controller;
    private PropertiesMenuController propertiesMenuController; // PropertiesMenuController controller;
    private PropertiesTableController propertiesTableController; // PropertiesTableController controller;
    private StatusLineController statusLineController; // StatusLineController controller;
    private PropertyChangesController propertyChangesController; // PropertyChangesController controller;

    /**
     * Init this configuration.
     * @param aPrimaryNodeController - controller.
     */
    public void init(PrimaryNodeController aPrimaryNodeController) {
        LOGGER.debug("Init singleton instance of [GuiConfiguration] class with [PrimaryNodeController] instance. Check parameter:");
        Objects.requireNonNull(aPrimaryNodeController, "PrimaryNodeController [aPrimaryNodeController] must be not null.");

        // Get controllers:
        LOGGER.debug("Create and map GUI controllers:");
        LOGGER.debug(String.format("PrimaryNodeController controller: [%s];", aPrimaryNodeController));
        TopMenuController topMenuController = aPrimaryNodeController.getTopMenuController();
        LOGGER.debug(String.format("TopMenuController controller: [%s];", topMenuController));
        this.fileMenuController = topMenuController.getFileMenuController();
        LOGGER.debug(String.format("FileMenuController controller: [%s];", this.fileMenuController));
        this.propertiesMenuController = topMenuController.getPropertiesMenuController();
        LOGGER.debug(String.format("PropertiesMenuController controller: [%s];", this.propertiesMenuController));
        this.propertiesTableController = aPrimaryNodeController.getPropertiesTableController();
        LOGGER.debug(String.format("PropertiesTableController controller: [%s];", this.propertiesTableController));
        this.statusLineController = aPrimaryNodeController.getStatusLineController();
        LOGGER.debug(String.format("StatusLineController controller: [%s];", this.statusLineController));
        this.propertyChangesController = PropertyChangesController.getInstance();
        LOGGER.debug(String.format("PropertyChangesController controller: [%s];", this.propertyChangesController));
        // Set "init" flag:
        this.isInitialized = true;
        LOGGER.debug("Init singleton instance of [GuiConfiguration] configuration: SUCCESSFUL;");
    }

    /**
     * Subscribe GUI components on application events.
     */
    public void subscribeOnEvents() {
        LOGGER.trace("Subscribe GUI components on events:");
        this.propertiesMenuController.subscribe(this.getPropertiesTableController(), PropertiesMenuEvents.PROPERTY_INSERT_EVENT);

        this.propertyChangesController.subscribe(this.statusLineController, PropertyEvents.PROPERTY_UPDATE_EVENT);

        this.subscribeOnRootEvents();
    }

    /**
     * Subscribe these GUI components on ROOT application events.
     */
    private void subscribeOnRootEvents() {
        LOGGER.debug("Subscribe GUI components on ROOT application events:");
        // Subscribe on repository events:
        RootConfiguration.getInstance().getRepositoryController().subscribe(this.getFileMenuController(),
                RepositoryEvents.REPOSITORY_OPENED,
                RepositoryEvents.REPOSITORY_CLOSED);
        RootConfiguration.getInstance().getRepositoryController().subscribe(this.getPropertiesMenuController(),
                RepositoryEvents.REPOSITORY_OPENED,
                RepositoryEvents.REPOSITORY_CLOSED);
        RootConfiguration.getInstance().getRepositoryController().subscribe(this.getPropertiesTableController(),
                RepositoryEvents.REPOSITORY_OPENED,
                RepositoryEvents.REPOSITORY_CHANGES_SAVED,
                RepositoryEvents.REPOSITORY_CLOSED);
        RootConfiguration.getInstance().getRepositoryController().subscribe(this.getStatusLineController(),
                RepositoryEvents.REPOSITORY_OPENED,
                RepositoryEvents.REPOSITORY_CHANGES_SAVED,
                RepositoryEvents.REPOSITORY_CLOSED);

    }

    /**
     * Get application primary stage.
     * @return - FX Stage.
     */
    public Stage getPrimaryStage() {
        if (!this.isInitialized) throw new IllegalStateException("Configuration [GuiConfiguration] is not initialized. See GuiConfiguration#init method.");
        return PropperGui.getInstance().getPrimaryStage();
    }

    /**
     * Get shared errors controller instance.
     * @return - error controller instance.
     */
    public ErrorsController getErrorsController() {
        return PropperGui.getInstance().getErrorsController();
    }

    /**
     * Get singleton statusLineController controller instance.
     * @return - StatusLineController controller instance.
     */
    public StatusLineController getStatusLineController() {
        if (!this.isInitialized) throw new IllegalStateException("Configuration [GuiConfiguration] is not initialized. See GuiConfiguration#init method.");
        else return this.statusLineController;
    }

    /**
     * Get current singleton instance of this class.
     * @return - singleton instance.
     */
    public static GuiConfiguration getInstance() {
        if (GuiConfiguration.INSTANCE == null) GuiConfiguration.INSTANCE = new GuiConfiguration();
        return GuiConfiguration.INSTANCE;
    }

    /**
     * Get singleton FileMenuController controller instance.
     * @return - FileMenuController controller instance.
     */
    public FileMenuController getFileMenuController() {
        if (!this.isInitialized) throw new IllegalStateException("Configuration [GuiConfiguration] is not initialized. See GuiConfiguration#init method.");
        else return this.fileMenuController;
    }

    /**
     * Get singleton PropertiesMenuController controller instance.
     * @return - PropertiesMenuController controller instance.
     */
    public PropertiesMenuController getPropertiesMenuController() {
        if (!this.isInitialized) throw new IllegalStateException("Configuration [GuiConfiguration] is not initialized. See GuiConfiguration#init method.");
        else return this.propertiesMenuController;
    }

    /**
     * Get PropertiesTableController instance.
     * @return - PropertiesTableController instance.
     * @throws IllegalStateException - if this configuration haven't been initialized.
     */
    public PropertiesTableController getPropertiesTableController() {
        if (!this.isInitialized) throw new IllegalStateException("Configuration [GuiConfiguration] is not initialized. See GuiConfiguration#init method.");
        else return this.propertiesTableController;
    }

    /**
     * Get PropertyChangesController instance.
     * @return - PropertiesTableController instance.
     * @throws IllegalStateException - if this configuration haven't been initialized.
     */
    public PropertyChangesController getPropertyChangesController() {
        if (!this.isInitialized) throw new IllegalStateException("Configuration [GuiConfiguration] is not initialized. See GuiConfiguration#init method.");
        else return this.propertyChangesController;
    }
}
