package me.saniukvyacheslav.gui.controllers.menu;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import lombok.Getter;
import me.saniukvyacheslav.Main;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.models.topmenu.FileMenuModel;

import java.net.URL;
import java.util.ResourceBundle;

public class TopMenuController implements Initializable {

    // Fx nodes:
    @FXML Node topMenuBar; // TopMenu Bar node;

    // Class variables:
    private final FileMenuController fileMenuController = FileMenuController.getInstance(); // FileMenu controller;
    private static TopMenuController INSTANCE; // Instance of this controller;
    @Getter private FileMenuModel fileMenuModel;

    /**
     * Get current instance of this controller.
     * @return - instance of this controller.
     */
    public static TopMenuController getInstance() {
        return TopMenuController.INSTANCE;
    }

    /**
     * Subscribe observers on FileMenu events.
     * @param anObserver - observer.
     * @param anEvents - supported events.
     */
    public void subscribeOnFileMenuEvents(Observer anObserver, PropperApplicationEvent... anEvents) {
        this.fileMenuController.subscribe(anObserver, anEvents);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Initialize static instance:
        TopMenuController.INSTANCE = this;

        // Construct models:
        for (Menu menu : ((MenuBar) this.topMenuBar).getMenus()) {
            if (menu.getId() == null) continue;
            if (menu.getId().equals("menu_file_menu"))
                this.fileMenuModel = new FileMenuModel(menu);
        }

        // Set models to controllers:
        this.fileMenuController.setFileMenuModel(this.fileMenuModel);
    }

    /**
     * Notify {@link me.saniukvyacheslav.gui.controllers.table.PropertiesTableController} about
     * {@link me.saniukvyacheslav.gui.controllers.menu.events.FileMenuEvents#OPEN_FILE_EVENT} event.
     */
    @FXML public void onNewFileEvent() {
        this.fileMenuController.onNewFileEvent();
    }

    @FXML public void onOpenFileEvent() { this.fileMenuController.onOpenFileEvent(); }

    @FXML public void onSaveFileEvent() { this.fileMenuController.onSaveFileEvent(); }

    /**
     * Notify observers about {@link me.saniukvyacheslav.gui.controllers.menu.events.FileMenuEvents#CLOSE_FILE_EVENT}.
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
        System.out.println("123");
        // Exit from application:
        Main.exit();
    }




}
