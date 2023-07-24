package me.saniukvyacheslav.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.events.topmenu.TopMenuEvents;

import java.net.URL;
import java.util.ResourceBundle;


public class PrimaryNodeController implements Initializable, Observer {

    // Inner controllers:
    @FXML private TopMenuController topMenuController;
    @FXML private StatusLineController statusLineController;
    @FXML private PropertiesTableController propertiesTableController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Configure top menu controller:
        // Subscribe observers:
        this.topMenuController.subscribe(this, TopMenuEvents.OPEN_FILE);
        this.topMenuController.subscribe(this.propertiesTableController, TopMenuEvents.OPEN_FILE);
        this.topMenuController.subscribe(this.statusLineController, TopMenuEvents.OPEN_FILE);
    }

    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {

    }



}
