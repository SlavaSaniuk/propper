package me.saniukvyacheslav.core.controller;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.core.error.ApplicationError;
import me.saniukvyacheslav.core.property.PropertiesChanges;
import me.saniukvyacheslav.core.repo.PropertiesRepository;
import me.saniukvyacheslav.core.repo.RepositoryErrors;
import me.saniukvyacheslav.core.repo.RepositoryEvents;
import me.saniukvyacheslav.core.repo.RepositoryTypes;
import me.saniukvyacheslav.core.repo.exception.RepositoryNotInitializedException;
import me.saniukvyacheslav.core.repo.file.FileRepository;
import me.saniukvyacheslav.core.repo.file.FileRepositoryContentDecorator;
import me.saniukvyacheslav.gui.controllers.table.PropertiesTableController;
import me.saniukvyacheslav.gui.events.Observable;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryController.class); // Logger;
    @Getter private PropertiesRepository propertiesRepository; // Properties repository;
    public boolean isOpened = false; // Repository opening state;
    private RepositoryTypes currentRepositoryType; // Current opening repository type;
    private final Map<Observer, PropperApplicationEvent[]> subscribers = new HashMap<>(); // Map of subscribers;
    /**
     * Get current singleton instance of this class.
     * @return - current singleton instance.
     */
    public static RepositoryController getInstance() {
        LOGGER.debug(String.format("Get singleton instance of [%s] class;", RepositoryController.class.getName()));
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
            case 1: {
                LOGGER.debug(String.format("Repository implementation for PropertiesRepository: [%s];", FileRepository.class.getName()));
                // Initialize file repository:
                File propertiesFile = ((File) arguments[1]);
                this.initializeFileRepository(propertiesFile);
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
     * Try to initialize properties file repository.
     * @param aPropertiesFile - properties file.
     */
    private void initializeFileRepository(File aPropertiesFile) {
        LOGGER.debug("Initialize file repository:");

        // Try to open file repository:
        try {
            // Open and init file repository:
            this.propertiesRepository = FileRepositoryContentDecorator.getInstance().initRepository(aPropertiesFile);
        }catch (IOException e) {
            LOGGER.error("Cannot open FileRepository for properties file;");
            ApplicationError openingError = new ApplicationError(RepositoryErrors.REPOSITORY_OPENING_ERROR.getCode(), "Properties file cannot be opened.");

            if (e instanceof RepositoryNotInitializedException) openingError.setOriginalExceptionMessage(((RepositoryNotInitializedException) e).getExceptionMessage());
            else openingError.setOriginalExceptionMessage(e.getMessage());

            this.notify(RepositoryErrors.REPOSITORY_OPENING_ERROR, openingError);
            return;
        }
        LOGGER.debug(String.format("Initialization of file repository with properties file [%s] was successful;", aPropertiesFile.getPath()));

        // Set state:
        this.isOpened = true;
        this.currentRepositoryType = RepositoryTypes.FileRepository;

        // Notify observers:
        this.notify(RepositoryEvents.REPOSITORY_OPENED, RepositoryTypes.FileRepository, aPropertiesFile);

    }

    /**
     * Try to save properties in repository.
     * Get all properties changes from properties table and save, delete and update it.
     */
    public void save() {
        LOGGER.debug("Try to save all changes in repository: ");

        // Handle properties changes:
        PropertiesChanges changes = PropertiesTableController.getInstance().getPropertiesTableModel().getPropertiesChanges();
        LOGGER.debug(String.format("Save property changes: [%s];", changes));

        // Save in repository:
        // Inserts:
        // Updates:
        // Update properties keys before:
        this.propertiesRepository.updateKeys(changes.getPropertiesKeysUpdates());
        // Update properties values:
        this.propertiesRepository.updateValues(changes.getPropertiesValueUpdates());
        // Deletions:
        // Flush changes:
        try {
            this.propertiesRepository.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        // Check unsaved changes:

        // Close repository:
        this.closeRepository();
    }

    private void closeRepository() {
        if (this.propertiesRepository == null) return;

        // Try to close repository:
        try {
            // Switch repository type:
            if (this.currentRepositoryType.getType() == 1) { // FileRepository:
                ((FileRepository) this.propertiesRepository).close();
            } else LOGGER.warn("Repository type not supported.");
        }catch (Exception e) {
            LOGGER.error("Error when closing repository.");
        }

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
            case 103: { // SaveFile FileMenu item:
                this.save();
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
        // Iterate through subscribers:
        this.subscribers.forEach((subscriber, actionEvents) -> {

            // Iterate through supported event array of current subscriber:
            for (PropperApplicationEvent event: actionEvents) {
                if (event == anApplicationEvent) subscriber.update(anApplicationEvent, anArguments);
            }
        });
    }
}
