package me.saniukvyacheslav.gui.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.annotation.Nullable;

/**
 * Different application dialogs.
 */
public class ApplicationDialogs {

    public static final ButtonType BTN_YES = new ButtonType("Yes");
    public static final ButtonType BTN_NO = new ButtonType("No");
    public static final ButtonType BTN_CANCEL = new ButtonType("Cancel");

    /**
     *  Get "Save file" dialog instance. This dialog usually used when user want to open, close, create new properties
     * file, when current properties file has unsaved changes. If aPathToPropertiesFile not null and not empty, then in
     * dialog text, this path will be showed.
     * @param aPathToPropertiesFile - path to properties file.
     *                              This dialog show text "You have .... in properties file [A_PATH_TO_PROPERTIES_FILE.]"
     * @return - User answer (OK, NO, Cancel).
     */
    public static Alert saveFileDialog(@Nullable String aPathToPropertiesFile) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Save properties file:");

        // Set content text:
        // Check specified path for null and empty value:
        String path = "";
        if ((aPathToPropertiesFile != null) && (!(aPathToPropertiesFile.isEmpty())))
            path = aPathToPropertiesFile;
        alert.setContentText(String.format("You have unsaved changes in properties file %s. \n" +
                "Do you want to save it?", path));

        alert.getButtonTypes().setAll(
                ApplicationDialogs.BTN_YES, ApplicationDialogs.BTN_NO, ApplicationDialogs.BTN_CANCEL);
        return alert;
    }


}
