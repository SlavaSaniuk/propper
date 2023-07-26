package me.saniukvyacheslav.gui.controllers.props;

import me.saniukvyacheslav.gui.events.PropperApplicationEvent;

/**
 * Properties events.
 * These events are calling, when user try to change all properties list (create, delete) or single property (update).
 */
public enum PropertyEvents implements PropperApplicationEvent {

    /**
     * KEY_UPDATE_EVENT is calling, when user updates property key.
     */
    KEY_UPDATE_EVENT(201),
    /**
     * VALUE_UPDATE_EVENT is calling, when user updates property value.
     */
    VALUE_UPDATE_EVENT(202),
    /**
     * ABORT_KEY_UPDATE_EVENT is calling, when user cancels update property key.
     */
    ABORT_KEY_UPDATE_EVENT (203),
    /**
     * ABORT_VALUE_UPDATE_EVENT is calling, when user cancels update property value.
     */
    ABORT_VALUE_UPDATE_EVENT (204),
    /**
     * PROPERTY_UPDATE_EVENT is calling, when user updates all property (key and value).
     */
    PROPERTY_UPDATE_EVENT (205);

    /**
     * Event code.
     */
    private final int code;

    /**
     * Construct new property event.
     * @param aCode - event code.
     */
    PropertyEvents(int aCode) {
        this.code = aCode;
    }

    @Override
    public int getCode() {
        return this.code;
    }



}
