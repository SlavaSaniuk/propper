package me.saniukvyacheslav.core.controller;

import me.saniukvyacheslav.core.error.ApplicationError;
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
public class ErrorsController implements Observer {

    // Class variables:
    private static ErrorsController INSTANCE; // Singleton instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorsController.class); // Logger;

    /*
        Private default constructor.
     */
    private ErrorsController() {
        LOGGER.debug(String.format("Create singleton instance of [%s] class;", ErrorsController.class.getName()));
    }

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
