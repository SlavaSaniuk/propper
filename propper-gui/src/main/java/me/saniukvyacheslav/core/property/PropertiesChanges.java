package me.saniukvyacheslav.core.property;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.prop.Property;

import java.util.*;

/**
 * PropertiesChanges instance store properties changes by categories: inserts, updates, deletions.
 * Use {@link PropertiesChangesHandler#handle(Set, List)} to get instance of this object.
 */
@NoArgsConstructor
public class PropertiesChanges {

    @Getter private final List<Property> propertiesInserts = new ArrayList<>(); // Properties inserts;
    @Getter private final List<PropertyChanges> propertiesUpdates = new ArrayList<>(); // Properties updates;
    @Getter private final List<String> propertiesDeletes = new ArrayList<>(); // Properties deletions;

    /**
     * Get Map of properties keys updates.
     * Map of keys updates consist of origin_property_key=actual_property_key pairs.
     * @return - Map of keys updates.
     */
    public Map<String, String> getPropertiesKeysUpdates() {
        // Result map:
        Map<String, String> propertiesKeyUpdates = new HashMap<>();

        // Check properties changes list:
        this.propertiesUpdates.forEach(propertyChanges -> {
            String originKey = propertyChanges.getOriginPropertyKey();
            String actualKey = propertyChanges.getChangedProperty().getPropertyKey();
            // If actual key not equals origin key, then add it to map:
            if (!(originKey.equals(actualKey))) propertiesKeyUpdates.put(originKey, actualKey);
        });


        return propertiesKeyUpdates;
    }

    /**
     * Check if properties has changes.
     * @return - true, if properties has changes.
     */
    public boolean isEmpty() {
        if (!this.propertiesInserts.isEmpty()) return false;
        if (!this.propertiesUpdates.isEmpty()) return false;
        return this.propertiesDeletes.isEmpty();
    }

    /**
     * Clear all changes in this instance.
     */
    public void clear() {
        this.propertiesInserts.clear();
        this.propertiesUpdates.clear();
        this.propertiesDeletes.clear();
    }

    /**
     * To String.
     * @return - PropertiesChanges string.
     */
    @Override
    public String toString() {

        StringBuilder tmpSb = new StringBuilder();
        String insertsStr;
        this.propertiesInserts.forEach((property -> tmpSb.append(String.format("[%s]", property))));
        insertsStr = tmpSb.toString();
        tmpSb.setLength(0);
        String updatesStr;
        this.propertiesUpdates.forEach((propertyChanges ->
                tmpSb.append(String.format("[originKey: %s, updatedValue: [%s]]",
                        propertyChanges.getOriginPropertyKey(), propertyChanges.getChangedProperty()))));
       updatesStr = tmpSb.toString();
       tmpSb.setLength(0);
       String deletesStr;
       this.propertiesDeletes.forEach(originKey -> tmpSb.append(String.format("[%s]", originKey)));
       deletesStr = tmpSb.toString();

       return String.format("PropertiesChanges[Inserts: %s [%s]; %s Updates: %s [%s]; %s Deletions %s [%s]];",
               System.lineSeparator(), insertsStr, System.lineSeparator(), System.lineSeparator(), updatesStr,
               System.lineSeparator(), System.lineSeparator(), deletesStr);
    }
}
