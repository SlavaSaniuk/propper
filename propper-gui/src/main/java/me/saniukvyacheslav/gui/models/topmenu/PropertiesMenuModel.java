package me.saniukvyacheslav.gui.models.topmenu;

import javafx.scene.control.Menu;
import lombok.Getter;
import me.saniukvyacheslav.gui.views.menu.PropertyMenuView;

import java.util.Objects;

/**
 * Top "Properties" menu model.
 */
public class PropertiesMenuModel {

    // FX Menu:
    @Getter private final Menu propertiesMenu; // FX Menu node;
    private final PropertyMenuView menuView; // PropertiesMenu view;

    /**
     * Construct new PropertiesMenu model instance.
     * @param aPropertiesMenu - FX Menu node.
     */
    public PropertiesMenuModel(Menu aPropertiesMenu) {
        Objects.requireNonNull(aPropertiesMenu, "Menu [aPropertiesMenu] must be not null.");
        this.propertiesMenu = aPropertiesMenu;

        // Construct view:
        this.menuView = new PropertyMenuView(this);

        // Disable menu by default:
        this.disableMenu();
    }

    /**
     * Disable whole PropertiesMenu menu.
     */
    public void disableMenu() {
        this.menuView.setDisabledMenu(true);
    }

    /**
     * Enable whole PropertiesMenu menu.
     */
    public void enableMenu() {
        this.menuView.setDisabledMenu(false);
    }
}
