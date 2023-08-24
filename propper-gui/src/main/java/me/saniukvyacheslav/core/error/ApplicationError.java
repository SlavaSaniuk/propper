package me.saniukvyacheslav.core.error;

import lombok.Getter;
import lombok.Setter;

/**
 * ApplicationError is POJO which describe exception situation when program is executing.
 * Used to store information about exception.
 */
public class ApplicationError {

    @Getter private final int errorCode; // Exception code;
    @Getter @Setter private String originalExceptionMessage; // Original exception message;
    @Getter @Setter private String simpleExceptionMessage; // Simple exception message;
    @Getter @Setter private ExceptionDialog exceptionDialog;

    /**
     * Construct new instance with specified code and simple user message.
     * @param aCode - error code.
     * @param aSimpleErrorMessage - simple string message for user.
     */
    public ApplicationError(int aCode, String aSimpleErrorMessage) {
        this.errorCode = aCode;
        this.simpleExceptionMessage = aSimpleErrorMessage;
    }



}
