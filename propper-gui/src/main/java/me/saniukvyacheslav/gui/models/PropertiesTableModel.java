package me.saniukvyacheslav.gui.models;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import me.saniukvyacheslav.gui.controllers.props.PropertyChangesController;
import me.saniukvyacheslav.prop.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Properties table model.
 */
public class PropertiesTableModel {

    // FX Nodes:
    private final GridPane embeddedGridPane; // Embedded GridPane layout;
    // Class variables:
    private final List<Property> loadedProperties = new ArrayList<>();
    private final List<PropertyModel> tableProperties = new ArrayList<>();

    /**
     * Construct new {@link PropertiesTableModel} model instance.
     * @param aGridPane - embedded GridPane layout.
     */
    public PropertiesTableModel(Node aGridPane) {
        if (aGridPane instanceof GridPane) this.embeddedGridPane = (GridPane) aGridPane;
        else throw new IllegalArgumentException("Parameter [aGridPane] must have GridPane class.");
    }

    public void loadProperties(List<Property> aPropertiesList) {
        // Load properties in embedded grid pane:
        int i=1;
        for (Property property: aPropertiesList) {
            PropertyModel model = new PropertyModel(property);
            this.tableProperties.add(model);
            this.embeddedGridPane.addRow(i, model.getKeyPropertyField(), model.getValuePropertyField());
            i++;
        }
    }

    public void clearTable() {
        this.embeddedGridPane.getChildren().remove(2, (this.tableProperties.size()*2)+3);
        this.tableProperties.clear();
    }

    /**
     * Check if this table model has unsaved properties changes.
     * @return - true, if this properties table has unsaved properties changes.
     */
    public boolean isUnsavedChanges() {
        return PropertyChangesController.getInstance().getUpdatesCount() != 0;
    }

}
