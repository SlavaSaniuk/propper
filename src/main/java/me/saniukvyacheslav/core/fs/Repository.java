package me.saniukvyacheslav.core.fs;

import me.saniukvyacheslav.core.prop.Property;
import me.saniukvyacheslav.core.prop.PropertyAlreadyExistException;
import me.saniukvyacheslav.core.prop.PropertyNotFoundException;

import java.io.IOException;

/**
 * Repository interface define common CRUd methods for working with properties.
 */
public interface Repository {

    /**
     * Create property instance in repository.
     * @param aProperty - property for creation.
     * @throws IOException - if IO exception occurs.
     */
    void create(Property aProperty) throws IOException, PropertyAlreadyExistException;

    /**
     * Read property from repository by property key.
     * If property with specified key not found in repository, return {@code null}.
     * @param aKey - property key.
     * @return - property object (null - if property not found in repository).
     * @throws IOException - If IO exceptions occurs.
     */
    Property read(String aKey) throws IOException;

    Property update(String aKey, String aNewValue) throws PropertyNotFoundException, IOException;

    Property delete(String aKey) throws PropertyNotFoundException;

}
