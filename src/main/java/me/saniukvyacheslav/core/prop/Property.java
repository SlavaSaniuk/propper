package me.saniukvyacheslav.core.prop;

import lombok.Getter;
import lombok.Setter;

/**
 * Property object used to store key-value pair of Strings.
 */
public class Property {

    @Getter @Setter
    private String propertyKey;
    @Getter @Setter
    private String propertyValue;

    /**
     * Construct new property object with specified key-value pair.
     * @param aKey - property key.
     * @param aValue - property value.
     */
    public Property(String aKey, String aValue) {
        // Check property key:
        if (aKey == null) throw new NullPointerException("Property key must be not null.");
        if (aKey.isEmpty()) throw new IllegalArgumentException("Property key must be not empty.");
        this.propertyKey = aKey;

        // Property value:
        if (aValue == null) this.propertyValue = "";
        else this.propertyValue = aValue;
    }

}
