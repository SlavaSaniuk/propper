package me.saniukvyacheslav.core.fs;

import me.saniukvyacheslav.core.prop.Property;
import me.saniukvyacheslav.core.prop.PropertyNotFoundException;

import java.io.IOException;
import java.util.List;

/**
 * Repository interface define common CRUd methods for working with properties.
 */
public interface Repository {

    Property create(Property aProperty);

    /**
     * Read property from repository by property key.
     * @param aKey - property key.
     * @return - property object.
     * @throws PropertyNotFoundException - Throws in cases, when property with specified key not exist in repository.
     * @throws IOException - If IO exceptions occurs.
     */
    Property read(String aKey) throws PropertyNotFoundException, IOException;

    Property update(String aKey, String aNewValue) throws PropertyNotFoundException;

    Property delete(String aKey) throws PropertyNotFoundException;

    List<Property> list();

}
