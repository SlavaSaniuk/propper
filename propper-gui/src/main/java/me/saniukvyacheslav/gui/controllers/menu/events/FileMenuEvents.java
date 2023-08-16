package me.saniukvyacheslav.gui.controllers.menu.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;

/**
 * "File" TopMenu application events.
 */
@AllArgsConstructor
public enum FileMenuEvents implements PropperApplicationEvent {


    /**
     * Open file application event. Event calls when user open properties file.
     */
    OPEN_FILE_EVENT(102),

    /**
     * SAVE FILE FileMenu application event.
     */
    SAVE_FILE_EVENT(103),

    /**
     * Close file application event. Event will be triggered, when user click on "Close" FileMenu item.
     */
    CLOSE_FILE_EVENT(105);

    @Getter private final int code; // Event code;

}
