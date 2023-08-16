package me.saniukvyacheslav.core.repo;

import me.saniukvyacheslav.gui.events.PropperApplicationEvent;

/**
 * This enum define common event of repository error.
 */
public enum RepositoryErrors implements PropperApplicationEvent {

    /**
     * Repository type not supported.
     * This error throws in cases, when user try to open not supported repository.
     * {@link  me.saniukvyacheslav.core.controller.RepositoryController#open(Object...)}.
     */
    REPOSITORY_TYPE_NOT_SUPPORTED(500),

    /**
     * Repository cannot be opened.
     * This error throws in cases, when user try to open invalid file.
     * {@see RepositoryController#initializeFileRepository(File)}.
     */
    REPOSITORY_OPENING_ERROR(501);

    private final int code; // Error code;

    /**
     * Construct new RepositoryErrors error instance.
     * @param aCode - error code.
     */
    RepositoryErrors(int aCode) {
        this.code = aCode;
    }

    /**
     * Get repository error code.
     * @return - error code.
     */
    @Override
    public int getCode() {
        return this.code;
    }
}
