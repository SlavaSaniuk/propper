package me.saniukvyacheslav.core.services;

import me.saniukvyacheslav.core.exceptions.PropertyIsInvalidException;
import me.saniukvyacheslav.core.fs.FileRepository;
import me.saniukvyacheslav.core.prop.Property;
import me.saniukvyacheslav.core.exceptions.PropertyAlreadyExistException;
import me.saniukvyacheslav.core.exceptions.PropertyNotFoundException;
import me.saniukvyacheslav.core.prop.PropertyWrapper;

import java.io.IOException;

/**
 * {@link PropertiesFileService} service is implementation of {@link PropertiesService} interface,
 * and it used in cases when properties saved in properties file (properties repository = properties file).
 */
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

    @Override
    public void create(Property property) throws PropertyAlreadyExistException, IOException {
        // Check property instance:
        PropertyWrapper.checkProperty(property);

        // Check if property already exist in repo:
        if(this.fileRepository.read(property.getPropertyKey()) != null)
            throw new PropertyAlreadyExistException(property.getPropertyKey());

        // try to create property in file:
        this.fileRepository.create(property);
    }

    @Override
    public void create(String aPropertyKey, String aPropertyValue) throws PropertyAlreadyExistException, IOException {
        // Check property key:
        PropertyWrapper.checkPropertyKey(aPropertyKey);

        // Create property in file:
        this.create(new Property(aPropertyKey, aPropertyValue));
    }

    @Override
    public void update(Property aProperty) throws PropertyNotFoundException, IOException {
        // Check property:
        if(aProperty == null) throw new PropertyIsInvalidException("Property must be not null.");

        // Update property value:
        this.update(aProperty.getPropertyKey(), aProperty.getPropertyValue());
    }

    @Override
    public void update(String aPropertyKey, String aPropertyNewValue) throws PropertyNotFoundException, IOException {
        // Check property key:
        PropertyWrapper.checkPropertyKey(aPropertyKey);

        // Check if property exist in file:
        // If property is not exist, then throw PNFE:
        this.read(aPropertyKey);

        // Update property value:
        this.fileRepository.update(aPropertyKey, aPropertyNewValue);
    }

    @Override
    public void delete(Property aProperty) throws PropertyNotFoundException, IOException {
        // Check specified property instance at null:
        if (aProperty == null) throw new PropertyIsInvalidException("Property instance must be not null.");
        // Delete property instance by key:
        this.delete(aProperty.getPropertyKey());
    }

    @Override
    public void delete(String aPropertyKey) throws PropertyNotFoundException, IOException {
        // Check property key:
        PropertyWrapper.checkPropertyKey(aPropertyKey);

        // Check if property exist in database:
        // If "read()" completed without exceptions, property exist in file:
        this.fileRepository.read(aPropertyKey);

        // Delete property from file:
        this.fileRepository.delete(aPropertyKey);
    }

}
