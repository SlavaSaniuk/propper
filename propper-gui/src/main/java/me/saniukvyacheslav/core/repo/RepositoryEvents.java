package me.saniukvyacheslav.core.repo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;

/**
 * Different repository events.
 */
@AllArgsConstructor
public enum RepositoryEvents implements PropperApplicationEvent {

    /**
     * Repository was opened event.
     * Arguments array: 1-st element {@link RepositoryTypes} type, 2-nd {@link Object} link on repository (file, connection).
     */
    REPOSITORY_OPENED(550),
    /**
     * This event calling when changes saved in repository.
     */
    REPOSITORY_CHANGES_SAVED(551),
    /**
     * Repository was closed event.
     */
    REPOSITORY_CLOSED(552);

    @Getter private final int code; // Event code;


}
