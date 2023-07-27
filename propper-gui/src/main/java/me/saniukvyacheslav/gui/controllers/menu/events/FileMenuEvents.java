package me.saniukvyacheslav.gui.controllers.menu.events;

import me.saniukvyacheslav.gui.events.PropperApplicationEvent;

public enum FileMenuEvents implements PropperApplicationEvent {

    /**
     * Open file application event. Event calls when user open properties file.
     */
    OPEN_FILE_EVENT(102),
    SAVE_FILE_EVENT(103),
    CLOSE_FILE_EVENT(105);

    private final int eventCode;
    FileMenuEvents(int aEventCode) {
        this.eventCode = aEventCode;
    }

    @Override
    public int getCode() {
        return this.eventCode;
    }

}
