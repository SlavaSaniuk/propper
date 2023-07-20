package me.saniukvyacheslav.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.events.topmenu.TopMenuEvents;
import me.saniukvyacheslav.gui.views.PropertiesTableView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class PrimaryNodeController implements Initializable, Observer {

    // Inner nodes:
    @FXML
    private Parent topMenu;
    @FXML
    private GridPane propertiesTable;
    // Views:
    PropertiesTableView propertiesTableView;
    // Inner controllers:
    @FXML @Getter
    private TopMenuController topMenuController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Draw empty properties table:
        this.propertiesTableView = new PropertiesTableView(this.propertiesTable);
        this.propertiesTableView.draw();

        // Configure top menu controller:
        System.out.println(this.topMenu.getScene());
        System.out.println(this.propertiesTable.getScene());
        this.topMenuController.subscribe(this, TopMenuEvents.OPEN_FILE);

    }

    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {
        System.out.println(arguments.length);
        System.out.println("Update action!");
        File propertiesFile = (File) arguments[0];
        System.out.println(propertiesFile.getPath());
    }


}
