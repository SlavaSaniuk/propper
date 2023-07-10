package me.saniukvyacheslav.core.prop;

public class PropertyAlreadyExistException extends Exception {

    public PropertyAlreadyExistException(String aPropertyKey) {
        super(String.format("Property [%s] already exist.", aPropertyKey));
    }
}
