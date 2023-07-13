package me.saniukvyacheslav.core.actions;

import me.saniukvyacheslav.exceptions.PropertyIsInvalidException;
import me.saniukvyacheslav.prop.PropertyWrapper;
import me.saniukvyacheslav.services.PropertiesFileService;
import me.saniukvyacheslav.services.PropertiesService;

/**
 * Common action class, which has additional methods to reduce repeated code.
 */
public class PropertiesFileAction {

    // Disable class instances creation;
    private PropertiesFileAction() {}

    /**
     * Check specified property key and path to properties file.
     * @param aPropertyKey - property key.
     * @param aPathToPropertiesFile - path to properties file.
     * @return - properties service associated with specified properties file.
     * @throws PropertyIsInvalidException - If specified property key isn't valid (null or empty).
     * @throws IllegalArgumentException - If specified path to property file is invalid (not exist, link on directory, ...);
     */
    public static PropertiesService checkPropertyKeyAndGetPropertiesService(String aPropertyKey, String aPathToPropertiesFile) throws PropertyIsInvalidException, IllegalArgumentException {
        // Check property key:
        PropertyWrapper.checkPropertyKey(aPropertyKey);
        // Create new instance of properties file service:
        return new PropertiesFileService(aPathToPropertiesFile);
    }

}
