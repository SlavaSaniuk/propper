package me.saniukvyacheslav.gui.controllers.menu.events;

import me.saniukvyacheslav.gui.events.PropperApplicationEvent;

/**
 * "File" top menu application events.
 */
public enum FileMenuEvents implements PropperApplicationEvent {


    /**
     * Open file application event. Event calls when user open properties file.
     */
    OPEN_FILE_EVENT(102),
    SAVE_FILE_EVENT(103),

    /**
     * Close file application event. Event will be triggered, when user click on "Close" FileMenu item.
     */
    CLOSE_FILE_EVENT(105);

    private final int eventCode; // Event code;

    /**
     * Construct new FileMenu application event with specified code.
     * @param aEventCode - event code.
     */
    FileMenuEvents(int aEventCode) {
        this.eventCode = aEventCode;
    }

    @Override
    public int getCode() {
        return this.eventCode;
    }

}
