package me.saniukvyacheslav.prop;

import lombok.Getter;
import me.saniukvyacheslav.exceptions.PropertyIsInvalidException;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.util.Optional;

/**
 * Property object used to store key-value pair of Strings.
 */
public class Property {

    @Getter final private String propertyKey;
    @Getter private String propertyValue;

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
    public static Property parse(@Nullable String propertyString) throws PropertyIsInvalidException {
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

    /**
     * Parse specified string to Property instance.
     * This is safe implementation of {@link Property#parse(String)} method.
     * If specified string is null, empty, comment, doesn't have '=' sign or key, then {@link ParseException} will be throwing.
     * @param aStr - string to parse.
     * @return - property instance.
     * @throws ParseException - if specified string cannot be parsed.
     */
    public static Property safeParse(@Nullable String aStr) throws ParseException {
        try {
            // Unsafe parse and handle exceptions:
            return Optional.ofNullable(Property.parse(aStr)).orElseThrow(() -> new ParseException(String.format("Specified string [%s] is not a property.", aStr), 0));
        }catch (NullPointerException e) {
            throw new ParseException("Specified {String} is null.", 0);
        }catch (IllegalArgumentException e) {
            throw new ParseException("Specified {String} is empty.", 0);
        }catch (PropertyIsInvalidException e) {
            throw new ParseException(String.format("Specified property string [%s] without property key.",aStr), 0);
        }
    }

    /**
     * Set property value to current instance.
     * If specified value is null, property value will be empty.
     * @param aValue - property value.
     */
    public void setPropertyValue(@Nullable String aValue) {
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
