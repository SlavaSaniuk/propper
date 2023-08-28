package me.saniukvyacheslav.core.store;

import me.saniukvyacheslav.core.property.ExtendedBaseProperty;
import me.saniukvyacheslav.prop.Property;

import java.io.Closeable;
import java.util.List;

/**
 * Properties store. Used to store loaded from repository properties and work with them in repositories instances.
 */
public interface PropertiesStore<T extends ExtendedBaseProperty> extends Closeable {

    /**
     * Check if properties loaded in this store.
     * @return - true, if properties were loading.
     */
    boolean isLoaded();

    /**
     * Get stored instances.
     * @return - stored instance.
     */
    List<T> getStored();

    /**
     * Get list of loaded properties.
     * @return - list of loaded properties.
     */
    List<Property> getProperties();
}
