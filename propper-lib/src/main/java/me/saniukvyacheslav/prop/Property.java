package me.saniukvyacheslav.prop;

import lombok.Getter;
import lombok.Setter;
import me.saniukvyacheslav.exceptions.PropertyIsInvalidException;

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

    /**
     * Parse property from specified string.
     * Property must be in valid format [property.key=property.value].
     * If specified string start with ! or @, method return null.
     * If specified string doesn't have "=" sign, method return null.
     * @param propertyString - string to parse;
     * @return - parsed property instance.
     * @throws PropertyIsInvalidException - if property without key.
     */
    public static Property parse(String propertyString) throws PropertyIsInvalidException {
        // Check String to parse:
        if (propertyString == null) throw new NullPointerException("Parsed string must be not null.");
        if (propertyString.isEmpty()) throw new IllegalArgumentException("Parsed string must be not empty.");

        // If parsed string is comment or ..., return null:
        if (propertyString.startsWith("!")) return null;
        if (propertyString.startsWith("@")) return null;

        // Trim parsed string:
        String propertyStr = propertyString.trim();
        if (propertyStr.startsWith("=")) throw new PropertyIsInvalidException("Property key must be not empty.");

        // Check if property string has equal sing:
        if (!propertyStr.contains("=")) return null;

        // Parse property str:
        String[] keyValuePair = propertyStr.split("=");

        // Create property object:
        if (keyValuePair.length == 1) return new Property(keyValuePair[0], "");
        else return new Property(keyValuePair[0], keyValuePair[1]);
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
