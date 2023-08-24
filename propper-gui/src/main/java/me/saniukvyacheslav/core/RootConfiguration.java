package me.saniukvyacheslav.core;

import me.saniukvyacheslav.annotation.pattern.Singleton;
import me.saniukvyacheslav.core.error.ErrorsController;
import me.saniukvyacheslav.core.controller.RepositoryController;
import me.saniukvyacheslav.core.property.ExtendedBaseProperty;
import me.saniukvyacheslav.core.repo.PropertiesRepository;
import me.saniukvyacheslav.core.repo.RepositoryErrors;
import me.saniukvyacheslav.core.repo.exception.RepositoryNotInitializedException;
import me.saniukvyacheslav.core.repo.file.FileRepository;
import me.saniukvyacheslav.core.repo.file.FileRepositoryContentDecorator;
import me.saniukvyacheslav.core.store.PropertiesStore;
import me.saniukvyacheslav.gui.GuiConfiguration;
import me.saniukvyacheslav.gui.events.menu.FileMenuEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;

/**
 * Configuration class has all beans definitions (Services, repositories, ...).
 */
@Singleton
public class RootConfiguration {

    private static RootConfiguration INSTANCE; // Singleton instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(RootConfiguration.class); // Logger;
    // Class variables:
    private PropertiesStore<ExtendedBaseProperty> currentPropertiesStoreImpl; // Current PropertiesStore implementation;
    private PropertiesRepository currentPropertiesRepositoryImpl; // Current PropertiesRepository implementation;
    private boolean propertiesStoreInitState; // PropertiesStore "init" state;
    private boolean propertiesRepositoryInitState; // PropertiesRepository "init" state;

    /**
     * Private default constructor.
     */
    private RootConfiguration() {
        LOGGER.debug("Construct [RootConfiguration] singleton instance.");
    }

    /**
     * Get current singleton instance of this class.
     * @return - singleton instance.
     */
    public static RootConfiguration getInstance() {
        if (RootConfiguration.INSTANCE == null) RootConfiguration.INSTANCE = new RootConfiguration();
        return RootConfiguration.INSTANCE;
    }

    /**
     * Subscribe these ROOT components on GUI application events.
     */
    public void subscribeOnGuiEvents() {
        LOGGER.debug("Subscribe these ROOT components on GUI application events:");

        // Subscribe this RepositoryController controller on FileMenu menu events:
        GuiConfiguration.getInstance().getFileMenuController().subscribe(this.getRepositoryController(), FileMenuEvents.OPEN_FILE_EVENT, FileMenuEvents.SAVE_FILE_EVENT, FileMenuEvents.CLOSE_FILE_EVENT);

        this.getRepositoryController().subscribe(this.getErrorsController(), RepositoryErrors.REPOSITORY_TYPE_NOT_SUPPORTED, RepositoryErrors.REPOSITORY_OPENING_ERROR);

    }

    /**
     * Get shared errors controller instance.
     * @return - error controller instance.
     */
    public ErrorsController getErrorsController() {
        return PropperGui.getInstance().getErrorsController();
    }

    /**
     * Get [RepositoryController] controller.
     * @return - RepositoryController controller.
     */
    public RepositoryController getRepositoryController() {
        return RepositoryController.getInstance();
    }


    /**
     * Get current PropertiesStore implementation.
     * @return - PropertiesStore implementation.
     */
    public PropertiesStore<ExtendedBaseProperty> getPropertiesStore() {
        if (!this.propertiesStoreInitState)
            throw new IllegalStateException("Properties store service must be initialized before [see: RootConfiguration#initPropertiesStore method].");
        else return this.currentPropertiesStoreImpl;
    }

    /**
     * Init current PropertiesStore implementation.
     * @param aPropertiesStore - properties store implementation.
     */
    public void initPropertiesStore(PropertiesStore<ExtendedBaseProperty> aPropertiesStore) {
        // Check parameter:
        Objects.requireNonNull(aPropertiesStore, "A [PropertiesStore] implementation must be not [null].");
        LOGGER.debug(String.format("Init current [PropertiesStore] implementation of [%s] class;", aPropertiesStore.getClass().getName()));

        // Map parameter:
        this.currentPropertiesStoreImpl = aPropertiesStore;

        // Set "init" state:
        this.propertiesStoreInitState = true;
    }

    /**
     * Get current PropertiesRepository repository.
     * @return - PropertiesRepository repository implementation.
     */
    public PropertiesRepository getPropertiesRepository() {
        if (!this.propertiesRepositoryInitState)
            throw new IllegalStateException("Properties store service must be initialized before [see: RootConfiguration#init[*]PropertiesRepository method].");
        else return this.currentPropertiesRepositoryImpl;
    }

    /**
     * Init current {@link PropertiesRepository} repository with {@link FileRepositoryContentDecorator} implementation.
     * @param aFile - properties file.
     * @throws RepositoryNotInitializedException - if initialization error occurs.
     */
    public void initFilePropertiesRepository(File aFile) throws RepositoryNotInitializedException {
        LOGGER.debug("Try to init [FileRepository] instance:");
        FileRepository.getInstance().init(aFile);

        LOGGER.debug("Construct new [FileRepositoryContentDecorator] instance and map it:");
        this.currentPropertiesRepositoryImpl = new FileRepositoryContentDecorator(FileRepository.getInstance());

        // Set "init" state:
        this.propertiesRepositoryInitState = true;
    }
}
