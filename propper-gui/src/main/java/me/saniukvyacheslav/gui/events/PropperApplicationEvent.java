package me.saniukvyacheslav.gui.events;

/**
 * Application events basic contract.
 * All application events must implement this interface.
 */
public interface PropperApplicationEvent {

    /**
     * Get application event code.
     * @return - event code.
     */
    int getCode();
}
