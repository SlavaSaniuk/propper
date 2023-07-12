package me.saniukvyacheslav.exceptions;

/**
 * PropertyAlreadyExist exception describes situation, when property instance already exist in repository.
 */
public class PropertyAlreadyExistException extends Exception {

    /**
     * Construct new exception instance with specified property key.
     * @param aPropertyKey - property key.
     */
    public PropertyAlreadyExistException(String aPropertyKey) {
        super(String.format("Property [%s] already exist.", aPropertyKey));
    }
}
