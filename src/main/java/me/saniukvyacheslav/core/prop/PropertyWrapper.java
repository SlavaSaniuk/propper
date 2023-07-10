package me.saniukvyacheslav.core.prop;

import me.saniukvyacheslav.core.exceptions.PropertyIsInvalidException;

/**
 * PropertyWrapper wrapper class has utilities methods for working with {@link Property} instances.
 */
public class PropertyWrapper {

    /**
     * Method check property key on null and emptiness.
     * If property key is invalid, then method throw {@link PropertyIsInvalidException} exception with corresponding message.
     * @param aPropertyKey - property key.
     * @throws RuntimeException - If property key is invalid (NPE ar IAE).
     */
    public static void checkPropertyKey(String aPropertyKey) throws RuntimeException {
        if (aPropertyKey == null) throw new NullPointerException("Property key must be not mull.");
        if (aPropertyKey.isEmpty()) throw new IllegalArgumentException("Property ket must be not empty.");
    }

    /**
     * Method check property instance on null, and then check property key via {@link PropertyWrapper#checkPropertyKey(String)} method.
     * @param aProperty - property instance.
     * @throws PropertyIsInvalidException - If property is invalid.
     */
    public static void checkProperty(Property aProperty) throws PropertyIsInvalidException {
        if (aProperty == null) throw new PropertyIsInvalidException("Property object must be not null");
        try {
            PropertyWrapper.checkPropertyKey(aProperty.getPropertyKey());
        }catch (RuntimeException e) {
            throw new PropertyIsInvalidException(e.getMessage());
        }
    }
}
