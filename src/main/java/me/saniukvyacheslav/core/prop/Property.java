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

    @Override
    public String toString() {
        return String.format("%s=%s", this.propertyKey, this.propertyValue);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) return true; // Check memory links;
        if (!(obj instanceof Property)) return false; // Check obj class;

        Property prop2 = (Property) obj;
        if (!(this.propertyKey.equals(prop2.getPropertyKey()))) return false; // Check property key;
        return this.propertyValue.equals(prop2.getPropertyValue()); // Check property value;
    }
}
