package me.saniukvyacheslav.core.exception;

import lombok.Getter;

/**
 * This checked exception used to wrap original exceptions which throws when some components are initializing.
 */
public class InitializationException extends Exception {

    @Getter private final Exception originalExceptionInstance; // Original Exception instance.
    @Getter private Class<?> initializationClass; // Component class, which throw exception.

    /**
     * Construct new exception instance only with original exception instance.
     * @param aOriginalException - original exception instance.
     */
    public InitializationException(Exception aOriginalException) {
        super(aOriginalException);
        this.originalExceptionInstance = aOriginalException;
    }

    /**
     * Construct new exception instance with all arguments.
     * @param anOriginalException - original exception instance.
     * @param anInitializationClass - component class which threw exception.
     */
    public InitializationException(Exception anOriginalException, Class<?> anInitializationClass) {
        this(anOriginalException);
        this.initializationClass = anInitializationClass;
    }



}
