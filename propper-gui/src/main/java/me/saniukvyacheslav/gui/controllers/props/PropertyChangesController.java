package me.saniukvyacheslav.gui.controllers.props;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import me.saniukvyacheslav.gui.events.Observable;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.nodes.PropertyField;

import java.util.HashMap;
import java.util.Map;

public class PropertyChangesController implements Observable {

    // Class variables:
    private static PropertyChangesController INSTANCE; // Singleton instance;
    private final Map<Observer, PropperApplicationEvent[]> subscribers = new HashMap<>(); // This controller subscribers;
    private final Map<PropertyField, String> keyUpdatedProperties = new HashMap<>();

    /**
     * Get current singleton instance.
     * @return - current instance.
     */
    public static PropertyChangesController getInstance() {
        if (PropertyChangesController.INSTANCE == null)
            PropertyChangesController.INSTANCE = new PropertyChangesController();
        return PropertyChangesController.INSTANCE;
    }

    private PropertyChangesController() {}

    @Override
    public void subscribe(Observer anObserver, PropperApplicationEvent... anApplicationEvents) {
        this.subscribers.put(anObserver, anApplicationEvents);
    }

    @Override
    public void unsubscribe(Observer anObserver) {
        this.subscribers.remove(anObserver);
    }

    @Override
    public void notify(PropperApplicationEvent anApplicationEvent, Object... anArguments) {
        // Iterate through subscribers:
        this.subscribers.forEach((subscriber, actionEvents) -> {

            // Iterate through supported event array of current subscriber:
            for (PropperApplicationEvent event: actionEvents) {
                if (event == anApplicationEvent) subscriber.update(anApplicationEvent, anArguments);
            }
        });
    }

    public EventHandler<KeyEvent> getKeyReleasedHandler() {
        return this.keyReleasedHandler;
    }

    private final EventHandler<KeyEvent> keyReleasedHandler = event -> {
        // Get source:
        PropertyField propertyField = ((PropertyField) event.getSource());

        // Check if PropertyField is key field:
        if (propertyField.isKeyPropertyField()) {
            // Add or remove in KeyUpdatedProperties list:
            if (!(propertyField.getDefaultValue().equals(propertyField.getText()))) {
                String oldValue = this.keyUpdatedProperties.put(propertyField, propertyField.getText());
                // Notify observers:
                if (oldValue == null)
                    this.notify(PropertyEvents.KEY_UPDATE_EVENT, propertyField.getPropertyKey());
            } else {
                this.keyUpdatedProperties.remove(propertyField);
            }

        }

        System.out.println(this.keyUpdatedProperties.size());


    };

}
