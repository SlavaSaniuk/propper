package me.saniukvyacheslav.gui.controllers.menu;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import lombok.Getter;

import me.saniukvyacheslav.core.PropperGui;
import me.saniukvyacheslav.core.error.global.InitializationApplicationError;
import me.saniukvyacheslav.core.exception.InitializationException;
import me.saniukvyacheslav.gui.GuiConfiguration;
import me.saniukvyacheslav.gui.controllers.PropertiesTableController;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.events.menu.FileMenuEvents;
import me.saniukvyacheslav.gui.models.topmenu.FileMenuModel;

public class TopMenuController implements Observer {

    @Getter private FileMenuController fileMenuController; // FileMenu controller;
    @FXML Node topMenuBar; // TopMenu Bar node;

    @FXML
    public void initialize() {


        // Construct models:
        FileMenuModel fileMenuModel = null;
        for (Menu menu : ((MenuBar) this.topMenuBar).getMenus()) {
            if (menu.getId() == null) continue;
            if (menu.getId().equals("menu_file_menu"))
                fileMenuModel = new FileMenuModel(menu);
        }

        // Initialize FileMenu controller:
        try {
            this.fileMenuController = FileMenuController.getInstance(); // Construct new instance;
            this.fileMenuController.init(fileMenuModel);
        } catch (InitializationException e) {
            // Show global error dialog and close application:
            GuiConfiguration.getInstance().getErrorsController().showGlobalErrorDialogAndExit(new InitializationApplicationError(e, FileMenuController.class), true);
        }
    }

    /**
     * Notify {@link PropertiesTableController} about
     * {@link FileMenuEvents#OPEN_FILE_EVENT} event.
     */
    @FXML public void onNewFileEvent() {
        this.fileMenuController.onNewFileEvent();
    }

    @FXML public void onOpenFileEvent() { this.fileMenuController.onOpenFileEvent(); }

    @FXML public void onSaveFileEvent() { this.fileMenuController.onSaveFileEvent(); }

    /**
     * Notify observers about {@link FileMenuEvents#CLOSE_FILE_EVENT}.
     * Call {@link FileMenuController#onCloseFileEvent()} method.
     */
    @FXML public void onCloseFileEvent() {
        this.fileMenuController.onCloseFileEvent();
    }

    /**
     * Exit from application with exit code 0.
     */
    @FXML
    public void onExitMenuAction() {
        // Exit from application:
        PropperGui.getInstance().close(0);
    }


    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {

    }
}
