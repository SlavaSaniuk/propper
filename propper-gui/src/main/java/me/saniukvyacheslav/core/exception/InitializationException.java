package me.saniukvyacheslav.core.exception;

import lombok.Getter;

public class InitializationException extends Exception {

    @Getter private final Exception originalExceptionInstance;

    public InitializationException(Exception aOriginalException) {
        super(aOriginalException);
        this.originalExceptionInstance = aOriginalException;
    }

}
