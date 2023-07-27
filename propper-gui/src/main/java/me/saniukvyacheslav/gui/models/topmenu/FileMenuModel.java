package me.saniukvyacheslav.gui.models.topmenu;


import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;


public class FileMenuModel {

    private MenuItem openFileItem; // OPEN_FILE menu item;


    public FileMenuModel(Menu aFileMenu) {

        // Initialize menu items:
        for (MenuItem item: aFileMenu.getItems()) {
            if (item.getId() == null) continue;
            if (item.getId().equals("file_menu_item_open")) this.openFileItem = item;
        }

        this.setHotKeys();
    }

    private void setHotKeys() {
        // For openFileItem:
        KeyCombination combination = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
        this.openFileItem.setAccelerator(combination);
    }
}
