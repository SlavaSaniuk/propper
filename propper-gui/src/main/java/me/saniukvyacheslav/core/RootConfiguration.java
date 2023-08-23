package me.saniukvyacheslav.core;

import me.saniukvyacheslav.annotation.pattern.Singleton;
import me.saniukvyacheslav.core.store.PropertiesStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Configuration class has all beans definitions (Services, repositories, ...).
 */
@Singleton
public class RootConfiguration {

    private static RootConfiguration INSTANCE; // Singleton instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(RootConfiguration.class); // Logger;
    // Class variables:
    private PropertiesStore currentPropertiesStoreImpl; // Current PropertiesStore implementation;
    private boolean propertiesStoreInitState; // PropertiesStore "init" state;

    /**
     * Private default constructor.
     */
    private RootConfiguration() {
        LOGGER.debug("Construct [RootConfiguration] singleton instance.");
    }

    /**
     * Get current singleton instance of this class.
     * @return - singleton instance.
     */
    public static RootConfiguration getInstance() {
        if (RootConfiguration.INSTANCE == null) RootConfiguration.INSTANCE = new RootConfiguration();
        return RootConfiguration.INSTANCE;
    }

    /**
     * Get current PropertiesStore implementation.
     * @return - PropertiesStore implementation.
     */
    public PropertiesStore getPropertiesStore() {
        if (!this.propertiesStoreInitState)
            throw new IllegalStateException("Properties store service must be initialized before [see: RootConfiguration#initPropertiesStore method].");
        else return this.currentPropertiesStoreImpl;
    }

    /**
     * Init current PropertiesStore implementation.
     * @param aPropertiesStore - properties store implementation.
     */
    public void initPropertiesStore(PropertiesStore aPropertiesStore) {
        // Check parameter:
        Objects.requireNonNull(aPropertiesStore, "A [PropertiesStore] implementation must be not [null].");
        LOGGER.debug(String.format("Init current [PropertiesStore] implementation of [%s] class;", aPropertiesStore.getClass().getName()));

        // Map parameter:
        this.currentPropertiesStoreImpl = aPropertiesStore;

        // Set "init" state:
        this.propertiesStoreInitState = true;
    }

}
