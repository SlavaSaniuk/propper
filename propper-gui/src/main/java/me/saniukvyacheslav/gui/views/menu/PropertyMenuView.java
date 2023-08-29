package me.saniukvyacheslav.gui.views.menu;

import me.saniukvyacheslav.gui.models.topmenu.PropertiesMenuModel;

import java.util.Objects;

/**
 * Top "Properties" menu view.
 */
public class PropertyMenuView {
    private final PropertiesMenuModel menuModel; // PropertiesMenu model;

    /**
     * Construct new PropertiesMenu view instance.
     * @param aMenuModel - PropertiesMenu model.
     */
    public PropertyMenuView(PropertiesMenuModel aMenuModel) {
        // Check parameters:
        Objects.requireNonNull(aMenuModel, "PropertiesMenuModel [aMenuModel] must be not null.");
        // Map parameters:
        this.menuModel = aMenuModel;
    }

    /**
     * Enable/disable whole menu.
     * @param isDisabled - disabled flag.
     */
    public void setDisabledMenu(boolean isDisabled) {
        // Disable whole menu:
        this.menuModel.getPropertiesMenu().setDisable(isDisabled);
    }

}
