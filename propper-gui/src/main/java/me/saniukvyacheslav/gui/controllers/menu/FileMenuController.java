package me.saniukvyacheslav.gui.controllers.menu;

import javafx.stage.FileChooser;
import me.saniukvyacheslav.Main;
import me.saniukvyacheslav.gui.controllers.menu.events.FileMenuEvents;
import me.saniukvyacheslav.gui.events.Observable;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton instance.
 */
public class FileMenuController implements Observable {

    // Class variables:
    private static FileMenuController INSTANCE; // Singleton instance:
    private final Map<Observer, PropperApplicationEvent[]> subscribers = new HashMap<>(); // Map of observers;
    private final FileChooser fileChooser = new FileChooser();

    /**
     * Get singleton instance of FileMenu controller.
     * @return - singleton instance.
     */
    public static FileMenuController getInstance() {
        if (FileMenuController.INSTANCE == null) FileMenuController.INSTANCE = new FileMenuController();
        return FileMenuController.INSTANCE;
    }

    public void onOpenFileEvent() {
        // Set fileChooser title:
        this.fileChooser.setTitle("Open properties file:");
        // Open file:
        File propertiesFile = this.fileChooser.showOpenDialog(Main.getPrimaryStage());
        // Notify observers:
        this.notify(FileMenuEvents.OPEN_FILE_EVENT, propertiesFile);
    }





















    /**
     * Private default constructor, because it's singleton instance;
     */
    private FileMenuController() {}

    @Override
    public void subscribe(Observer anObserver, PropperApplicationEvent... anApplicationEvents) {
        this.subscribers.put(anObserver, anApplicationEvents);
    }

    @Override
    public void unsubscribe(Observer anObserver) {
        this.subscribers.remove(anObserver);
    }

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
