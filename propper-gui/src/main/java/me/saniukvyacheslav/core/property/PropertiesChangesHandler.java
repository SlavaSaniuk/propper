package me.saniukvyacheslav.core.property;

import me.saniukvyacheslav.prop.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Properties changes handler.
 */
public class PropertiesChangesHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesChangesHandler.class); // Logger;

    /**
     * Handle properties changes by relation to origin properties list.
     * @param originPropertiesList - origin properties list.
     * @param changesList - list of properties changes.
     * @return - {@link PropertiesChanges} store instance.
     */
    public static PropertiesChanges handle(List<Property> originPropertiesList, List<PropertyChanges> changesList) {
        PropertiesChanges changes = new PropertiesChanges();

        // Check arguments:
        Objects.requireNonNull(originPropertiesList);
        Objects.requireNonNull(changesList);
        if (changesList.isEmpty()) return changes; // Empty PropertiesChanges;

        LOGGER.debug("Handle properties changes by relation to origin properties list:");
        // Print lists:
        LOGGER.debug("Origin properties list: ");
        originPropertiesList.forEach((prop) -> LOGGER.debug(String.format("\t [%s]", prop)));
        LOGGER.debug("Changes list:");
        changesList.forEach((prop) -> LOGGER.debug(String.format("\t [%s]", prop)));

        // Iterate thought changes list:
        Set<String> originListOfKeys = originPropertiesList.stream().map(Property::getPropertyKey).collect(Collectors.toSet());
        changesList.forEach((propChanges -> {

            if (!(originListOfKeys.contains(propChanges.getOriginPropertyKey()))) {
                // Inserts:
                if(propChanges.getChangedProperty() != null) changes.getPropertiesInserts().add(propChanges.getChangedProperty());
            } else { // If origin list contains origin property key:
                // Updates or deletion:
                if(propChanges.getChangedProperty() != null) changes.getPropertiesUpdates().add(propChanges); // Updates;
                else changes.getPropertiesDeletes().add(propChanges.getOriginPropertyKey()); // Deletions;
            }

        }));
        return changes;
    }


}
