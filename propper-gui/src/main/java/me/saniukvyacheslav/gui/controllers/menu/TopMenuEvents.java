package me.saniukvyacheslav.gui.controllers.menu;

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

    public enum FileMenu implements PropperApplicationEvent {

        OPEN_FILE_EVENT(102),
        SAVE_FILE_EVENT(103),
        CLOSE_FILE_EVENT(105);

        private final int eventCode;
        FileMenu(int aEventCode) {
            this.eventCode = aEventCode;
        }

        @Override
        public int getCode() {
            return 0;
        }
    }
}
