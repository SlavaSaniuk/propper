package me.saniukvyacheslav.core;

import me.saniukvyacheslav.annotation.pattern.Singleton;
import me.saniukvyacheslav.core.error.ErrorsController;
import me.saniukvyacheslav.core.controller.RepositoryController;
import me.saniukvyacheslav.core.property.ExtendedBaseProperty;
import me.saniukvyacheslav.core.repo.PropertiesRepository;
import me.saniukvyacheslav.core.repo.RepositoryErrors;
import me.saniukvyacheslav.core.repo.RepositoryEvents;
import me.saniukvyacheslav.core.repo.exception.RepositoryNotInitializedException;
import me.saniukvyacheslav.core.repo.file.FileRepository;
import me.saniukvyacheslav.core.repo.file.FileRepositoryContentDecorator;
import me.saniukvyacheslav.core.store.PropertiesStore;
import me.saniukvyacheslav.gui.GuiConfiguration;
import me.saniukvyacheslav.gui.events.Observable;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.events.menu.FileMenuEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;

/**
 * Configuration class has all beans definitions (Services, repositories, ...).
 */
@Singleton
public class RootConfiguration implements Observer {

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
        LOGGER.debug("Try to init [PropertiesRepository] repository:");
        FileRepository.getInstance().init(aFile);

        LOGGER.debug("Construct new [FileRepositoryContentDecorator] instance and map it:");
        this.currentPropertiesRepositoryImpl = new FileRepositoryContentDecorator(FileRepository.getInstance());

        // Set "init" state:
        this.propertiesRepositoryInitState = true;

        // Subscribe this configuration on repository events:
        LOGGER.debug("Subscribe this [RootConfiguration] configuration on [REPOSITORY_CLOSED] event:");
        this.getRepositoryController().subscribe(this, RepositoryEvents.REPOSITORY_CLOSED);

        LOGGER.debug("Try to init [PropertiesRepository] repository: SUCCESS;");
    }

    /**
     * Check for initialization of {@link PropertiesRepository} repository.
     * @return - true, if properties repository is initialized.
     */
    public boolean isPropertiesRepositoryInitialized() {
        return this.propertiesRepositoryInitState;
    }

    /**
     * Do something on event.
     * @param event - {@link Observable} instance event.
     * @param arguments - event arguments.
     */
    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {
        LOGGER.debug(String.format("Event [%d] for this [RootConfiguration] configuration:", event.getCode()));

        // Select event:
        if (event.getCode() == RepositoryEvents.REPOSITORY_CLOSED.getCode()) {
            LOGGER.debug(String.format("[%d: %s] event. Clear [PropertiesRepository] initialization:",
                    RepositoryEvents.REPOSITORY_CLOSED.getCode(), RepositoryEvents.REPOSITORY_CLOSED.name()));
            this.onClosePropertiesRepositoryEvent();
        }
    }

    /**
     * Reset these [PropertiesRepository], [PropertiesStore] 'init' states, and nullable these implementations.
     */
    private void onClosePropertiesRepositoryEvent() {
        LOGGER.debug("Reset these [PropertiesRepository] and [PropertiesStore] 'init' states:");

        this.propertiesRepositoryInitState = false;
        if (this.currentPropertiesRepositoryImpl != null) this.currentPropertiesRepositoryImpl = null;

        this.propertiesStoreInitState = false;
        if (this.currentPropertiesStoreImpl != null) this.currentPropertiesStoreImpl = null;

        LOGGER.debug("Reset these [PropertiesRepository] and [PropertiesStore] 'init' states: SUCCESS;");
    }
}
