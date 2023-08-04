package me.saniukvyacheslav.fs;

import me.saniukvyacheslav.prop.Property;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public interface ExtendedCrudRepository {

    /**
     * Save list of {@link Property} properties in repository.
     * If any property in list is invalid, method skip it, and not save it in repository.
     * If specified list is null or empty, method skip it, and return new empty list.
     * @param aPropertiesList - list of {@link Property} properties.
     * @return - list of saved properties.
     * @throws IOException - If IO exceptions occur.
     */
    List<Property> save(@Nullable List<Property> aPropertiesList) throws IOException;

    /**
     * Read specified properties from repository.
     * This method used {@link ExtendedCrudRepository#readByKeys(List)} method under the hood.
     * @param aPropertiesList - list of properties will be read.
     * @return - list of read properties.
     * @throws IOException - If IO exceptions occur.
     */
    List<Property> read(@Nullable List<Property> aPropertiesList) throws IOException;

    /**
     * Read properties from repository by properties keys.
     * If any key in specified list is invalid, method skip it.
     * If specified list is null or empty, method return null or empty list.
     * @param aPropertiesKeys - list of properties keys.
     * @return - list of read properties.
     * @throws IOException - If IO exceptions occur.
     */
    List<Property> readByKeys(@Nullable List<String> aPropertiesKeys) throws IOException;

    /**
     * Update properties in repository.
     * @param aPropertiesList - properties list with new values.
     * @return - list of updated properties.
     * @throws IOException - If IO exceptions occur.
     */
    List<Property> update(@Nullable List<Property> aPropertiesList) throws IOException;
}
