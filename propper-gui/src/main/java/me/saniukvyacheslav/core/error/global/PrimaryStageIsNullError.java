package me.saniukvyacheslav.core.error.global;

import javafx.stage.Stage;
import me.saniukvyacheslav.core.error.ApplicationError;
import me.saniukvyacheslav.core.error.GlobalError;

/**
 * This exception instance used in cases when developer start application
 * via {@link me.saniukvyacheslav.core.PropperGui#start(Stage)} method and didn't specify {@link Stage} parameter.
 */
public class PrimaryStageIsNullError extends ApplicationError {

    /**
     * Construct new instance.
     * @param e - original exception instance.
     */
    public PrimaryStageIsNullError(NullPointerException e) {
        super(GlobalError.PRIMARY_STAGE_IS_NULL_ERROR.getErrorCode(), "Primary stage instance is [NULL]");
        super.setOriginalExceptionMessage(e.getMessage());
    }
}
