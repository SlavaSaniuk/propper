package me.saniukvyacheslav.gui.events;

/**
 * Observer interface used to specify that instance of class is observer and should be subscribed
 * on {@link Observable) observable instances.
 */
public interface Observer {

    /**
     * Do something on event.
     * @param event - {@link Observable} instance event.
     * @param arguments - event arguments.
     */
    void update(PropperApplicationEvent event, Object... arguments);
}
