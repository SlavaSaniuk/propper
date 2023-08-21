package me.saniukvyacheslav.core.repo.file;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.prop.Property;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class ActualProperties {

    // Class variables:
    @Getter private boolean isLoaded = false; // Loading state;
    private final List<Property> memoryProperties = new ArrayList<>(); // Memory list of properties;
    private final Map<String, String> memoryPropertiesMap = new HashMap<>(); // Memory map of properties;

    /**
     * Load properties in this instance.
     * @param aListOfProperties - list to load.
     */
    public void load(@Nullable List<Property> aListOfProperties) {
        // Check parameters:
        if (aListOfProperties == null || aListOfProperties.isEmpty()) return;

        // Sync store:
        this.syncStore();

        // Check each property:
        // Add property if it's not exist:
        for (Property property: aListOfProperties) {
            this.add(property);
        }

        // Set state:
        this.isLoaded = true;
    }

    public boolean add(@Nullable Property aProperty) {
        // Check property:
        if (aProperty == null) return false; // Skip property;
        if (aProperty.getPropertyKey() == null || aProperty.getPropertyKey().isEmpty()) return false; // Skip property;

        // Check if property already in list:
        if (!(this.contains(aProperty.getPropertyKey()))) {
            this.memoryProperties.add(aProperty);
            this.syncStore();
            return true;
        } else return false;
    }

    public Property getPropertyByKey(@Nullable String aKey) {
        // Get property index:
        int index = this.getIndexByKey(aKey);

        if (index >= 0) return this.memoryProperties.get(index);
        else return null;
    }

    /**
     * Change property key for property.
     * Method replace property of origin key with new property of actual key and previous value.
     * Method return updated property or null, if property not found in memory table.
     * @param anOriginKey - origin property key to be replaced.
     * @param anActualKey - new property key.
     * @return - updated property, or null.
     */
    public Property changePropertyKey(@Nullable String anOriginKey, String anActualKey) {
        // Check actual key:
        if (anActualKey == null || anActualKey.isEmpty()) return null;
        // Get origin property:
        Property originProperty = this.getPropertyByKey(anOriginKey);
        if (originProperty == null) return null;

        Property changedProperty = new Property(anActualKey, originProperty.getPropertyValue());
        this.getMemoryProperties().set(this.memoryProperties.indexOf(originProperty), changedProperty);

        // Sync stores:
        this.syncStore();
        return changedProperty;
    }

    public int getIndexByKey(@Nullable String aKey)  {
        // Check key:
        if (aKey == null || aKey.isEmpty()) return -1;

        // Search key:
        for (int i=0; i<this.memoryProperties.size(); i++) {
            if (this.memoryProperties.get(i).getPropertyKey().equals(aKey)) return i;
        }
        return -1;
    }

    public boolean contains(String aPropertyKey) {
        return this.memoryPropertiesMap.containsKey(aPropertyKey);
    }

    public void reset() {
        // Clear stores:
        this.memoryProperties.clear();
        this.memoryPropertiesMap.clear();

        // Reset loading state:
        this.isLoaded = false;
    }

    public List<Property> getMemoryProperties() {
        return this.memoryProperties;
    }

    public void syncStore() {
        // Sync map:
        this.memoryProperties.forEach((property -> this.memoryPropertiesMap.put(property.getPropertyKey(), property.getPropertyValue())));
    }
}
