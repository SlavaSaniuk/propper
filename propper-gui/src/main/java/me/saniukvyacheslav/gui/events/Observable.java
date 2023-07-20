package me.saniukvyacheslav.gui.events;

public interface Observable {

    void subscribe(Observer anObserver, PropperApplicationEvent... anApplicationEvents);

    void unsubscribe(Observer anObserver);

    void notify(PropperApplicationEvent anApplicationEvent, Object... anArguments);
}
