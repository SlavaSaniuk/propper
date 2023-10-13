package me.saniukvyacheslav.core.controller;


import javafx.scene.control.ButtonType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.Logger;
import me.saniukvyacheslav.core.RootConfiguration;
import me.saniukvyacheslav.core.error.ApplicationError;
import me.saniukvyacheslav.core.logging.PropperLoggingConfiguration;
import me.saniukvyacheslav.core.property.PropertiesChanges;
import me.saniukvyacheslav.core.repo.PropertiesRepository;
import me.saniukvyacheslav.core.repo.RepositoryErrors;
import me.saniukvyacheslav.core.repo.RepositoryEvents;
import me.saniukvyacheslav.core.repo.RepositoryTypes;
import me.saniukvyacheslav.core.repo.exception.RepositoryNotInitializedException;
import me.saniukvyacheslav.core.repo.file.FileRepository;
import me.saniukvyacheslav.core.repo.file.FileRepositoryContentDecorator;
import me.saniukvyacheslav.gui.GuiConfiguration;
import me.saniukvyacheslav.gui.dialogs.ApplicationDialogs;
import me.saniukvyacheslav.gui.events.Observable;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.events.menu.TopMenuEvents;


import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * RepositoryController controller used to open, close repository and save properties in repository.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RepositoryController implements Observer, Observable {

    // Class variables:
    private static RepositoryController INSTANCE; // Singleton instance;
    private static final Logger LOGGER = PropperLoggingConfiguration.getLogger(RepositoryController.class); // Logger;
    public boolean isOpened = false; // Repository opening state;
    private RepositoryTypes currentRepositoryType; // Current opening repository type;
    private final Map<Observer, PropperApplicationEvent[]> subscribers = new HashMap<>(); // Map of subscribers;

    /**
     * Get current singleton instance of this class.
     * @return - current singleton instance.
     */
    public static RepositoryController getInstance() {
        if(RepositoryController.INSTANCE == null) RepositoryController.INSTANCE = new RepositoryController();
        return RepositoryController.INSTANCE;
    }

    /**
     * Try to open repository.
     * @param arguments - 1-st argument - {@link RepositoryTypes} code, 2-nd is link on repository (File, URL, ...).
     */
    public void open(Object... arguments) {
        // Parse arguments:
        int repositoryType = ((RepositoryTypes) arguments[0]).getType();
        LOGGER.debug(String.format("Repository type for PropertiesRepository: [%d];", repositoryType));

        // Initialize repository based on type:
        switch (repositoryType) {
            case 1: { // File repository:
                LOGGER.debug(String.format("Repository implementation for PropertiesRepository: [%s];", FileRepository.class.getName()));
                this.openFileRepository(arguments [1]);
                break;
            }
            case 2: {
                LOGGER.error("Repository type: [2] - is not supported.");
                this.notify(RepositoryErrors.REPOSITORY_TYPE_NOT_SUPPORTED, new ApplicationError(RepositoryErrors.REPOSITORY_TYPE_NOT_SUPPORTED.getCode(), "Repository type not supported."));
                break;
            }
            default: {
                LOGGER.error(String.format("Repository type: [%d] - is not supported;", repositoryType));
                this.notify(RepositoryErrors.REPOSITORY_TYPE_NOT_SUPPORTED, new ApplicationError(RepositoryErrors.REPOSITORY_TYPE_NOT_SUPPORTED.getCode(), "Repository type not supported."));
            }
        }
    }

    /**
     * Open FileRepository repository.
     * Try to init FileRepository instance, and notify observers about {@link RepositoryEvents#REPOSITORY_OPENED} event.
     * After this method, developer can use {@link RootConfiguration#getPropertiesRepository()} method.
     * @param aPropertiesFile - properties file.
     */
    private void openFileRepository(Object aPropertiesFile) throws NullPointerException, ClassCastException {
        LOGGER.debug("Try to initialize FileRepository repository:");

        // Check parameter:
        Objects.requireNonNull(aPropertiesFile, "File [File] object must be not null.");
        File file = ((File) aPropertiesFile); // Cast to file:

        // Try to open file repository:
        try {
            // Open and init file repository:
            RootConfiguration.getInstance().initFilePropertiesRepository(file);
        }catch (RepositoryNotInitializedException e) {
            LOGGER.error("Cannot open FileRepository for properties file;");
            ApplicationError openingError = new ApplicationError(RepositoryErrors.REPOSITORY_OPENING_ERROR.getCode(), "Properties file cannot be opened.");

            openingError.setOriginalExceptionMessage(e.getExceptionMessage());

            this.notify(RepositoryErrors.REPOSITORY_OPENING_ERROR, openingError);
            return;
        }
        LOGGER.debug(String.format("Initialization of file repository with properties file [%s] was successful;", file.getPath()));

        // Set state:
        this.isOpened = true;
        this.currentRepositoryType = RepositoryTypes.FileRepository;

        // Notify observers:
        this.notify(RepositoryEvents.REPOSITORY_OPENED, RepositoryTypes.FileRepository, file);
    }

    /**
     * Try to save properties in repository.
     * Get all properties changes from properties table and save, delete and update it.
     */
    public void save() {
        LOGGER.debug("Try to save all changes in repository: ");

        // Handle properties changes:
        PropertiesChanges changes = GuiConfiguration.getInstance().getPropertiesTableController().getPropertiesChanges();
        LOGGER.debug(String.format("Save property changes: [%s];", changes));



        try {
            // Save in repository:
            // Inserts:
            // Updates:
            // Update properties keys before:
            RootConfiguration.getInstance().getPropertiesRepository().updateKeys(changes.getPropertiesKeysUpdates());
            // Update properties values:
            RootConfiguration.getInstance().getPropertiesRepository().updateValues(changes.getPropertiesValueUpdates());
            // Deletions:
            // Flush changes:
            RootConfiguration.getInstance().getPropertiesRepository().flush();

            // Notify about successful saving:
            LOGGER.debug("Call [REPOSITORY_CHANGES_SAVED: 551] repository event:");
            this.notify(RepositoryEvents.REPOSITORY_CHANGES_SAVED);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * On "FileMenuEvents#CLOSE_FILE_EVENT" action.
     * Try to close [PropertiesRepository] repository.
     */
    public void onCloseEvent() {
        LOGGER.debug(String.format("[%d: %s] event. Close repository:", TopMenuEvents.CLOSE_FILE_EVENT.getCode(), TopMenuEvents.CLOSE_FILE_EVENT.name()));
        this.close();
    }

    /**
     * Check for unsaved changes and close repository.
     */
    public void close() {
        LOGGER.debug("Try to close repository instance:");

        // Check for unsaved changes:
        LOGGER.debug("Check for unsaved changes:");
        boolean isUnsavedChanges = GuiConfiguration.getInstance().getPropertiesTableController().isTableHasUnsavedChanges();
        LOGGER.debug(String.format("Properties table has unsaved changes: [%b];", isUnsavedChanges));

        // Ask to save changes:
        if (isUnsavedChanges) {
            // If current repository is FileRepository, show save file dialog:
            if (this.currentRepositoryType == RepositoryTypes.FileRepository) {
                ButtonType answer =  this.showSaveFileDialog((File) RootConfiguration.getInstance().getPropertiesRepository().getRepositoryObject());
                if (answer == ApplicationDialogs.BTN_YES) this.save(); // Save changes;
            }
        }

        LOGGER.debug("Close repository: ");

        // Close repository:
        this.closeRepository();
    }

    /**
     * Show "Save file" dialog.
     * @param aFile - file to save.
     * @return - user choose.
     */
    private ButtonType showSaveFileDialog(File aFile) {
        return ApplicationDialogs.saveFileDialog(aFile.getAbsolutePath()).showAndWait().orElse(ApplicationDialogs.BTN_CANCEL);
    }

    /**
     * Close [PropertiesRepository] repository and notify observers about {@link RepositoryEvents#REPOSITORY_CLOSED} event.
     */
    private void closeRepository() {

        LOGGER.debug("Close [PropertiesRepository] repository:");
        // Check if properties repository was initialized:
        if (!RootConfiguration.getInstance().isPropertiesRepositoryInitialized()) {
            LOGGER.warn("[PropertiesRepository] repository is not initialized.");
            return;
        }

        // Close repository:
        PropertiesRepository propertiesRepository = RootConfiguration.getInstance().getPropertiesRepository();
        if (propertiesRepository instanceof FileRepositoryContentDecorator)
            ((FileRepositoryContentDecorator) propertiesRepository).close();
        LOGGER.debug("Close [PropertiesRepository] repository: SUCCESS;");

        this.isOpened = false;
        // Notify observers about REPOSITORY_CLOSED (552):
        this.notify(RepositoryEvents.REPOSITORY_CLOSED);
    }

    /**
     * Do something on event.
     * @param event - {@link Observable} instance event.
     * @param arguments - event arguments.
     */
    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {
        // Choose branch:
        switch (event.getCode()) {
            case 102: { // OpenFile FileMenu event:
                this.open(arguments);
                break;
            }
            case 103: { // SAVE_FILE_MENU FileMenu event:
                this.save();
                break;
            }
            case 105: { // CLOSE_FILE_MENU FileMenu event:
                this.onCloseEvent();
                break;
            }
            default: {
                LOGGER.warn(String.format("Not supported event [eventCode: %d];", event.getCode()));
            }
        }


    }

    /**
     * Subscribe observer to this observable instance.
     * @param anObserver - observer.
     * @param anApplicationEvents - observer supported events.
     */
    @Override
    public void subscribe(Observer anObserver, PropperApplicationEvent... anApplicationEvents) {
        this.subscribers.put(anObserver, anApplicationEvents);
    }

    /**
     * Unsubscribe observer to this observable instance.
     * @param anObserver - observer.
     */
    @Override
    public void unsubscribe(Observer anObserver) {
        this.subscribers.remove(anObserver);
    }

    /**
     * Notify observers of any event.
     * @param anApplicationEvent - any event.
     * @param anArguments - event arguments.
     */
    @Override
    public void notify(PropperApplicationEvent anApplicationEvent, Object... anArguments) {
        LOGGER.debug(String.format("Notify observers about [%d: %s] event:",  anApplicationEvent.getCode(), ((RepositoryEvents) anApplicationEvent).name()));

        // Iterate through subscribers:
        this.subscribers.forEach((subscriber, actionEvents) -> {

            // Iterate through supported event array of current subscriber:
            for (PropperApplicationEvent event: actionEvents) {
                if (event == anApplicationEvent) {
                    LOGGER.debug(String.format("Notify [%s] observer about [%d: %s] event:",
                            subscriber.getClass().getName(), event.getCode(), ((RepositoryEvents) event).name()));
                    subscriber.update(anApplicationEvent, anArguments);
                }
            }
        });
    }
}
