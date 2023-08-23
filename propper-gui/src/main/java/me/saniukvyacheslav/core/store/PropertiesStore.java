package me.saniukvyacheslav.core.store;

import me.saniukvyacheslav.prop.Property;

import java.util.List;

/**
 * Properties store. Used to store loaded from repository properties and work with them in repositories instances.
 */
public interface PropertiesStore {

    /**
     * Check if properties loaded in this store.
     * @return - true, if properties were loading.
     */
    boolean isLoaded();

    /**
     * Get list of loaded properties.
     * @return - list of loaded properties.
     */
    List<Property> getProperties();
}
