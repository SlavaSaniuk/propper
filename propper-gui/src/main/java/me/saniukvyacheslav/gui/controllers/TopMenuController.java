package me.saniukvyacheslav.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;
import me.saniukvyacheslav.Main;
import me.saniukvyacheslav.gui.events.Observable;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.events.topmenu.TopMenuEvents;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TopMenuController implements Observable, Initializable {

    // Class variables:
    private final Map<Observer, PropperApplicationEvent[]> subscribers = new HashMap<>();
    private final FileChooser fileChooser = new FileChooser();


    @FXML
    public void onOpenFileAction() {
        File propertiesFile = this.fileChooser.showOpenDialog(Main.getPrimaryStage());
        this.notify(TopMenuEvents.OPEN_FILE, propertiesFile);
    }

    /**
     * Close propper-gui application with exit code 0.
     */
    @FXML
    public void closeApplication() {
        System.exit(0);
    }


    @Override
    public void subscribe(Observer anObserver, PropperApplicationEvent... anApplicationEvents) {
        this.subscribers.put(anObserver, anApplicationEvents);
    }

    @Override
    public void unsubscribe(Observer anObserver) {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set column title to fileChooser:
        this.fileChooser.setTitle("Choose properties file:");
    }
}
