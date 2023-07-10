package me.saniukvyacheslav.core.services;


import me.saniukvyacheslav.core.prop.Property;
import me.saniukvyacheslav.core.exceptions.PropertyAlreadyExistException;
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

    /**
     * Create property instance in repository.
     * @param property - property to create.
     * @throws PropertyAlreadyExistException - If property with same key already exist in repository.
     * @throws IOException - If IO exception occurs.
     */
    void create(Property property) throws PropertyAlreadyExistException, IOException;

    /**
     * Create property instance in repository.
     * @param aPropertyKey - property key.
     * @param aPropertyValue - property value.
     * @throws PropertyAlreadyExistException - If property with same key already exist in repository.
     * @throws IOException - If IO exception occurs.
     */
    void create(String aPropertyKey, String aPropertyValue) throws PropertyAlreadyExistException, IOException;

}
