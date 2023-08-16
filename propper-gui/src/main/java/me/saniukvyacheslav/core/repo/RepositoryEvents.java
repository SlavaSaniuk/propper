package me.saniukvyacheslav.core.repo;

import me.saniukvyacheslav.gui.events.PropperApplicationEvent;

public enum RepositoryEvents implements PropperApplicationEvent {

    /**
     * Repository was opened event.
     * Arguments array: 1-st element {@link RepositoryTypes} type, 2-nd {@link Object} link on repository (file, connection).
     */
    REPOSITORY_OPENED(550);

    private final int eventCode; // Event code;

    /**
     * Construct new event.
     * @param aCode - event code.
     */
    RepositoryEvents(int aCode) {
        this.eventCode = aCode;
    }

    /**
     * Get application event code.
     * @return - event code.
     */
    @Override
    public int getCode() {
        return this.eventCode;
    }
}
