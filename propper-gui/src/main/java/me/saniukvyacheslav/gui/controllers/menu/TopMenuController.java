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
import me.saniukvyacheslav.gui.events.menu.TopMenuEvents;
import me.saniukvyacheslav.gui.models.topmenu.FileMenuModel;
import me.saniukvyacheslav.gui.models.topmenu.PropertiesMenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TopMenu main controller class.
 */
public class TopMenuController implements Observer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopMenuController.class); // Logger;
    @Getter private FileMenuController fileMenuController; // FileMenu controller;
    @Getter private PropertiesMenuController propertiesMenuController; // PropertiesMenu controller;
    @FXML Node topMenuBar; // TopMenu Bar node;

    /**
     * Initialize this controller and inner controllers.
     */
    @FXML
    public void initialize() {

        // Get FX nodes:
        Menu fileMenuNode = null;
        Menu propertiesMenuNode = null; // properties menu node;
        String menuId;
        for (Menu menu : ((MenuBar) this.topMenuBar).getMenus()) {
            // Get menu id and check it:
            menuId = menu.getId(); if (menuId == null) continue;
            switch (menuId) {
                case "menu_file_menu":
                    fileMenuNode = (menu);
                    break;
                case "menu_properties_menu":
                    propertiesMenuNode = menu;
                    break;
                default:
                    LOGGER.warn(String.format("Menu [id: [%s]] is not supported;", menuId));
            }
        }

        // Construct and init controllers:
        try {
            this.constructAndInitControllers(fileMenuNode, propertiesMenuNode);
        } catch (InitializationException e) {
            // Show global error dialog and close application:
            GuiConfiguration.getInstance().getErrorsController().showGlobalErrorDialogAndExit(
                    new InitializationApplicationError(e, e.getInitializationClass()), true);
        }

    }

    /**
     * Construct and init controllers from Menu nodes.
     * @param aFileMenuNode - FileMenu FX node.
     * @param aPropertiesMenuNode - PropertiesMenu FX node.
     * @throws InitializationException - if one of specified nodes is null.
     */
    private void constructAndInitControllers(Menu aFileMenuNode, Menu aPropertiesMenuNode) throws InitializationException {
        LOGGER.debug("Try to construct and init inner controllers:");
        // Check parameters:
        if (aFileMenuNode == null) throw new InitializationException(
                new NullPointerException("FX [Menu] Node with id [menu_file_menu] must be not null."), FileMenuController.class);
        if (aPropertiesMenuNode == null) throw new InitializationException(
                new NullPointerException("FX [Menu] Node with id [menu_properties_menu] must be not null."), PropertiesMenuController.class);
        // FileMenuController:
        this.fileMenuController = FileMenuController.getInstance(); // Construct new instance;
        this.fileMenuController.init(new FileMenuModel(aFileMenuNode)); // init;
        LOGGER.debug(String.format("FileMenuController controller instance: [%s];", this.fileMenuController));
        // PropertiesMenuController:
        this.propertiesMenuController = new PropertiesMenuController(new PropertiesMenuModel(aPropertiesMenuNode)); // construct and init;
        LOGGER.debug(String.format("PropertiesMenuController controller instance: [%s];", this.propertiesMenuController));
    }


    /**
     * Notify {@link PropertiesTableController} about
     * {@link TopMenuEvents#OPEN_FILE_EVENT} event.
     */
    @FXML public void onNewFileEvent() {
        this.fileMenuController.onNewFileEvent();
    }

    @FXML public void onOpenFileEvent() { this.fileMenuController.onOpenFileEvent(); }

    @FXML public void onSaveFileEvent() { this.fileMenuController.onSaveFileEvent(); }

    /**
     * Notify observers about {@link TopMenuEvents#CLOSE_FILE_EVENT}.
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
        PropperGui.getInstance().normallyCloseApplication();
    }


    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {

    }
}
