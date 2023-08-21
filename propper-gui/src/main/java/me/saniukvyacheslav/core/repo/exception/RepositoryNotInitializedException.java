package me.saniukvyacheslav.core.repo.exception;

import lombok.Getter;
import me.saniukvyacheslav.core.repo.RepositoryTypes;

import java.io.IOException;

/**
 * This exception throws in cases, when application cannot initialize {@link me.saniukvyacheslav.core.repo.PropertiesRepository}
 * repository instance, or when developer try to call methods on not initialized repository.
 */
public class RepositoryNotInitializedException extends IOException {

    @Getter private String exceptionMessage; // Exception message;

    /**
     * Construct new exception instance with origin caused exception message.
     * Exception message will be the same as caused exception message.
     * @param anOriginExceptionMessage - original exception message.
     */
    public RepositoryNotInitializedException(String anOriginExceptionMessage) {
        super(anOriginExceptionMessage);
        this.exceptionMessage = anOriginExceptionMessage;
    }

    /**
     * Construct new exception instance with same original and exception messages:
     * "Repository[type: REPOSITORY_TYPE, object: REPOSITORY_OBJECT] is not initialized".
     * @param aRepositoryType - repository type.
     * @param aRepositoryObject - repository object.
     */
    public RepositoryNotInitializedException(RepositoryTypes aRepositoryType, Object aRepositoryObject) {
        this(String.format("Repository[type: %s, object: %s] is not initialized", aRepositoryType, aRepositoryObject));
        this.exceptionMessage = super.getMessage();
    }

    /**
     * Construct new exception instance with different original and exception messages.
     * Exception message will be: "Repository[type: REPOSITORY_TYPE, object: REPOSITORY_OBJECT] is not initialized.
     * Origin exception message: [ORIGINAL_EXCEPTION_MESSAGE];".
     * @param aRepositoryType - repository type.
     * @param aRepositoryObject - repository object.
     */
    public RepositoryNotInitializedException(RepositoryTypes aRepositoryType, Object aRepositoryObject, String anOriginExceptionMessage) {
        this(anOriginExceptionMessage);
        this.exceptionMessage = String.format("Repository[type: %s, object: %s] is not initialized. Origin exception message: [%s];", aRepositoryType, aRepositoryObject, anOriginExceptionMessage);
    }

}
