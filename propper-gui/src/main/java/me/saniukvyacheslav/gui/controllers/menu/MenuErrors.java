package me.saniukvyacheslav.gui.controllers.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;

@AllArgsConstructor
public enum MenuErrors implements PropperApplicationEvent {

    /**
     * File create process error.
     */
    FILE_CREATION_ERROR(151),
    /**
     * File rewrite process error.
     */
    FILE_REWRITING_ERROR(152);

    @Getter private final int code;
}
