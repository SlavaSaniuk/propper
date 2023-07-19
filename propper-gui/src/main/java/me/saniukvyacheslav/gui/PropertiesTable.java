package me.saniukvyacheslav.gui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class PropertiesTable {

    public GridPane drawPropertiesTable() {
        GridPane gridPane = new GridPane();

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(50);
        columnConstraints.setHalignment(HPos.CENTER);

        gridPane.getColumnConstraints().add(0, columnConstraints);
        gridPane.getColumnConstraints().add(1, columnConstraints);

        Label label = new Label("Property key");
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 12));

        gridPane.setGridLinesVisible(true);


        gridPane.add(label, 0,0);
        gridPane.add(new Label("Property value"), 1, 0);

        return gridPane;
    }

}
