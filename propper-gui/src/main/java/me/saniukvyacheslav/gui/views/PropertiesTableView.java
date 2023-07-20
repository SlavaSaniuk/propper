package me.saniukvyacheslav.gui.views;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * {@link PropertiesTableView} view used to stylize properties table.
 * Use {@link PropertiesTableView#draw()} method to apply styles to inner {@link GridPane}.
 */
public class PropertiesTableView {

    private final GridPane propsTable;

    /**
     * Construct new {@link PropertiesTableView} view with inner GridPane table.
     * @param aPropertiesTable - inner GridPane properties table.
     */
    public PropertiesTableView(GridPane aPropertiesTable) {
        this.propsTable = aPropertiesTable;
    }

    /**
     * Apply styles to inner GridPane properties table.
     */
    public void draw() {

        // Apply column constraints:
        this.columnConstraints();

        // Create table title:
        this.tableTitle();

        // Set table grid visibility:
        this.propsTable.setGridLinesVisible(true);
    }

    /**
     * Apply column constraints to GridPane properties table columns.
     * By default, 2 columns with same width (50%/50%), with center horizontal alignment.
     */
    private void columnConstraints() {

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(50);
        columnConstraints.setHalignment(HPos.CENTER);

        this.propsTable.getColumnConstraints().add(0, columnConstraints);
        this.propsTable.getColumnConstraints().add(1, columnConstraints);
    }

    /**
     * Construct title row for inner GridPane properties table.
     * By default, 2 columns ("Key" column and "Value" column).
     * Note: Columns creates via {@link PropertiesTableView#titleColumn(String)} method.
     */
    private void tableTitle() {
        this.propsTable.add(this.titleColumn("Key"), 0,0);
        this.propsTable.add(this.titleColumn("Value"), 1, 0);
    }

    /**
     * Construct new title column with specified title.
     * By default, title column has center horizontal alignment, with bold font of 12 size.
     * @param aTitle - column title.
     * @return - title column.
     */
    private Label titleColumn(String aTitle) {
        Label label = new Label(aTitle);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 12));
        return label;
    }

}
