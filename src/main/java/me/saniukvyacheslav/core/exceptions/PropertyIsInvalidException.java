package me.saniukvyacheslav.core.exceptions;

/**
 *  PropertyIsInvalidException exception object describes situations, when {@link me.saniukvyacheslav.core.prop.Property}
 * object is invalid.
 * For example:
 *  * Property key null or empty;
 *  * Property object is null.
 *  This exception often throws in {@link me.saniukvyacheslav.core.prop.PropertyWrapper} check methods.
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
