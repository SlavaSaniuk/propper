package me.saniukvyacheslav.gui.views;

import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * {@link PropertiesTableView} view used to stylize properties table.
 * Use {@link PropertiesTableView#drawEmpty()} method to apply styles to inner {@link GridPane}.
 */
public class PropertiesTableView {

    private final GridPane propsTable; // GridPane layout;

    /**
     * Construct new {@link PropertiesTableView} view with inner GridPane table.
     * @param aPropertiesTable - inner GridPane properties table.
     */
    public PropertiesTableView(GridPane aPropertiesTable) {
        this.propsTable = aPropertiesTable;

        // Set common style:
        this.propsTable.getStyleClass().addAll("properties-table-common");
    }

    /**
     * Create properties table title row, and apply common styles to GridPane.
     */
    public GridPane drawEmpty() {

        // Apply column constraints:
        this.columnConstraints();

        // Create table title:
        this.tableTitle();

        return this.propsTable;
    }

    /**
     * Apply column constraints to GridPane properties table columns.
     * By default, 2 columns with same width (50%/50%), with center horizontal alignment.
     */
    private void columnConstraints() {

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(50);

        this.propsTable.getColumnConstraints().add(0, columnConstraints);
        this.propsTable.getColumnConstraints().add(1, columnConstraints);
    }

    /**
     * Construct title row for inner GridPane properties table.
     * By default, 2 columns ("Key" column and "Value" column).
     * Note: Columns creates via {@link PropertiesTableView#titleColumn(String)} method.
     */
    private void tableTitle() {
        Node keyTitle = this.titleColumn("Key");
        Node valueTitle = this.titleColumn("Value");
        this.propsTable.add(keyTitle, 0,0);
        this.propsTable.add(valueTitle, 1, 0);
        GridPane.setHalignment(keyTitle, HPos.CENTER);
        GridPane.setHalignment(valueTitle, HPos.CENTER);
    }

    /**
     * Construct new title column with specified title.
     * By default, title column has center horizontal alignment, with bold font of 12 size.
     * @param aTitle - column title.
     * @return - title column.
     */
    private Label titleColumn(String aTitle) {
        // Create label node:
        Label label = new Label(aTitle);
        // Set css style:
        label.getStyleClass().addAll(
                "properties-table-title-column");
        return label;
    }

    /**
     *
     * @param isWritable
     * @return
     */
    public static Node createPropertyCellNode(String aText, boolean isWritable) {

        Node node = null;
        if (isWritable) node = new TextField(aText);
        else node = new Label(aText);

        node.getStyleClass().addAll(
                "properties-cell-common", "property-key");
        GridPane.setHgrow(node, Priority.ALWAYS);
        node.minWidth(Double.MAX_VALUE);
        node.maxWidth(Double.MAX_VALUE);

        return node;

    }

}
