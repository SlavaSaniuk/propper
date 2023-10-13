package me.saniukvyacheslav.gui.controllers.props;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.Logger;
import me.saniukvyacheslav.annotation.pattern.Singleton;
import me.saniukvyacheslav.core.logging.PropperLoggingConfiguration;
import me.saniukvyacheslav.core.util.PropertiesList;
import me.saniukvyacheslav.gui.GuiConfiguration;
import me.saniukvyacheslav.gui.events.Observable;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.nodes.PropertyField;
import me.saniukvyacheslav.prop.Property;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This controller handle property changes.
 * Singleton instance.
 */
@Singleton
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertyChangesController implements Observable {

    // Class variables:
    private static final Logger LOGGER = PropperLoggingConfiguration.getLogger(PropertyChangesController.class); // Logger;
    private static PropertyChangesController INSTANCE; // Singleton instance;
    private final Map<Observer, PropperApplicationEvent[]> subscribers = new HashMap<>(); // List of controller subscribers;
    private final Map<String, String> keyUpdatedProperties = new HashMap<>(); // Map of propertyKey=updatedPropertyKey;
    private final Map<String, String> valueUpdatedProperties = new HashMap<>(); // Map of propertyKey=updatedPropertyValue;
    @Getter private final PropertyChangesHandler keyReleasedHandler = new PropertyChangesHandler(); // Properties changes handler;

    /**
     * Get current singleton instance.
     * @return - current instance.
     */
    public static PropertyChangesController getInstance() {
        if (PropertyChangesController.INSTANCE == null) PropertyChangesController.INSTANCE = new PropertyChangesController();
        return PropertyChangesController.INSTANCE;
    }

    /**
     * Get updates map (origin_property_key = new_property_key_value).
     * @return - updates map.
     */
    public Map<String, Property> getUpdatesMap() {
        Map<String, Property> updatesMap = new HashMap<>(); // Updates map;

        // Add properties, where key was updated:
        this.keyUpdatedProperties.forEach((originPropertyKey, newPropertyKey) ->
            updatesMap.put(originPropertyKey, new Property(newPropertyKey, ""))

        );

        // Add properties, where value was updating:
        this.valueUpdatedProperties.forEach((originKey, newPropertyValue) -> {
            if (updatesMap.containsKey(originKey)) updatesMap.get(originKey).setPropertyValue(newPropertyValue);
            else updatesMap.put(originKey, new Property(originKey, newPropertyValue));
        });

        return updatesMap;
    }

    /**
     *  Get map of updated properties (origin_property_key = updated_property)
     * @return - map of updated properties.
     */
    public Map<String, Property> getUpdates(List<Property> originPropertiesList) {
        LOGGER.trace("Get updates map:");

        // Result map:
        Map< String, Property> resultMap = new HashMap<>();

        // Create PropertiesList:
        PropertiesList propertiesList = PropertiesList.ofList(originPropertiesList);

        // Add properties where the key has been updated:
        this.keyUpdatedProperties.forEach((originKey, updatedKey) -> {
            if (propertiesList.getByKey(originKey) != null)
                resultMap.put(originKey, new Property(updatedKey, propertiesList.getByKey(originKey).getPropertyValue()));
        });

        // Add properties where the value has been updated:
        this.valueUpdatedProperties.forEach((originKey, updatedValue) -> {
            if(propertiesList.getByKey(originKey) != null) {
                if (resultMap.containsKey(originKey))
                    resultMap.get(originKey).setPropertyValue(updatedValue);
                else resultMap.put(originKey, new Property(originKey, updatedValue));
            }
        });

        return resultMap;
    }

    /**
     * Get properties updates count.
     * @return - updates count.
     */
    public int getUpdatesCount() {
        return this.getUpdatesMap().size();
    }

    /**
     * Clear all properties changes in store (these key/value maps).
     */
    public void clear() {
        // Clear changes maps:
        this.keyUpdatedProperties.clear();
        this.valueUpdatedProperties.clear();
    }

    /**
     * Notify observers about PROPERTY_UPDATE_EVENT.
     * Method also set "unsaved" state in PropertiesTable model.
     */
    public void notifyAboutPropertyUpdateEvent() {

        // Set isUnsaved state in properties table model:
        GuiConfiguration.getInstance().getPropertiesTableController().tableModel().setUnsavedState(this.getUpdatesCount() != 0);

        // Notify observers about PROPERTY_UPDATE_EVENT:
        this.notify(PropertyEvents.PROPERTY_UPDATE_EVENT, this.getUpdatesCount());

    }

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

    /**
     * Handler of property text changes for {@link PropertyField} fields.
     */
    private static class PropertyChangesHandler implements EventHandler<KeyEvent> {

        /**
         * Check if property changes already in store (Map of keys or values changes), and if it not in store, then put it, and notify observers.
         * @param aPropertyField - property field;
         */
        private void ifFieldWasChanged(PropertyField aPropertyField) {
            // Check if property changes already in map:
            String oldValue;
            if (aPropertyField.isKeyPropertyField()) oldValue = PropertyChangesController.getInstance().keyUpdatedProperties.put(aPropertyField.getPropertyKey(), aPropertyField.getText());
            else oldValue = PropertyChangesController.getInstance().valueUpdatedProperties.put(aPropertyField.getPropertyKey(), aPropertyField.getText());

            // If not added, then notify observers:
            if (oldValue == null) {
                if (aPropertyField.isKeyPropertyField())
                    PropertyChangesController.getInstance().notify(PropertyEvents.KEY_UPDATE_EVENT, aPropertyField.getPropertyKey());
                else PropertyChangesController.getInstance().notify(PropertyEvents.VALUE_UPDATE_EVENT, aPropertyField.getPropertyKey());
                PropertyChangesController.getInstance().notifyAboutPropertyUpdateEvent();
            }
        }

        /**
         * Check if property changes already in store (Map of keys or values changes), and if it in store, then remove it, and notify observers.
         * @param aPropertyField - property field;
         */
        private void ifFieldWasNotChanged(PropertyField aPropertyField) {
            // Check if property changes already in map:
            String oldValue;
            if (aPropertyField.isKeyPropertyField()) oldValue = PropertyChangesController.getInstance().keyUpdatedProperties.remove(aPropertyField.getPropertyKey());
            else oldValue = PropertyChangesController.getInstance().valueUpdatedProperties.remove(aPropertyField.getPropertyKey());
            // If property in list, then notify observers:
            if (oldValue != null) {
                if (aPropertyField.isKeyPropertyField())
                    PropertyChangesController.getInstance().notify(PropertyEvents.ABORT_KEY_UPDATE_EVENT, aPropertyField.getPropertyKey());
                else PropertyChangesController.getInstance().notify(PropertyEvents.ABORT_VALUE_UPDATE_EVENT, aPropertyField.getPropertyKey());
                PropertyChangesController.getInstance().notifyAboutPropertyUpdateEvent();
            }
        }

        @Override
        public void handle(KeyEvent event) {
            // Get source property field:
            PropertyField propertyField = ((PropertyField) event.getSource());
            // Check if field text is changed:
            if (!(propertyField.getDefaultValue().equals(propertyField.getText())))
                this.ifFieldWasChanged(propertyField);
            else this.ifFieldWasNotChanged(propertyField);
        }
    }

}
