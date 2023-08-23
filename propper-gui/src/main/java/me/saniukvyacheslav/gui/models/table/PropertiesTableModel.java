package me.saniukvyacheslav.gui.models.table;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import me.saniukvyacheslav.core.property.PropertiesChanges;
import me.saniukvyacheslav.core.property.PropertiesChangesHandler;
import me.saniukvyacheslav.core.property.PropertyChanges;
import me.saniukvyacheslav.core.util.UniqueElementsList;
import me.saniukvyacheslav.gui.controllers.props.PropertyChangesController;
import me.saniukvyacheslav.gui.models.PropertyModel;
import me.saniukvyacheslav.gui.views.table.PropertiesTableView;
import me.saniukvyacheslav.prop.Property;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Properties table model.
 */
public class PropertiesTableModel {

    // FX Nodes:
    private final GridPane embeddedGridPane; // Embedded GridPane layout;
    // Class variables:
    private final PropertiesTableView tableView; // Properties table view;
    // States:
    @Getter private boolean isClear = true; // Is GridPane has children flag;
    @Getter private final Set<Property> originPropertiesList = new HashSet<>();
    private final List<PropertyModel> propertiesModels = new UniqueElementsList<>();

    /**
     * Construct new {@link PropertiesTableModel} model instance.
     * @param aGridPane - embedded GridPane layout.
     */
    public PropertiesTableModel(Node aGridPane) {
        if (aGridPane instanceof GridPane) this.embeddedGridPane = (GridPane) aGridPane;
        else throw new IllegalArgumentException("Parameter [aGridPane] must have GridPane class.");

        // Initialize table view:
        this.tableView = new PropertiesTableView(this.embeddedGridPane);

        // Set default label:
        this.defaultLayout();
    }

    /**
     * Set default label to embedded GridPane.
     * When properties file isn't opened, label is being showed.
     */
    public void defaultLayout() {
        // Clear table before:
        if (!(this.isClear)) this.clearTable();

        // Label text:
        String defaultText = "Properties file isn't opened." +"\n"
                +"\"Ctrl+O\" to open.";
        // Construct label;
        Label noFileOpenedNode = new Label();
        noFileOpenedNode.setText(defaultText);

        // Add text to layout:
        this.embeddedGridPane.add(noFileOpenedNode, 0, 0);
        // Stylize:
        this.tableView.stylizeDefaultLayout(noFileOpenedNode);

        // Set clear state:
        this.isClear = false;
    }

    /**
     * Add title row to GridPane layout and stylize it.
     */
    public void emptyTable() {
        // Clear table before:
        if (!(this.isClear)) this.clearTable();

        // Stylize:
        this.tableView.stylizeNotEmptyTable();

        // Add title row:
        Label keyLabel = new Label("Key");
        Label valueLabel = new Label("Value");
        this.embeddedGridPane.addRow(0, keyLabel, valueLabel);
        // Stylize it:
        this.tableView.stylizeTitleRow(keyLabel, valueLabel);


        // GridPane has children:
        this.isClear = false;
    }

    /**
     *  New empty properties table for new property file.
     * Set "isNew" state to true.
     */
    public void newTable() {
        // Empty table:
        this.emptyTable();
    }

    /**
     *  Clear GridPane. Delete all children in GridPane (include title row), remove all constraints and set grid lines
     * visibility to false.
     */
    public void clearTable() {
        this.embeddedGridPane.getColumnConstraints().clear();
        this.embeddedGridPane.setGridLinesVisible(false);
        this.embeddedGridPane.getChildren().clear();
        // Remove all styles:
        this.embeddedGridPane.getStyleClass().clear();

        // Clear changes in PropertyChangesController:
        PropertyChangesController.getInstance().clear();

        this.isClear = true;
    }

    /**
     * Clear embedded GridPane and show default layout label.
     */
    public void closeTable() {
        this.clearTable();
        this.defaultLayout();
    }

    public void loadProperties(List<Property> aPropertiesList) {

        // Initialize empty table:
        this.emptyTable();

        // Load properties in embedded grid pane:
        int i=1;
        for (Property property: aPropertiesList) {
            PropertyModel model = new PropertyModel(property);
            this.embeddedGridPane.addRow(i, model.getKeyPropertyField(), model.getValuePropertyField());
            i++;

            // Add property to origin properties list and properties models lists:
            this.originPropertiesList.add(property);
            this.propertiesModels.add(model);
        }
    }

    private List<PropertyChanges> getListOfChanges() {
        List<PropertyChanges> changesList = new ArrayList<>();
        PropertyChangesController.getInstance().getUpdatesMap().forEach((String originKey, Property changedProp) -> changesList.add(new PropertyChanges(originKey, changedProp)));

        return changesList;
    }

    public PropertiesChanges getPropertiesChanges() {
        return PropertiesChangesHandler.handle(this.originPropertiesList, this.getListOfChanges());
    }

    /**
     * Check if this table model has unsaved properties changes.
     * @return - true, if this properties table has unsaved properties changes.
     */
    public boolean isUnsavedChanges() {
        return PropertyChangesController.getInstance().getUpdatesCount() != 0;
    }

    public void removeUpdatedClassFromPropertiesModels() {
        this.propertiesModels.forEach((model) -> model.getPropertyView().setUpdatedPropertyValue(false));
    }

}
