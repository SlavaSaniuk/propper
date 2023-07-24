package me.saniukvyacheslav.gui.models;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import me.saniukvyacheslav.prop.Property;

import java.util.ArrayList;
import java.util.List;

public class PropertiesTable {

    // Embedded nodes:
    private GridPane embeddedGridPane;
    // Class variables:
    private final List<Property> loadedProperties = new ArrayList<>();

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
        for (int i=1; i<(this.loadedProperties.size()+1); i++) {
            this.embeddedGridPane.add(this.createCell(this.loadedProperties.get(i-1).getPropertyKey(), false), 0, i);
            this.embeddedGridPane.add(this.createCell(this.loadedProperties.get(i-1).getPropertyValue(), true), 1, i);
        }
    }

    private Node createCell(String aDefaultText, boolean isWritable) {
        Node cellNode;
        if (isWritable) {
            cellNode = new TextField();
            ((TextField) cellNode).setText(aDefaultText);
        }
        else cellNode = new Label(aDefaultText);

        return cellNode;
    }


}
