package me.saniukvyacheslav.repositories;

import me.saniukvyacheslav.prop.Property;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * {@link AdvancedRepository} repository interface extends {@link Repository} CRUD interface and define new methods for
 * working with properties in repository.
 */
public interface AdvancedRepository extends Repository, ExtendedCrudRepository {

    /**
     * Get all properties from repository as list of properties.
     * @return - List of read properties.
     * @throws IOException - If IO exceptions occurs.
     */
    List<Property> list() throws IOException;

    /**
     * Get all properties from repository as map of property key = property value pair.
     * @return - Map of read properties.
     * @throws IOException - If IO exceptions occurs.
     */
    Map<String, String> map() throws IOException;
}
