package me.saniukvyacheslav.gui.controllers.menu;

import javafx.stage.FileChooser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.annotation.pattern.Singleton;
import me.saniukvyacheslav.core.error.ApplicationError;
import me.saniukvyacheslav.core.exception.InitializationException;
import me.saniukvyacheslav.core.repo.RepositoryEvents;
import me.saniukvyacheslav.core.repo.RepositoryTypes;
import me.saniukvyacheslav.definition.Initializable;
import me.saniukvyacheslav.gui.GuiConfiguration;
import me.saniukvyacheslav.gui.events.menu.TopMenuEvents;
import me.saniukvyacheslav.gui.events.Observable;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.models.topmenu.FileMenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * FileMenuController controller.
 * This controller instance handle FileMenu menu actions.
 */
@Singleton
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileMenuController implements Observable, Observer, Initializable {

    private static FileMenuController INSTANCE; // Singleton instance.
    private static final Logger LOGGER = LoggerFactory.getLogger(FileMenuController.class); // Logger;
    private final Map<Observer, PropperApplicationEvent[]> subscribers = new HashMap<>(); // Map of observers;
    private final FileChooser fileChooser = new FileChooser(); // FileChooser;
    private FileMenuModel fileMenuModel; // FileMenu model;

    /**
     * Get current singleton instance of this class.
     * @return - singleton instance.
     */
    public static FileMenuController getInstance() {
        if(FileMenuController.INSTANCE == null) {
            LOGGER.debug("Construct [FileMenuController] singleton instance. FileMenuController instance must be initialized.");
            FileMenuController.INSTANCE = new FileMenuController();
        }
        return FileMenuController.INSTANCE;
    }

    /**
     * Initialize this singleton instance with FileMenu model object.
     * @param objects - {@link FileMenuModel} model as first element in array.
     * @throws InitializationException - if specified element null or not FileMenuModel.
     */
    @Override
    public void init(Object... objects) throws InitializationException {
        LOGGER.debug("Try to initialize [FileMenuController] singleton instance. Check arguments:");

        // Cast and check parameters:
        try {
            Objects.requireNonNull(objects[0], "FileMenuModel [objects[0] must be not null.]");
            this.fileMenuModel = (FileMenuModel) objects[0];
        }catch (ClassCastException | NullPointerException e) {
            throw new InitializationException(e, FileMenuController.class);
        }

        LOGGER.debug(String.format("Model [FileMenuModel]: [%s];", this.fileMenuModel));
        LOGGER.debug("Initialize [FileMenuController] singleton instance: SUCCESSFUL;");
    }

    /**
     * Show 'Create file' fileChooser and then create or rewrite selected file.
     * If file already exist, fileChooser will ask user about rewrite file.
     * Notify all subscribed observers about {@link TopMenuEvents#OPEN_FILE_EVENT} event.
     */
    public void onNewFileEvent() {
        LOGGER.debug("Show 'Create file' fileChooser dialog:");

        // Show save file dialog and check result:
        File file = this.fileChooser.showSaveDialog(GuiConfiguration.getInstance().getPrimaryStage());
        if (file == null) {
            LOGGER.debug("FileChooser window was closed without choose. Do nothing;");
            return;
        }else LOGGER.debug(String.format("FileChooser selected file: [%s];", file.getAbsolutePath()));

        // Check selected file:
        if (!(file.exists())) { // If file not exist, create it:
            try {
                LOGGER.debug(String.format("File [%s] - doesn't exist. Try to create it:", file.getAbsolutePath()));
                boolean isCreated = file.createNewFile();
                if (!isCreated) {
                    LOGGER.warn(String.format("File: [%s] - already exist;", file.getAbsolutePath()));
                    return;
                }
                LOGGER.debug(String.format("File [%s] was created;", file.getAbsolutePath()));
            }catch (IOException e) {
                LOGGER.error(String.format("Cannot create file [%s].", file.getAbsolutePath()));
                ApplicationError error = new ApplicationError(MenuErrors.FILE_CREATION_ERROR.getCode(), "Cannot create file.");
                error.setOriginalExceptionMessage(e.getMessage());
                this.notify(MenuErrors.FILE_CREATION_ERROR, error);
            }
        }else { // If file already exist, rewrite it:
            try {
                LOGGER.debug(String.format("File [%s] is exist. Try to clean it:", file.getAbsolutePath()));
                new PrintWriter(file).close();
                LOGGER.debug(String.format("File [%s] was cleaned.", file.getAbsolutePath()));
            } catch (FileNotFoundException e) {
                LOGGER.error(String.format("File [%s] not found.", file.getAbsolutePath()));
                ApplicationError error = new ApplicationError(MenuErrors.FILE_REWRITING_ERROR.getCode(), "Cannot rewrite file.");
                error.setOriginalExceptionMessage(e.getMessage());
                this.notify(MenuErrors.FILE_REWRITING_ERROR, error);
            }
        }

        // Notify observers about NEW_FILE_EVENT fileMenu event:
        LOGGER.debug("Notify observers about OPEN_FILE_EVENT[102] event:");
        this.notify(TopMenuEvents.OPEN_FILE_EVENT, RepositoryTypes.FileRepository, file);
    }

    /**
     * Run "Open file" file chooser and notify observers
     * about {@link TopMenuEvents#OPEN_FILE_EVENT}.
     */
    public void onOpenFileEvent() {
        LOGGER.debug("Show 'Open file' fileChooser dialog:");

        // Select file and check it:
        File propertiesFile = this.fileChooser.showOpenDialog(GuiConfiguration.getInstance().getPrimaryStage());
        if (propertiesFile == null) {
            LOGGER.debug("FileChooser window was closed without choose. Do nothing;");
            return;
        }else LOGGER.debug(String.format("FileChooser selected file: [%s];", propertiesFile.getAbsolutePath()));

        // Notify observers about OPEN_FILE_EVENT:
        LOGGER.debug("Notify observers about OPEN_FILE_EVENT[102] event:");
        this.notify(TopMenuEvents.OPEN_FILE_EVENT, RepositoryTypes.FileRepository, propertiesFile);
    }

    /**
     * Save properties in repository.
     * Method notify observers about {@link TopMenuEvents#SAVE_FILE_EVENT} event.
     */
    public void onSaveFileEvent() {
        LOGGER.debug("SAVE_FILE_EVENT FileMenu event:");

        // Notify observers:
        this.notify(TopMenuEvents.SAVE_FILE_EVENT);
    }

    /**
     * Close properties file.
     * Notify all subscribed observers about {@link TopMenuEvents#CLOSE_FILE_EVENT} event.
     */
    public void onCloseFileEvent() {
        LOGGER.debug(String.format("Notify observers about [%d: %s] event:", TopMenuEvents.CLOSE_FILE_EVENT.getCode(), TopMenuEvents.CLOSE_FILE_EVENT.name()));
        // Notify observers:
        this.notify(TopMenuEvents.CLOSE_FILE_EVENT);
    }

    /**
     * Enable "Save", "Close" menu items.
     */
    public void onRepositoryOpenedEvent() {
        LOGGER.debug("REPOSITORY_OPENED [505] event:");
        LOGGER.debug("Enable [SAVE], [CLOSE] menu items;");
        this.fileMenuModel.setDisableSaveCloseMenuItems(false);
    }

    /**
     * Disable "Save", "Close" menu items.
     */
    public void onRepositoryClosedEvent() {
        LOGGER.debug("Disable [SAVE, CLOSE] menu items:");
        this.fileMenuModel.setDisableSaveCloseMenuItems(true);
    }

    /**
     * Subscribe observer to this observable instance.
     * @param anObserver - observer.
     * @param anApplicationEvents - observer supported events.
     */
    @Override
    public void subscribe(Observer anObserver, PropperApplicationEvent... anApplicationEvents) {
        LOGGER.debug(String.format("Subscribe [%s] observer on [%s] FileMenu events.", anObserver, Arrays.toString(anApplicationEvents)));
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
     * Notify observers about FileMenu event.
     * @param anApplicationEvent - FileMenu event.
     * @param anArguments - event arguments.
     */
    @Override
    public void notify(PropperApplicationEvent anApplicationEvent, Object... anArguments) {
        LOGGER.debug(String.format("Notify observers about [%d] event:", anApplicationEvent.getCode()));
        // Iterate through subscribers:
        this.subscribers.forEach((subscriber, actionEvents) -> {

            // Iterate through supported event array of current subscriber:
            for (PropperApplicationEvent event: actionEvents) {
                if (event == anApplicationEvent) {
                    LOGGER.debug(String.format("Notify observer [%s] about [%d] file menu event:", subscriber.getClass().getCanonicalName(), event.getCode()));
                    subscriber.update(anApplicationEvent, anArguments);
                }
            }
        });
    }

    /**
     * Do something on event.
     * @param event - {@link Observable} instance event.
     * @param arguments - event arguments.
     */
    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {
        switch (event.getCode()) {
            case 550: { // REPOSITORY_OPENED event:
                this.onRepositoryOpenedEvent();
                break;
            }
            case 552: { // REPOSITORY_CLOSED event:
                LOGGER.debug(String.format("[%d: %s] event. Update [MenuItems] states:",
                        RepositoryEvents.REPOSITORY_CLOSED.getCode(), RepositoryEvents.REPOSITORY_CLOSED.name()));
                this.onRepositoryClosedEvent();
                break;
            }
            default: {
                LOGGER.warn(String.format("Event [eventCode: %d] not supported;", event.getCode()));
            }
        }
    }

}
