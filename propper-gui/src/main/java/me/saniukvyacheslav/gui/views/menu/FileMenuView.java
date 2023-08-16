package me.saniukvyacheslav.gui.views.menu;

import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import me.saniukvyacheslav.gui.models.topmenu.FileMenuModel;

/**
 * FileMenu view.
 */
public class FileMenuView {

    private final FileMenuModel fileMenuModel; // FileMenu model;

    /**
     * Construct new FileMenu view.
     * @param aModel - FileMenu model.
     */
    public FileMenuView(FileMenuModel aModel) {
        this.fileMenuModel = aModel;

        // Init defaults:
        this.initDefaults();
    }

    /**
     * Disable/enable menu item.
     * @param item - menu item
     * @param isDisable - disabled state.
     */
    public void setDisableMenuItem(MenuItem item, boolean isDisable) {
        item.setDisable(isDisable);
    }


    private void initDefaults() {

        // Disable "Save" and "Close" menu items:
        this.fileMenuModel.getSaveFileItem().setDisable(true);
        this.fileMenuModel.getCloseFileItem().setDisable(true);

        // Set hotkeys:
        this.setHotKeys();
    }

    /**
     * Set keyboard hotkeys to embedded menu items.
     * "Ctrl+N" for "New file" menu item.
     * "Ctrl+O" for "Open file" menu item.
     * "Ctrl+S" for "Save file" menu item.
     * "Ctrl+X" for "Close file" menu item.
     */
    private void setHotKeys() {
        // For NewFile fileMenu item:
        this.fileMenuModel.getNewFileItem().setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        // For openFileItem:
        this.fileMenuModel.getOpenFileItem().setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        // For saveFileItem;
        this.fileMenuModel.getSaveFileItem().setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        // For closeFileItem;
        this.fileMenuModel.getCloseFileItem().setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
    }



}
