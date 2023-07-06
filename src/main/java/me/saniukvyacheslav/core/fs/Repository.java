package me.saniukvyacheslav.core.fs;

import me.saniukvyacheslav.core.prop.Property;
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
    void create(Property aProperty) throws IOException;

    /**
     * Read property from repository by property key.
     * @param aKey - property key.
     * @return - property object.
     * @throws PropertyNotFoundException - Throw in cases, when property with specified key not exist in repository.
     * @throws IOException - If IO exceptions occurs.
     */
    Property read(String aKey) throws PropertyNotFoundException, IOException;

    Property update(String aKey, String aNewValue) throws PropertyNotFoundException;

    Property delete(String aKey) throws PropertyNotFoundException;

}
