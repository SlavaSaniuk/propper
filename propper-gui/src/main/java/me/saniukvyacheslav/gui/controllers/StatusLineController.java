package me.saniukvyacheslav.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import me.saniukvyacheslav.gui.controllers.menu.TopMenuController;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.controllers.menu.TopMenuEvents;
import me.saniukvyacheslav.gui.models.StatusLine;
import me.saniukvyacheslav.gui.views.StatusLineView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * StatusLine controller updates embedded status line label text on different user actions.
 * This controller subscribed to {@link TopMenuController} and {@link  PrimaryNodeController} observable controllers.
 */
public class StatusLineController implements Observer, Initializable {

    // Inner nodes:
    @FXML
    private Node statusLine;
    @FXML
    private Label statusLineLabel; // Inner status line label;
    // Class variables:
    private final StatusLine statusLineModel = new StatusLine(); // Status line model;

    /**
     * Update status line text on different user actions.
     * @param event - users action event.
     * @param arguments - action arguments.
     */
    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {

        switch (event.getCode()) {
            case 102: // OPEN_FILE_EVENT FileMenuController event:
                this.onOpenFileAction(((File) arguments[0]).getAbsolutePath());
                break;
            case 205: // PROPERTY_UPDATE_EVENT PropertyChangesController event:
                this.onPropertyChangeEvent((int) arguments[0]);
                break;
        }

        // If user open new properties file:
        if (event == TopMenuEvents.OPEN_FILE) {
            this.onOpenFileAction(((File) arguments[0]).getAbsolutePath());
        }

    }

    /**
     * Update status line label text, when user open properties file.
     * Clear path, reset counters in {@link StatusLine} model, set new path in it, and set new embedded label text.
     * @param aPathToPropertiesFile - path to properties file.
     */
    public void onOpenFileAction(String aPathToPropertiesFile) {
        // Clear model, set path to properties file, and set new label text:
        this.statusLineModel.clear();
        this.statusLineModel.setPropertiesFilePath(aPathToPropertiesFile);
        this.statusLineLabel.setText(this.statusLineModel.getLineText());
    }

    /**
     * Update status line text, when user update property key or value.
     * @param anUpdatesCount - whole updates count.
     */
    public void onPropertyChangeEvent(int anUpdatesCount) {
        // Set updates count in model, then set new text to label:
        this.statusLineModel.setUpdateActionsCounter(anUpdatesCount);
        this.statusLineLabel.setText(this.statusLineModel.getLineText());
    }

    /**
     * Set default status line text.
     * @param location
     * The location used to resolve relative paths for the root object, or
     * <tt>null</tt> if the location is not known.
     * @param resources
     * The resources used to localize the root object, or <tt>null</tt> if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set default status line text:
        this.statusLineLabel.setText("");

        // Create status line view:
        new StatusLineView(this.statusLine).stylize();
    }
}
