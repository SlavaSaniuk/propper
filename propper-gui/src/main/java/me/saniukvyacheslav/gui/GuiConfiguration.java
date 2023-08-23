package me.saniukvyacheslav.gui;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.annotation.pattern.Singleton;
import me.saniukvyacheslav.gui.controllers.PrimaryNodeController;
import me.saniukvyacheslav.gui.controllers.PropertiesTableController;
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
    private PropertiesTableController propertiesTableController; // PropertiesTableController controller;

    /**
     * Init this configuration.
     * @param aPrimaryNodeController - controller.
     */
    public void init(PrimaryNodeController aPrimaryNodeController) {
        LOGGER.debug("Init singleton instance of [GuiConfiguration] class with [PrimaryNodeController] instance. Check parameter:");
        Objects.requireNonNull(aPrimaryNodeController, "PrimaryNodeController [aPrimaryNodeController] must be not null.");
        LOGGER.debug(String.format("PrimaryNodeController controller: [%s];", aPrimaryNodeController));

        // Mapping:
        this.primaryNodeController = aPrimaryNodeController;
        this.propertiesTableController = aPrimaryNodeController.getPropertiesTableController();

        // Set "init" flag:
        this.isInitialized = true;
        LOGGER.debug("Init singleton instance of [GuiConfiguration] configuration: SUCCESSFUL;");
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
     * Get PrimaryNodeController controller instance.
     * @return - PrimaryNodeController controller instance.
     * @throws IllegalStateException - if this configuration haven't been initialized.
     */
    public PrimaryNodeController getPrimaryNodeController() {
        if (!this.isInitialized) throw new IllegalStateException("Configuration [GuiConfiguration] is not initialized. See GuiConfiguration#init method.");
        else return this.primaryNodeController;
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
