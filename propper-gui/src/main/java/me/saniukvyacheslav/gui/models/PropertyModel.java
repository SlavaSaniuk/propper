package me.saniukvyacheslav.gui.models;

import lombok.Getter;
import me.saniukvyacheslav.gui.controllers.props.PropertyChangesController;
import me.saniukvyacheslav.gui.controllers.props.PropertyEvents;
import me.saniukvyacheslav.gui.nodes.PropertyField;
import me.saniukvyacheslav.gui.views.PropertyView;
import me.saniukvyacheslav.prop.Property;

import java.util.Objects;

/**
 * Property model is a model for {@link Property} instance.
 */
public class PropertyModel {

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

        // Subscribe property view on property changes controller:
        PropertyChangesController.getInstance().subscribe(this.propertyView, PropertyEvents.KEY_UPDATE_EVENT, PropertyEvents.VALUE_UPDATE_EVENT);
    }

}
