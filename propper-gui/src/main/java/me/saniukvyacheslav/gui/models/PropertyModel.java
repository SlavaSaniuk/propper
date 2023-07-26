package me.saniukvyacheslav.gui.models;

import lombok.Getter;
import me.saniukvyacheslav.gui.controllers.props.PropertyChangesController;
import me.saniukvyacheslav.gui.controllers.props.PropertyEvents;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.nodes.PropertyField;
import me.saniukvyacheslav.gui.views.PropertyView;
import me.saniukvyacheslav.prop.Property;

import java.util.Objects;

/**
 * Property model is a model for {@link Property} instance.
 */
public class PropertyModel implements Observer {

    // Class variables:
    @Getter private final Property property; // Origin property;
    @Getter private final PropertyField keyPropertyField; // Property key text field;
    @Getter private final PropertyField valuePropertyField; // Property value text field;
    @Getter private final PropertyView propertyView; // View instance;

    /**
     * Construct new model for specified property.
     * @param aProperty - origin property.
     */
    public PropertyModel(Property aProperty) {
        Objects.requireNonNull(aProperty);

        // Map parameters:
        this.property = aProperty;

        // Initialize property fields:
        this.keyPropertyField = PropertyField.keyField(aProperty);
        this.valuePropertyField = PropertyField.valueField(aProperty);

        // Initialize view and stylize property fields.
        this.propertyView = new PropertyView(this);
        this.propertyView.stylize();

        // Add listeners:
        // Listener on changes of property key field:
        this.keyPropertyField.setOnKeyReleased(PropertyChangesController.getInstance().getKeyReleasedHandler());
        // Listener on changes of property value field:
        this.valuePropertyField.setOnKeyReleased(PropertyChangesController.getInstance().getKeyReleasedHandler());

        // Subscribe this model on property changes controller:
        PropertyChangesController.getInstance().subscribe(this, PropertyEvents.KEY_UPDATE_EVENT, PropertyEvents.VALUE_UPDATE_EVENT);
    }

    /**
     * Update property model based on {@link PropertyEvents} events.
     * This model listens {@link PropertyChangesController} controller on property changes events.
     * Note: The first object in arguments array must be property key, to detect if changes link with current model instance.
     * @param event - property changes event.
     * @param arguments - array of arguments.
     */
    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {

        // Check if this model instance was updating:
        String keyOfUpdatedProperty = (String) arguments[0];
        if (!(this.property.getPropertyKey().equals(keyOfUpdatedProperty)))
            return;

        switch (event.getCode()) {
            case 201: // KEY_UPDATE_EVENT event:
                this.onPropertyKeyUpdateEvent();
                break;
            case 202: // VALUE_UPDATE_EVENT event:
                this.onPropertyValueUpdateEvent();
                break;
        }
    }

    /**
     * Set "updated" css pseudo class to property fields.
     * If property key was updated, that's why all property fields must have "updated" css pseudo class.
     */
    public void onPropertyKeyUpdateEvent() {
        this.propertyView.setUpdatedWholeProperty(true);
    }

    /**
     * Set "updated" css pseudo class to property value field.
     */
    public void onPropertyValueUpdateEvent() {
        this.propertyView.setUpdatedPropertyValue(true);
    }

}
