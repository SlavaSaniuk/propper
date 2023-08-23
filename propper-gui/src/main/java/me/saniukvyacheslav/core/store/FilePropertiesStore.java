package me.saniukvyacheslav.core.store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.annotation.pattern.Singleton;
import me.saniukvyacheslav.core.property.ExtendedBaseProperty;
import me.saniukvyacheslav.core.util.UniqueElementsList;
import me.saniukvyacheslav.prop.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Singleton
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilePropertiesStore implements PropertiesStore {

    private static FilePropertiesStore INSTANCE; // Singleton instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(FilePropertiesStore.class);
    // Class variables:
    @Getter private boolean isLoaded = false; // Loading flag;
    private final UniqueElementsList<ExtendedBaseProperty> loadedProperties = new UniqueElementsList<>(); // List of loaded properties;

    /**
     * Get current singleton instance of this class.
     * @return - singleton instance.
     */
    public static FilePropertiesStore getInstance() {
        LOGGER.debug("Get current singleton instance of [FilePropertiesStore] class.");
        if(FilePropertiesStore.INSTANCE == null) FilePropertiesStore.INSTANCE = new FilePropertiesStore();
        return FilePropertiesStore.INSTANCE;
    }

    /**
     * Load properties in this store {@link FilePropertiesStore#loadedProperties}.
     * @param aLoadList - properties to load.
     */
    public void load(List<Property> aLoadList) {
        LOGGER.debug("Try to load properties in [FilePropertiesStore]:");
        // Check parameters:
        Objects.requireNonNull(aLoadList, "Specified list of [Property] instances must be not null.");
        if (aLoadList.isEmpty()) {
            LOGGER.debug("Specified list of [Property] instance is empty. Nothing to do;");
            return;
        }

        // Load properties:
        // Cast each property to EBP property and add it to loaded list:
        aLoadList.forEach((property -> {
            if (!(this.loadedProperties.add(new ExtendedBaseProperty(property))))
                LOGGER.debug(String.format("Property [%s] not loaded.", property));
        }));
        LOGGER.debug(String.format("Loaded [%d] properties;", this.loadedProperties.size()));

        // Set loading flag:
        this.isLoaded = true;
    }

    /**
     * Get list of loaded properties.
     * @return - list of loaded properties.
     */
    @Override
    public List<Property> getProperties() {
        if (!this.isLoaded) {
            LOGGER.debug("Properties are not loaded. Return [null].");
            return null;
        }
        return this.loadedProperties.stream().map(ExtendedBaseProperty::getActualProperty).collect(Collectors.toList());
    }

    /**
     * Reset current singleton instance. Clear loaded properties list and unset "loaded" flag.
     */
    public void reset() {
        LOGGER.debug("Reset current [FilePropertiesStore] singleton instance.");
        // Clear loaded properties:
        this.loadedProperties.clear();

        // Unset loaded flag:
        this.isLoaded = false;
    }
}
