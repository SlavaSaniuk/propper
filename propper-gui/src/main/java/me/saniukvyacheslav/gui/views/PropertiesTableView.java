package me.saniukvyacheslav.gui.views;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class PropertiesTableView implements Stylable {

    private final GridPane gridPaneLayout; // Embedded grid pane;

    public PropertiesTableView(Node aGridPane) {
        if (aGridPane instanceof GridPane) this.gridPaneLayout = ((GridPane) aGridPane);
        else throw new IllegalArgumentException("Specified Node must have [GridPane] class.");
    }

    @Override
    public void stylize() {

        // Set classes:
        this.gridPaneLayout.getStyleClass().addAll(
          "properties-table-common"
        );

    }


}
