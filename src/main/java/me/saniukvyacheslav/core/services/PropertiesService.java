package me.saniukvyacheslav.core.services;


import me.saniukvyacheslav.core.prop.Property;
import me.saniukvyacheslav.core.prop.PropertyNotFoundException;

import java.io.IOException;

public interface PropertiesService {

    /**
     * Read property instance from repository by property key.
     * @param aPropertyKey - property key.
     * @return - read property instance.
     * @throws PropertyNotFoundException - if property is not exist in repository.
     * @throws IOException - if property is not exist in repository.
     */
    Property read(String aPropertyKey) throws PropertyNotFoundException, IOException;

    /**
     * Read property value from repository by property key.
     * @param aPropertyKey - property key.
     * @return - property value.
     * @throws PropertyNotFoundException - if property is not exist in repository.
     * @throws IOException - if property is not exist in repository.
     */
    String readValue(String aPropertyKey) throws PropertyNotFoundException, IOException;

    /*
    void create(Property property) throws PropertyAlreadyExistException, IOException;

    void create(String aPropertyKey, String aPropertyValue) throws PropertyAlreadyExistException, IOException;
    */
}
