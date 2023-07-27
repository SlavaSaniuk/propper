package me.saniukvyacheslav.gui.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ApplicationDialogs {

    public static final ButtonType BTN_YES = new ButtonType("Yes");
    public static final ButtonType BTN_NO = new ButtonType("No");
    public static final ButtonType BTN_CANCEL = new ButtonType("Cancel");

    public static Alert unsavedResultDialog(String aDialogTitle) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle(aDialogTitle);
        alert.setContentText("You have unsaved changes in properties file. \n" +
                "Do you want to save it?");
        alert.getButtonTypes().setAll(
                ApplicationDialogs.BTN_YES, ApplicationDialogs.BTN_NO, ApplicationDialogs.BTN_CANCEL);
        return alert;
    }


}
