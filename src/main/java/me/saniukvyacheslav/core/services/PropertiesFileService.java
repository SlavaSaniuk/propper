package me.saniukvyacheslav.core.services;

import me.saniukvyacheslav.core.fs.FileRepository;
import me.saniukvyacheslav.core.prop.Property;
import me.saniukvyacheslav.core.prop.PropertyNotFoundException;

import java.io.IOException;

public class PropertiesFileService implements PropertiesService {

    private final FileRepository fileRepository; // File repository;

    /**
     * Construct new {@link PropertiesFileService} service.
     * @param aPathToPropertiesFile - path to properties file.
     */
    public PropertiesFileService(String aPathToPropertiesFile) {
        this.fileRepository = FileRepository.withPropertyFile(aPathToPropertiesFile);
    }

    @Override
    public Property read(String aPropertyKey) throws PropertyNotFoundException, IOException {
        // Check property key:
        if (aPropertyKey == null) throw new IllegalArgumentException("Property key must be not null.");
        if(aPropertyKey.isEmpty()) throw new IllegalArgumentException("Property key must be not empty.");

        // Find property in file:
        Property foundedProperty = this.fileRepository.read(aPropertyKey);

        // Check if property founded in property file:
        if (foundedProperty == null) throw new PropertyNotFoundException(aPropertyKey);
        else return foundedProperty;
    }

    @Override
    public String readValue(String aPropertyKey) throws PropertyNotFoundException, IOException {
        return this.read(aPropertyKey).getPropertyValue();
    }
}
