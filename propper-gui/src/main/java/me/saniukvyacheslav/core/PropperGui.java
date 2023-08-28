package me.saniukvyacheslav.core;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.saniukvyacheslav.Main;
import me.saniukvyacheslav.annotation.pattern.Singleton;
import me.saniukvyacheslav.core.error.ErrorsController;
import me.saniukvyacheslav.core.error.global.JavaFxError;
import me.saniukvyacheslav.core.error.global.PrimaryStageIsNullError;
import me.saniukvyacheslav.gui.GuiConfiguration;
import me.saniukvyacheslav.gui.controllers.PrimaryNodeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * PropperGui class is main program entry point.
 */
@Singleton
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropperGui {

    private static PropperGui INSTANCE; // Singleton instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(PropperGui.class); // Logger;
    @Getter @Setter private ErrorsController errorsController; // Shared errors controller;
    // Java FX nodes:
    @Getter private Stage primaryStage; // Primary stage;
    @Getter private PrimaryNodeController primaryNodeController; // Main GUI controller;
    @Getter private Scene mainScene; // Primary scene;

    /**
     * Get current singleton instance of this class.
     * @return - singleton instance.
     */
    public static PropperGui getInstance() {
        if (PropperGui.INSTANCE == null) {
            LOGGER.debug("Create singleton instance of [PropperGui] class:");
            PropperGui.INSTANCE = new PropperGui();
        }
        return PropperGui.INSTANCE;
    }

    /**
     * Start PropperGui application.
     * Method check stage parameter, map it. Then load XML configuration, init Root and GUI configurations,
     * subscribe these configurations for each other on events, and show GUI.
     * @param aPrimaryStage - JavaFx primaryStage.
     */
    public void start(Stage aPrimaryStage) {
        LOGGER.debug(String.format("Start [%s] application:", this.getApplicationTitle()));

        // Check parameters:
        try {
            Objects.requireNonNull(aPrimaryStage, "Stage [aPrimaryStage] must be not null.");
        }catch (NullPointerException e) {
            this.errorsController.logErrorAndExit(new PrimaryStageIsNullError(e));
        }
        this.primaryStage = aPrimaryStage;
        LOGGER.debug(String.format("Primary stage: [%s];", this.primaryStage));

        // Load XML configuration:
        try {
            this.loadXml();
        } catch (IOException e) {
            this.errorsController.logErrorAndExit(new JavaFxError(e));
        }

        // Initialize GUI Configuration:
        GuiConfiguration.getInstance().init(primaryNodeController);
        // Subscribe controllers on events:
        GuiConfiguration.getInstance().subscribeOnRootEvents();
        RootConfiguration.getInstance().subscribeOnGuiEvents();

        // Set onClose request:
        this.primaryStage.setOnCloseRequest((e) -> PropperGui.getInstance().normallyCloseApplication());

        // Show GUI:
        this.show();
    }

    /**
     * Load FX XML configuration.
     * Initialize this primaryNodeController and primaryNode in it.
     * @throws IOException - If IO exception occurs.
     */
    private void loadXml() throws IOException {
        LOGGER.debug("Try to load XML configuration:");
        // Load XML configuration:
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/main_scene.fxml"));

        // Get main scene controller:
        Node primaryNode = fxmlLoader.load();
        this.primaryNodeController = fxmlLoader.getController();
        this.primaryNodeController.setPrimaryNode(primaryNode);
        LOGGER.debug("Load XML configuration: SUCCESSFUL;");
    }

    /**
     * Show application GUI.
     * Initialize this mainScene field, set application title, apply css style and show this primaryStage.
     */
    private void show() {

        // Create primary scene and set it to primary stage:
        LOGGER.debug("Create Scene [mainScene:]");
        this.mainScene = new Scene(((Parent) this.primaryNodeController.getPrimaryNode()), 800, 600);
        this.primaryStage.setScene(this.mainScene);
        LOGGER.debug(String.format("Main scene: [%s];", this.mainScene));

        // Set application title:
        this.primaryStage.setTitle(this.getApplicationTitle());
        LOGGER.debug(String.format("Application title: [%s];", this.primaryStage.getTitle()));

        // Apply CSS styles:
        this.applyCssStyles();

        // Show GUI:
        LOGGER.debug("Show GUI:");
        this.primaryStage.show();
    }

    /**
     * Apply css styles to FX components.
     */
    private void applyCssStyles() {
        LOGGER.debug("Load and apply CSS styles:");
        String propertiesTableCss = Objects.requireNonNull(Main.class.getResource("/css/properties_table_styles.css")).toExternalForm();
        String statusLineCss = Objects.requireNonNull(Main.class.getResource("/css/status_line_styles.css")).toExternalForm();

        // Apply CSS:
        this.mainScene.getStylesheets().addAll(
                propertiesTableCss,
                statusLineCss
        );
    }

    /**
     * Get application title string.
     * @return - application title string.
     */
    public String getApplicationTitle() {
        return String.format("%s - %s",
                ApplicationProperties.APPLICATION_NAME, ApplicationProperties.APPLICATION_VERSION);
    }

    /**
     * Close application with exit code.
     * @param aExitCode - exit code.
     */
    public void close(int aExitCode) {
        Platform.exit();
        System.exit(aExitCode);
    }

    /**
     * Normally close application.
     * Close [PropertiesRepository] if it was opened.
     */
    public void normallyCloseApplication() {
        LOGGER.debug("Try to normally close application:");

        // Check if PropertiesRepository is initialized:
        if(RootConfiguration.getInstance().isPropertiesRepositoryInitialized()) { // Try to close PropertiesRepository:
            RootConfiguration.getInstance().getRepositoryController().close();
        }

        // Close FX and application:
        this.close(0);
    }

}
