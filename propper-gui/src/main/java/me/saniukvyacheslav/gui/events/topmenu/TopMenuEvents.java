package me.saniukvyacheslav.gui.events.topmenu;

import me.saniukvyacheslav.gui.events.PropperApplicationEvent;

public enum TopMenuEvents implements PropperApplicationEvent {

    OPEN_FILE (101),
    CLOSE_APPLICATION (102);

    private final int eventCode;

    TopMenuEvents(int anEventCode) {
        this.eventCode = anEventCode;
    }
    @Override
    public int getCode() {
        return this.eventCode;
    }
}
