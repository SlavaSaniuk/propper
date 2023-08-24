package me.saniukvyacheslav.core.error.global;

import me.saniukvyacheslav.core.error.ApplicationError;
import me.saniukvyacheslav.core.error.ExceptionDialog;
import me.saniukvyacheslav.core.error.GlobalError;
import me.saniukvyacheslav.core.exception.InitializationException;

public class InitializationApplicationError extends ApplicationError {

    public InitializationApplicationError(InitializationException anInitializationException, Class<?> aBeanClass) {
        super(GlobalError.INITIALIZATION_ERROR.getErrorCode(), "Initialization error.");

        // Create exception dialog:
        ExceptionDialog dialog = new ExceptionDialog();
        dialog.setTitle("Initialization exception");
        dialog.setHeaderText(null);
        dialog.setContentText(String.format("Could not initialize application component [%s].", aBeanClass.getName()));
        dialog.setOriginalException(anInitializationException.getOriginalExceptionInstance());

        super.setExceptionDialog(dialog);
    }

}
