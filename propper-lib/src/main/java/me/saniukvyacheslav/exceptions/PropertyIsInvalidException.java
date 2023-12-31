package me.saniukvyacheslav.exceptions;

import me.saniukvyacheslav.prop.Property;
import me.saniukvyacheslav.prop.PropertyWrapper;

/**
 *  PropertyIsInvalidException exception object describes situations, when {@link Property}
 * object is invalid.
 * For example:
 *  * Property key null or empty;
 *  * Property object is null.
 *  This exception often throws in {@link PropertyWrapper} check methods.
 */
public class PropertyIsInvalidException extends RuntimeException {

    /**
     * Construct new exception instance with specified message.
     * @param aMessage - exception message.
     */
    public PropertyIsInvalidException(String aMessage) {
        super(aMessage);
    }

}
