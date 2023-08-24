package me.saniukvyacheslav.gui;

import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.annotation.pattern.Singleton;
import me.saniukvyacheslav.core.PropperGui;
import me.saniukvyacheslav.core.RootConfiguration;
import me.saniukvyacheslav.core.error.ErrorsController;
import me.saniukvyacheslav.core.repo.RepositoryEvents;
import me.saniukvyacheslav.gui.controllers.PrimaryNodeController;
import me.saniukvyacheslav.gui.controllers.PropertiesTableController;
import me.saniukvyacheslav.gui.controllers.menu.FileMenuController;
import me.saniukvyacheslav.gui.controllers.menu.TopMenuController;
import me.saniukvyacheslav.gui.controllers.props.PropertyChangesController;
import me.saniukvyacheslav.gui.controllers.props.PropertyEvents;
import me.saniukvyacheslav.gui.controllers.statusline.StatusLineController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


/**
 * Graphical user interface configuration class.
 */
@Singleton
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GuiConfiguration {

    public static GuiConfiguration INSTANCE; // Singleton instance;
    @Getter private boolean isInitialized; // "Init" flag;
    private static final Logger LOGGER = LoggerFactory.getLogger(GuiConfiguration.class); // Logger;
    private  PrimaryNodeController primaryNodeController; // PrimaryNodeController controller;
    private TopMenuController topMenuController; // TopMenuController controller;
    private FileMenuController fileMenuController; // FileMenuController controller;
    private PropertiesTableController propertiesTableController; // PropertiesTableController controller;
    private StatusLineController statusLineController; // StatusLineController controller;
    private final PropertyChangesController propertyChangesController = PropertyChangesController.getInstance(); // PropertyChangesController controller;

    /**
     * Init this configuration.
     * @param aPrimaryNodeController - controller.
     */
    public void init(PrimaryNodeController aPrimaryNodeController) {
        LOGGER.debug("Init singleton instance of [GuiConfiguration] class with [PrimaryNodeController] instance. Check parameter:");
        Objects.requireNonNull(aPrimaryNodeController, "PrimaryNodeController [aPrimaryNodeController] must be not null.");

        // Get controllers:
        LOGGER.debug("Create and map GUI controllers:");
        this.primaryNodeController = aPrimaryNodeController;
        LOGGER.debug(String.format("PrimaryNodeController controller: [%s];", this.primaryNodeController));
        this.topMenuController = this.primaryNodeController.getTopMenuController();
        LOGGER.debug(String.format("TopMenuController controller: [%s];", this.topMenuController));
        this.fileMenuController = this.topMenuController.getFileMenuController();
        LOGGER.debug(String.format("FileMenuController controller: [%s];", this.fileMenuController));
        this.propertiesTableController = this.primaryNodeController.getPropertiesTableController();
        LOGGER.debug(String.format("PropertiesTableController controller: [%s];", this.propertiesTableController));
        this.statusLineController = this.primaryNodeController.getStatusLineController();
        LOGGER.debug(String.format("StatusLineController controller: [%s];", this.statusLineController));

        // Set "init" flag:
        this.isInitialized = true;
        LOGGER.debug("Init singleton instance of [GuiConfiguration] configuration: SUCCESSFUL;");
    }

    /**
     * Subscribe these GUI components on ROOT application events.
     */
    public void subscribeOnRootEvents() {
        LOGGER.debug("Subscribe GUI components on ROOT application events:");
        // Subscribe on repository events:
        RootConfiguration.getInstance().getRepositoryController().subscribe(this.getFileMenuController(), RepositoryEvents.REPOSITORY_OPENED);
        RootConfiguration.getInstance().getRepositoryController().subscribe(this.getTopMenuController(), RepositoryEvents.REPOSITORY_OPENED);
        RootConfiguration.getInstance().getRepositoryController().subscribe(this.getPropertiesTableController(), RepositoryEvents.REPOSITORY_OPENED, RepositoryEvents.REPOSITORY_CHANGES_SAVED);
        RootConfiguration.getInstance().getRepositoryController().subscribe(this.getStatusLineController(), RepositoryEvents.REPOSITORY_OPENED);
        this.propertyChangesController.subscribe(this.statusLineController, PropertyEvents.PROPERTY_UPDATE_EVENT);
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
     * Get singleton TopMenuController controller instance.
     * @return - TopMenuController controller instance.
     */
    public TopMenuController getTopMenuController() {
        if (!this.isInitialized) throw new IllegalStateException("Configuration [GuiConfiguration] is not initialized. See GuiConfiguration#init method.");
        else return this.topMenuController;
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
     * Get PropertiesTableController instance.
     * @return - PropertiesTableController instance.
     * @throws IllegalStateException - if this configuration haven't been initialized.
     */
    public PropertiesTableController getPropertiesTableController() {
        if (!this.isInitialized) throw new IllegalStateException("Configuration [GuiConfiguration] is not initialized. See GuiConfiguration#init method.");
        else return this.propertiesTableController;
    }
}
