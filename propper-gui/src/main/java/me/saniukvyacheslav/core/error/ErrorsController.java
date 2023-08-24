package me.saniukvyacheslav.core.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.Main;
import me.saniukvyacheslav.annotation.pattern.Singleton;
import me.saniukvyacheslav.core.PropperGui;
import me.saniukvyacheslav.gui.dialogs.ApplicationDialogs;
import me.saniukvyacheslav.gui.events.Observable;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * This controller class used to handle different application errors.
 * All logic of this controller in {@link ErrorsController#update(PropperApplicationEvent, Object...)} method.
 * Note: Developer must put {@link ApplicationError} pojo as first element in {@link ErrorsController#update(PropperApplicationEvent, Object...)} arguments array.
 */
@Singleton
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorsController implements Observer {

    // Class variables:
    private static ErrorsController INSTANCE; // Singleton instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorsController.class); // Logger;

    /**
     * Get current singleton instance of this class.
     * @return - current singleton instance.
     */
    public static ErrorsController getInstance() {
        if(ErrorsController.INSTANCE == null) ErrorsController.INSTANCE = new ErrorsController();

        LOGGER.debug(String.format("Get current singleton instance of [%s] class;", ErrorsController.class.getName()));
        return ErrorsController.INSTANCE;
    }

    /**
     * Log error to console "out" and "err" streams and close application with exit code which same as error code.
     * @param anError - error instance.
     */
    public void logErrorAndExit(ApplicationError anError) {
        String errorMessage = String.format("Application error: [errorCode: %d, message: [%s], originalMessage: [%s]].",
                anError.getErrorCode(), anError.getSimpleExceptionMessage(), anError.getOriginalExceptionMessage());
        System.out.println(errorMessage);
        System.err.println(errorMessage);
        // Close application:
        PropperGui.getInstance().close(anError.getErrorCode());
    }

    public void showGlobalErrorDialogAndExit(ApplicationError anError, boolean isPrintStacktrace) {

        // Show ExceptionDialog dialog:
        if (anError.getExceptionDialog() != null) {
            ExceptionDialog dialog = anError.getExceptionDialog();
            ApplicationDialogs.exceptionDialog(
                    dialog.getTitle(),
                    dialog.getHeaderText(),
                    dialog.getContentText(),
                    isPrintStacktrace,
                    dialog.getOriginalException()
            ).showAndWait();
        }

        // Close program:
        PropperGui.getInstance().close(anError.getErrorCode());
    }

    /**
     * Do something on application error.
     * @param event - {@link Observable} instance event.
     * @param arguments - event arguments (1-st is {@link ApplicationError} instance).
     */
    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {
        // Parse:
        ApplicationError applicationError = ((ApplicationError) arguments[0]);

        // Log error:
        LOGGER.error(String.format("Application error: [code: %d, error: [errorCode: %d, message: [%s], originalExceptionMessage: [%s]]].",
                event.getCode(), applicationError.getErrorCode(), applicationError.getSimpleExceptionMessage(), Optional.of(applicationError.getOriginalExceptionMessage()).orElse("")));
    }

}
