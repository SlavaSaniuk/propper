package me.saniukvyacheslav.gui.models;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import me.saniukvyacheslav.core.property.PropertiesChanges;
import me.saniukvyacheslav.core.property.PropertiesChangesHandler;
import me.saniukvyacheslav.core.property.PropertyChanges;
import me.saniukvyacheslav.core.util.UniqueElementsList;
import me.saniukvyacheslav.gui.controllers.props.PropertyChangesController;
import me.saniukvyacheslav.gui.views.table.PropertiesTableView;
import me.saniukvyacheslav.prop.Property;

import java.util.*;

/**
 * Properties table model.
 */
public class PropertiesTableModel {


    // Class variables:
    private final PropertiesTableView tableView; // Properties table view;
    // States:
    @Getter private boolean isClear = true; // Is GridPane has children flag;
    @Getter private final List<Property> originPropertiesList = new UniqueElementsList<>();
    private final List<PropertyModel> propertiesModels = new UniqueElementsList<>();
    private int lastTableRow = 0; // Last used table row;
    private final GridPane embeddedGridPane; // Embedded GridPane layout (JavaFx GridPane node);
    private boolean isUnsaved = false; // Unsaved state: If table has unsaved changes - true;

    /**
     * Construct new {@link PropertiesTableModel} model instance.
     * @param aGridPane - embedded GridPane layout.
     */
    public PropertiesTableModel(Node aGridPane) {
        // Check and cast GridPane:
        if (aGridPane instanceof GridPane) this.embeddedGridPane = (GridPane) aGridPane;
        else throw new IllegalArgumentException("Parameter [aGridPane] must have GridPane class.");

        // Initialize table view:
        this.tableView = new PropertiesTableView(this.embeddedGridPane);

        // Set default label:
        this.drawDefaultLayout();
    }

    /**
     * Load properties into this table.
     * Attention: Method clear table before, and then draw new empty table.
     * If developer wants to insert new property, developer has to use {@link PropertiesTableModel#insertIntoTable(Property)} method.
     * @param aPropertiesList - list to load.
     */
    public void loadIntoTable(List<Property> aPropertiesList) {

        // Draw empty table:
        this.drawEmptyTable();

        // Insert new rows:
        aPropertiesList.forEach((property -> {
            this.originPropertiesList.add(property); // Add property to origin properties list:
            this.insertIntoTable(property);
        }));

    }

    /**
     * Insert property into table.
     * Method create model for property and add it in {@link PropertiesTableModel#propertiesModels} list.
     * Then, method add new row in {@link PropertiesTableModel#embeddedGridPane} GridPane.
     * Attention: method doesn't add property in {@link PropertiesTableModel#originPropertiesList} list.
     * @param aProperty - property to insert.
     */
    public void insertIntoTable(Property aProperty) {
        Objects.requireNonNull(aProperty, "Property [aProperty] must be not [null].");

        // Create model for property:
        PropertyModel model = new PropertyModel(aProperty);
        this.propertiesModels.add(model);

        // Insert in GridPane:
        this.lastTableRow += 1;
        this.embeddedGridPane.addRow(this.lastTableRow, model.getKeyPropertyField(), model.getValuePropertyField());
    }

    /**
     * Set default label to embedded GridPane.
     * When properties file isn't opened, label is being showed.
     */
    private void drawDefaultLayout() {
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
     * Clear current table and draw new empty table.
     * Add title row to GridPane layout and stylize it.
     */
    private void drawEmptyTable() {
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


    private List<PropertyChanges> getListOfChanges() {
        List<PropertyChanges> changesList = new ArrayList<>();
        PropertyChangesController.getInstance().getUpdatesMap().forEach((String originKey, Property changedProp) -> changesList.add(new PropertyChanges(originKey, changedProp)));

        return changesList;
    }

    public PropertiesChanges getPropertiesChanges() {
        return PropertiesChangesHandler.handle(this.originPropertiesList, this.getListOfChanges());
    }

    /**
     * Clear "updated" css pseudo class from all properties models in table.
     */
    public void clearUpdatedClass() {
        this.propertiesModels.forEach(model -> model.setUpdatedClass(false));
    }

    /**
     * Set this table unsaved state.
     * @param state - state.
     */
    public void setUnsavedState(boolean state) {
        this.isUnsaved = state;
    }

    /**
     * Check for unsaved changes in this table.
     * @return - true, if this table has unsaved changes.
     */
    public boolean isUnsavedChanged() {
        return this.isUnsaved;
    }

}
