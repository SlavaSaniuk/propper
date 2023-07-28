package me.saniukvyacheslav.gui.views.table;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import me.saniukvyacheslav.gui.views.Stylable;

public class PropertiesTableView implements Stylable {

    private final GridPane gridPaneLayout; // Embedded grid pane;

    public PropertiesTableView(Node aGridPane) {
        if (aGridPane instanceof GridPane) this.gridPaneLayout = ((GridPane) aGridPane);
        else throw new IllegalArgumentException("Specified Node must have [GridPane] class.");
    }

    /**
     * Stylize default label (When user not open properties file).
     * Add "default-text-label" to label.
     * @param aDefaultText - label.
     */
    public void stylizeDefaultLayout(Label aDefaultText) {

        GridPane.setHgrow(aDefaultText, Priority.ALWAYS);
        GridPane.setVgrow(aDefaultText, Priority.ALWAYS);
        GridPane.setHalignment(aDefaultText, HPos.CENTER);
        GridPane.setValignment(aDefaultText, VPos.CENTER);


        // Add classes:
        aDefaultText.getStyleClass().add("default-text-label");
    }

    /**
     * Stylize not empty properties table.
     * Set "not-empty-table" css class, and set grid line visibility to true.
     */
    public void stylizeNotEmptyTable() {
        this.gridPaneLayout.setGridLinesVisible(true);
        this.gridPaneLayout.getStyleClass().add("not-empty-table");
    }

    /**
     * Stylize title row.
     * Set "title-cell" css class to each title cell. Align cells to center. Set column width to 50%;
     * @param aKeyLabel - key title cell.
     * @param aValueLabel - value title cell.
     */
    public void stylizeTitleRow(Label aKeyLabel, Label aValueLabel) {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(50);
        this.gridPaneLayout.getColumnConstraints().addAll(columnConstraints, columnConstraints);

        GridPane.setHalignment(aKeyLabel, HPos.CENTER);
        GridPane.setHalignment(aValueLabel, HPos.CENTER);

        // Set classes:
        aKeyLabel.getStyleClass().add("title-cell");
        aValueLabel.getStyleClass().add("title-cell");
    }

    @Override
    public void stylize() {

        // Set classes:
        //this.gridPaneLayout.getStyleClass().addAll(
          //"properties-table-common"
        //);

    }


}
