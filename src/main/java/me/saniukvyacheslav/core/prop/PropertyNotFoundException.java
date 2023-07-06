package me.saniukvyacheslav.core.prop;

/**
 * PropertyNotFoundException exception throws in cases, when property object not exist or not found in repository.
 */
public class PropertyNotFoundException extends Exception {

    private final String propertyKey;

    /**
     * Construct new PropertyNotFoundException object of property key.
     * @param aPropertyKey - property key.
     */
    public PropertyNotFoundException(String aPropertyKey) {
        this.propertyKey = aPropertyKey;
    }

    @Override
    public String getMessage() {
        return String.format("Property [%s] not found.", this.propertyKey);
    }
}
