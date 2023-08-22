package me.saniukvyacheslav.core.property;

import lombok.Getter;
import me.saniukvyacheslav.prop.Property;
import me.saniukvyacheslav.prop.PropertyWrapper;

/**
 * This POJO class used in application as base class for working with properties in repository and in GUI representation.
 */
public class ExtendedBaseProperty {

    @Getter private final long propertyId; // Property id;
    @Getter private final Property originalProperty; // Original property;
    @Getter private final Property actualProperty; // Actual property;

    /**
     * Construct new instance.
     * Check original property, generate id, and copy original to actual.
     * @param anOriginalProperty - original property instance.
     */
    public ExtendedBaseProperty(Property anOriginalProperty) {
        // Check property:
        PropertyWrapper.checkProperty(anOriginalProperty);
        this.originalProperty = anOriginalProperty;

        // Generate ID:
        this.propertyId = ExtendedBasePropertiesHelper.generateId(1000000L, 10000000L);
        // Copy original to actual:
        this.actualProperty = ExtendedBaseProperty.copy(anOriginalProperty);
    }

    /**
     * Get original property key.
     * @return - original property key.
     */
    public String getOriginalKey() {
        return this.originalProperty.getPropertyKey();
    }

    /**
     * Copy source property.
     * @param aSrcProperty - source property.
     * @return - copied instance of source property.
     */
    public static Property copy(Property aSrcProperty) {
        // Check property:
        PropertyWrapper.checkProperty(aSrcProperty);

        // Construct new property:
        return new Property(String.valueOf(aSrcProperty.getPropertyKey()), String.valueOf(aSrcProperty.getPropertyValue()));
    }

}
