package me.saniukvyacheslav.gui.events;

/**
 * Observable interface defines methods for subscribe, unsubscribe and updates {@link Observer} observers.
 */
public interface Observable {

    /**
     * Subscribe observer to this observable instance.
     * @param anObserver - observer.
     * @param anApplicationEvents - observer supported events.
     */
    void subscribe(Observer anObserver, PropperApplicationEvent... anApplicationEvents);

    /**
     * Unsubscribe observer to this observable instance.
     * @param anObserver - observer.
     */
    void unsubscribe(Observer anObserver);

    /**
     * Notify observers of any event.
     * @param anApplicationEvent - any event.
     * @param anArguments - event arguments.
     */
    void notify(PropperApplicationEvent anApplicationEvent, Object... anArguments);
}
