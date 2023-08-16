package me.saniukvyacheslav.gui.models.topmenu;


import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import lombok.Getter;
import me.saniukvyacheslav.gui.views.menu.FileMenuView;


/**
 * FileMenu menu model.
 */
public class FileMenuModel {

    // Embedded FX nodes:
    @Getter private MenuItem openFileItem; // OPEN_FILE menu item;
    @Getter private MenuItem saveFileItem; // SAVE_FILE menu item;
    @Getter private MenuItem newFileItem; // NEW_FILE menu item;
    @Getter private MenuItem closeFileItem; // CLOSE_FILE menu item;
    // View:
    private final FileMenuView fileMenuView;

    /**
     * Construct new model for FileMenu menu.
     * Also set keyboard hotkeys for embedded items.
     * @param aFileMenu - FileMenu Menu node.
     */
    public FileMenuModel(Menu aFileMenu) {

        // Initialize menu items:
        for (MenuItem item: aFileMenu.getItems()) {
            if (item.getId() == null) continue;
            switch (item.getId()) {
                case "file_menu_item_new":
                    this.newFileItem = item;
                    break;
                case "file_menu_item_open":
                    this.openFileItem = item;
                    break;
                case "file_menu_item_save":
                    this.saveFileItem = item;
                    break;
                case "file_menu_item_close":
                    this.closeFileItem = item;
                    break;
            }
        }

        // Create view:
        this.fileMenuView = new FileMenuView(this);
    }

    /**
     * Enable/disable "Save", "Close" menu items.
     * @param isDisabled - disabled state.
     */
    public void setDisableSaveCloseMenuItems(boolean isDisabled) {
        this.fileMenuView.setDisableMenuItem(this.saveFileItem, isDisabled);
        this.fileMenuView.setDisableMenuItem(this.closeFileItem, isDisabled);
    }
}
