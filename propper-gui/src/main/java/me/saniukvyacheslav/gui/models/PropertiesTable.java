package me.saniukvyacheslav.gui.models;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import me.saniukvyacheslav.prop.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Properties table model.
 */
public class PropertiesTable {

    // Embedded nodes:
    private final GridPane embeddedGridPane;
    // Class variables:
    private final List<Property> loadedProperties = new ArrayList<>();
    private final List<PropertyModel> tableProperties = new ArrayList<>();

    /**
     * Construct new {@link PropertiesTable} model instance.
     * @param aGridPane - embedded grid pane.
     */
    public PropertiesTable(Node aGridPane) {
        if (aGridPane instanceof GridPane) this.embeddedGridPane = (GridPane) aGridPane;
        else throw new IllegalArgumentException("Parameter [aGridPane] must have GridPane class.");
    }

    public void loadProperties(List<Property> aPropertiesList) {
        // Clear loaded properties list:
        this.loadedProperties.clear();
        this.loadedProperties.addAll(aPropertiesList);

        // Load properties in embedded grid pane:
        int i=1;
        for (Property property: aPropertiesList) {
            PropertyModel model = new PropertyModel(property);
            this.tableProperties.add(model);
            this.embeddedGridPane.addRow(i, model.getKeyPropertyField(), model.getValuePropertyField());
            i++;
        }
    }

}
