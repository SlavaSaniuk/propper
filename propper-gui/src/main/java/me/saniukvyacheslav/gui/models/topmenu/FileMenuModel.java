package me.saniukvyacheslav.gui.models.topmenu;


import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;


/**
 * FileMenu menu model.
 */
public class FileMenuModel {

    private MenuItem openFileItem; // OPEN_FILE menu item;
    private MenuItem saveFileItem; // SAVE_FILE menu item;
    private MenuItem newFileItem; // NEW_FILE menu item;

    /**
     * Construct new model for FileMenu menu.
     * Also set keyboard hotkeys for embedded items.
     * @param aFileMenu - FileMenu Menu node.
     */
    public FileMenuModel(Menu aFileMenu) {

        // Initialize menu items:
        for (MenuItem item: aFileMenu.getItems()) {
            if (item.getId() == null) continue;
            if (item.getId().equals("file_menu_item_new")) this.newFileItem = item;
            if (item.getId().equals("file_menu_item_open")) this.openFileItem = item;
            if (item.getId().equals("file_menu_item_save")) this.saveFileItem = item;
        }

        // Set keyboard hotkeys:
        this.setHotKeys();
    }

    /**
     * Set keyboard hotkeys to embedded menu items.
     * "Ctrl+N" for "New file" menu item.
     * "Ctrl+O" for "Open file" menu item.
     * "Ctrl+S" for "Save file" menu item.
     */
    private void setHotKeys() {
        // For NewFile fileMenu item:
        this.newFileItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        // For openFileItem:
        this.openFileItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        // For saveFileItem;
        this.saveFileItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

    }
}
