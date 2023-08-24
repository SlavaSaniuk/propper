package me.saniukvyacheslav.gui.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Different application dialogs.
 */
public class ApplicationDialogs {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationDialogs.class); // Logger;
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

    public static Alert exceptionDialog(@Nullable String aTitle, @Nullable String aHeaderText, @Nullable String aContentText, boolean isPrintStackTrace, @Nullable Exception anException) {
        LOGGER.debug("Create exception dialog [Alert] instance. Check parameters:");
        Alert alert = new Alert(Alert.AlertType.ERROR);

        // Check and set parameters:
        if (aTitle == null || aTitle.isEmpty()) { // Dialog title:
            LOGGER.debug("Default exception dialog title: [Exception];");
            alert.setTitle("Exception");
        } else alert.setTitle(aTitle);
        if (aHeaderText != null && !aHeaderText.isEmpty())  { // Dialog header text:
            alert.setHeaderText(aHeaderText);
        }else alert.setHeaderText(null);
        if (aContentText == null || aContentText.isEmpty()) { // Content text:
            LOGGER.debug("Default exception dialog content: [Exception occurs];");
            alert.setContentText("Exception occurs");
        } else alert.setContentText(aContentText);

        // If print stack trace:
        if (isPrintStackTrace) {
            if(anException != null) {
                // Create expandable Exception.
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                anException.printStackTrace(pw);
                String exceptionText = sw.toString();

                Label label = new Label("The exception stacktrace was:");

                TextArea textArea = new TextArea(exceptionText);
                textArea.setEditable(false);
                textArea.setWrapText(true);

                textArea.setMaxWidth(Double.MAX_VALUE);
                textArea.setMaxHeight(Double.MAX_VALUE);
                GridPane.setVgrow(textArea, Priority.ALWAYS);
                GridPane.setHgrow(textArea, Priority.ALWAYS);

                GridPane expContent = new GridPane();
                expContent.setMaxWidth(Double.MAX_VALUE);
                expContent.add(label, 0, 0);
                expContent.add(textArea, 0, 1);

                // Set expandable Exception into the dialog pane.
                alert.getDialogPane().setExpandableContent(expContent);
            } else LOGGER.debug("Original exception instance is null.");

        }
        return alert;
    }

}
