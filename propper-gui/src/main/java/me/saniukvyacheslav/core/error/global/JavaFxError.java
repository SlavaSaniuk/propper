package me.saniukvyacheslav.core.error.global;

import me.saniukvyacheslav.core.error.ApplicationError;
import me.saniukvyacheslav.core.error.GlobalError;

/**
 * JavaFx internal application error.
 */
public class JavaFxError extends ApplicationError {

    /**
     * Construct new instance.
     * @param anOriginalException - original exception instance.
     */
    public JavaFxError(Exception anOriginalException) {
        super(GlobalError.JAVA_FX_ERROR.getErrorCode(), "JavaFx internal error.");
        super.setOriginalExceptionMessage(anOriginalException.getMessage());
    }

}
