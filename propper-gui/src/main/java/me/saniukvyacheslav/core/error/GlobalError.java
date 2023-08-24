package me.saniukvyacheslav.core.error;

import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Global application errors constants.
 */
@AllArgsConstructor
public enum GlobalError {

    /**
     * "Initialization process" error constant. This constant used in classes which will be initialized.
     */
    INITIALIZATION_ERROR(901),

    /**
     * "Primary stage is null" error constant. See {@link me.saniukvyacheslav.core.PropperGui#start(Stage)} method.
     */
    PRIMARY_STAGE_IS_NULL_ERROR(902),

    /**
     * JavaFx internal error.
     */
    JAVA_FX_ERROR(903);

    /**
     * Get global error code.
     */
    @Getter private final int errorCode;

}
