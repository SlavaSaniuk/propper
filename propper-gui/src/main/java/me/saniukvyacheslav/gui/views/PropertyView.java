package me.saniukvyacheslav.gui.views;


import me.saniukvyacheslav.gui.controllers.props.PropertyChangesController;
import me.saniukvyacheslav.gui.controllers.props.PropertyEvents;
import me.saniukvyacheslav.gui.events.Observer;
import me.saniukvyacheslav.gui.events.PropperApplicationEvent;
import me.saniukvyacheslav.gui.models.PropertyModel;
import me.saniukvyacheslav.gui.nodes.PropertyField;

/**
 * View for single {@link PropertyModel} model.
 * This view implements {@link Observer} interface and it subscribed on {@link PropertyChangesController} controller.
 */
public class PropertyView implements Stylable, Observer {

    private final PropertyField propertyKeyField; // Property key text field;
    private final PropertyField propertyValueField; // Property value text field;

    /**
     * Construct new view for specified {@link PropertyModel} model.
     * @param aModel - property model.
     */
    public PropertyView(PropertyModel aModel) {
        this.propertyKeyField = aModel.getKeyPropertyField();
        this.propertyValueField = aModel.getValuePropertyField();
    }

    /**
     * Stylize embedded property fields.
     * Add "property-cell-common" css class.
     */
    @Override
    public void stylize() {

        // Add classes:
        this.propertyKeyField.getStyleClass().addAll(
                "property-cell-common"
        );
        this.propertyValueField.getStyleClass().addAll(
                "property-cell-common"
        );
    }

    /**
     * Set/unset "updated" css pseudo class to property key field.
     * @param isUpdated - "updated" css pseudo class flag.
     */
    public void setUpdatedPropertyKey(boolean isUpdated) {
        this.propertyKeyField.setUpdated(isUpdated);
    }

    /**
     * Set/unset "updated" css pseudo class to property value field.
     * @param isUpdated - "updated" css pseudo class flag.
     */
    public void setUpdatedPropertyValue(boolean isUpdated) {
        this.propertyValueField.setUpdated(isUpdated);
    }

    /**
     * Update property field based on {@link PropertyEvents} events.
     * This view listens {@link PropertyChangesController} controller on property changes events.
     * Note: The first object in arguments array must be property key, to detect if changes link with current view instance.
     * @param event - property changes event.
     * @param arguments - array of arguments.
     */
    @Override
    public void update(PropperApplicationEvent event, Object... arguments) {

        // Check if one of these fields was updating:
        String keyOfUpdatedProperty = (String) arguments[0];
        if (!(this.propertyKeyField.getPropertyKey().equals(keyOfUpdatedProperty)))
            return;

        switch (event.getCode()) {
            case 201: // KEY_UPDATE_EVENT event:
                this.onPropertyKeyUpdateEvent();
                break;
            case 202: // VALUE_UPDATE_EVENT event:
                this.onPropertyValueUpdateEvent();
                break;
            case 203: // ABORT_KEY_UPDATE_EVENT event:
                this.onAbortPropertyKeyUpdateEvent();
                break;
            case 204: // ABORT_VALUE_UPDATE_EVENT event:
                this.onAbortPropertyValueUpdateEvent();
                break;
        }
    }

    /**
     * Set "updated" css pseudo class to property field.
     */
    public void onPropertyKeyUpdateEvent() {
        this.setUpdatedPropertyKey(true);
    }

    /**
     * Set "updated" css pseudo class to property value field.
     */
    public void onPropertyValueUpdateEvent() {
        this.setUpdatedPropertyValue(true);
    }

    /**
     * Unset "updated" css pseudo class to property key field.
     */
    public void onAbortPropertyKeyUpdateEvent() {
        this.setUpdatedPropertyKey(false);
    }

    /**
     * Unset "updated" css pseudo class to property value field.
     */
    public void onAbortPropertyValueUpdateEvent() {
        this.setUpdatedPropertyValue(false);
    }

}
