package me.saniukvyacheslav.gui.nodes;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.TextField;
import lombok.Getter;
import me.saniukvyacheslav.prop.Property;

/**
 * Property field extends {@link TextField} node for property key and value.
 * Property field has [propertyKey] field, which links this property field with origin property.
 * Also, property field has [isKeyPropertyField] boolean flag, which indicates: this property field for property key.
 */
public class PropertyField extends TextField {

    // Class variables:
    @Getter private final String propertyKey; // Property key;
    @Getter private String defaultValue; // Property default value and field default text;
    @Getter private boolean isKeyPropertyField; // Key property field flag;
    private final BooleanProperty updated = new SimpleBooleanProperty(false); // CSS Pseudo-class;

    /**
     * Create empty property field. Default value for this field will be empty.
     * @param aPropertyKey - origin property key.
     */
    private PropertyField(String aPropertyKey) {
        super();
        this.propertyKey = aPropertyKey;
        this.defaultValue = "";

        // Add default classes:
        super.getStyleClass().add("property-field");

        // Add listeners:
        // On css pseudo-classes updated:
        this.updated.addListener(e -> pseudoClassStateChanged(PseudoClass.getPseudoClass("updated"), this.isUpdated()));
    }

    /**
     * Create property field with default value and field text.
     * @param aPropertyKey - origin property key.
     * @param aDefaultValue - default value and field text.
     */
    private PropertyField(String aPropertyKey, String aDefaultValue) {
        this(aPropertyKey);
        this.defaultValue = aDefaultValue;
        super.setText(this.defaultValue);
    }

    /**
     * Create property field for origin property key.
     * @param aProperty - origin property.
     * @return - property key field with default value and field text.
     */
    public static PropertyField keyField(Property aProperty) {
        PropertyField keyField = new PropertyField(aProperty.getPropertyKey(), aProperty.getPropertyKey());
        keyField.isKeyPropertyField = true;
        return keyField;
    }

    /**
     * Create property field for origin property value.
     * @param aProperty - origin property.
     * @return - property value field with default value and field text.
     */
    public static PropertyField valueField(Property aProperty) {
        PropertyField keyField = new PropertyField(aProperty.getPropertyKey(), aProperty.getPropertyValue());
        keyField.isKeyPropertyField = false;
        return keyField;
    }

    /**
     * Check if property field has "updated" css pseudo class.
     * @return - true, if field has "updated" css pseudo class.
     */
    public boolean isUpdated() {
        return this.updated.get();
    }

    /**
     * Set/unset "updated" css pseudo class based on specified boolean value.
     * @param isUpdated - "updated" css pseudo class flag.
     */
    public void setUpdated(boolean isUpdated) {
        this.updated.set(isUpdated);
    }


}
