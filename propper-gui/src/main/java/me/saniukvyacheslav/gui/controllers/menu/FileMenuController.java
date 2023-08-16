package me.saniukvyacheslav.gui.controllers.menu;

import javafx.stage.FileChooser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.Main;
import me.saniukvyacheslav.core.error.ApplicationError;
import me.saniukvyacheslav.core.repo.RepositoryTypes;
import me.saniukvyacheslav.gui.controllers.menu.events.FileMenuEvents;
import me.saniukvyacheslav.gui.events.Observable;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton instance.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileMenuController implements Observable {

    // Class variables:
    private static final Logger LOGGER = LoggerFactory.getLogger(FileMenuController.class); // Logger;
    private static FileMenuController INSTANCE; // Singleton instance:
    private final Map<Observer, PropperApplicationEvent[]> subscribers = new HashMap<>(); // Map of observers;
    private final FileChooser fileChooser = new FileChooser(); // FileChooser;

    /**
     * Get singleton instance of FileMenu controller.
     * @return - singleton instance.
     */
    public static FileMenuController getInstance() {
        if (FileMenuController.INSTANCE == null) FileMenuController.INSTANCE = new FileMenuController();

        // Set common fileChooser title:
        FileMenuController.INSTANCE.fileChooser.setTitle("Select file ...");
        return FileMenuController.INSTANCE;
    }

    /**
     * Show 'Create file' fileChooser and then create or rewrite selected file.
     * If file already exist, fileChooser will ask user about rewrite file.
     * Notify all subscribed observers about {@link FileMenuEvents#OPEN_FILE_EVENT} event.
     */
    public void onNewFileEvent() {
        LOGGER.debug("Show 'Create file' fileChooser dialog:");

        // Show save file dialog and check result:
        File file = this.fileChooser.showSaveDialog(Main.getPrimaryStage());
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
        this.notify(FileMenuEvents.OPEN_FILE_EVENT, RepositoryTypes.FileRepository, file);
    }

    /**
     * Run "Open file" file chooser and notify observers
     * about {@link FileMenuEvents#OPEN_FILE_EVENT}.
     */
    public void onOpenFileEvent() {
        LOGGER.debug("Show 'Open file' fileChooser dialog:");

        // Select file and check it:
        File propertiesFile = this.fileChooser.showOpenDialog(Main.getPrimaryStage());
        if (propertiesFile == null) {
            LOGGER.debug("FileChooser window was closed without choose. Do nothing;");
            return;
        }else LOGGER.debug(String.format("FileChooser selected file: [%s];", propertiesFile.getAbsolutePath()));

        // Notify observers about OPEN_FILE_EVENT:
        LOGGER.debug("Notify observers about OPEN_FILE_EVENT[102] event:");
        this.notify(FileMenuEvents.OPEN_FILE_EVENT, RepositoryTypes.FileRepository, propertiesFile);
    }

    /**
     * Save properties file.
     * Method notify observers about {@link FileMenuEvents#SAVE_FILE_EVENT} event.
     */
    public void onSaveFileEvent() {
        System.out.println("Save file event stab.");

        // Notify observers:
        this.notify(FileMenuEvents.SAVE_FILE_EVENT);
    }

    /**
     * Close properties file.
     * Notify all subscribed observers about {@link FileMenuEvents#CLOSE_FILE_EVENT} event.
     */
    public void onCloseFileEvent() {
        // Notify observers:
        this.notify(FileMenuEvents.CLOSE_FILE_EVENT);
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

}
