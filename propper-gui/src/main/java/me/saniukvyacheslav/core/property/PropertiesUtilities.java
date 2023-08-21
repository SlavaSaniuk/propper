package me.saniukvyacheslav.core.property;

import me.saniukvyacheslav.prop.Property;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PropertiesUtilities {

    public static Map<String, String> toMap(List<Property> aSetOfProperties) {
        return aSetOfProperties.stream().collect(Collectors.toMap(Property::getPropertyKey, Property::getPropertyValue));
    }


}
