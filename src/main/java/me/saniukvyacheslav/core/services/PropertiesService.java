package me.saniukvyacheslav.core.services;


import me.saniukvyacheslav.core.prop.Property;
import me.saniukvyacheslav.core.prop.PropertyNotFoundException;

import java.io.IOException;

public interface PropertiesService {

    Property read(String aPropertyKey) throws PropertyNotFoundException, IOException;

    String readValue(String aPropertyKey) throws PropertyNotFoundException, IOException;

    /*
    void create(Property property) throws PropertyAlreadyExistException, IOException;

    void create(String aPropertyKey, String aPropertyValue) throws PropertyAlreadyExistException, IOException;
    */
}
