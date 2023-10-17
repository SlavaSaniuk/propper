package me.saniukvyacheslav.core.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.prop.Property;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class AdvancedPropertiesList {

    @Getter private final List<Property> properties = new ArrayList<>(); // Inner properties list;

    public static AdvancedPropertiesList ofList(List<Property> aList) {
        AdvancedPropertiesList propertiesList = new AdvancedPropertiesList();
        aList.forEach(propertiesList::add);
        return propertiesList;
    }

    public void add(@Nullable Property aProperty) {
        if (aProperty == null) return;
        // Check if property with same key already in list:
        if (!this.contains(aProperty)) this.properties.add(aProperty);
    }

    public void add(@Nullable String aPropertyKey) {
        if (aPropertyKey == null) return;
        if (!this.contains(aPropertyKey)) this.properties.add(new Property(aPropertyKey, ""));
    }

    public void addAll(@Nullable List<Property> aList) {
        if (aList == null) return;
        aList.forEach(this::add);
    }

    public boolean contains(String aPropertyKey) {
        if (aPropertyKey == null) return false;
        return this.properties.stream().anyMatch(property -> property.getPropertyKey().equals(aPropertyKey));
    }

    public boolean contains(Property aProperty) {
        if (aProperty == null) return false;
        return this.contains(aProperty.getPropertyKey());
    }


}
