package me.saniukvyacheslav.core.util;

import lombok.Getter;
import me.saniukvyacheslav.prop.Property;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

/**
 * PropertiesList is wrapper for List<Property> list.
 * This class contains helpful methods for working with list of properties.
 */
public class PropertiesList {

    @Getter private final UniqueElementsList<Property> properties = new UniqueElementsList<>(); // Inner properties list;

    /**
     * Construct new empty PropertiesList instance.
     */
    public PropertiesList() {}

    /**
     * Construct new PropertiesList instance of specified properties list.
     * @param aList - origin properties list.
     * @return - new instance.
     */
    public static PropertiesList ofList(List<Property> aList) {
        Objects.requireNonNull(aList, "Properties list [aList] must be not null.");
        PropertiesList propertiesList = new PropertiesList();

        aList.forEach((property) -> {
            if (property != null) {
                propertiesList.properties.add(property);
            }
        });

        return propertiesList;
    }

    public void addToList(@Nullable List<Property> aList) {
        if (aList == null) return;
        if (aList.isEmpty()) return;

        aList.forEach(property -> {
            if (property != null) this.properties.add(property);
        });
    }

    /**
     * Get property by it key.
     * @param aPropertyKey - property key.
     * @return - property, if it has been founded.
     */
    public Property getByKey(String aPropertyKey) {
        Objects.requireNonNull(aPropertyKey, "Property key [aPropertyKey] must be not null.");
        return this.properties.stream().filter(prop -> prop.getPropertyKey().equals(aPropertyKey)).findFirst().orElse(null);
    }

    public boolean isInList(String aPropertyKey) {
        Objects.requireNonNull(aPropertyKey, "Property key [aPropertyKey] must be not null.");
        return this.getByKey(aPropertyKey) != null;
    }



}
