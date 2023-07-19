package me.saniukvyacheslav.services;

import me.saniukvyacheslav.prop.Property;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * {@link AdvancesPropertiesService} service define advanced methods for working with properties.
 */
public interface AdvancesPropertiesService {

    /**
     * Get all properties as list of properties.
     * @return - List of read properties.
     * @throws IOException - If IO exceptions occurs.
     */
    List<Property> list() throws IOException;

    /**
     * Get all properties as map of property key = property value pair.
     * @return - Map of read properties.
     * @throws IOException - If IO exceptions occurs.
     */
    Map<String, String> map() throws IOException;
}
