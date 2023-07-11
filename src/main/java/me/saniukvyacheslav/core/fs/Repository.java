package me.saniukvyacheslav.core.fs;

import me.saniukvyacheslav.core.prop.Property;

import java.io.IOException;

/**
 * Repository interface define common CRUD methods for working with properties.
 */
public interface Repository {

    /**
     * Create new property instance in database.
     * @param aProperty - property to create.
     * @throws IOException - If IO exceptions occurs.
     */
    void create(Property aProperty) throws IOException;

    /**
     * Read property from repository by property key.
     * If property with specified key not found in repository, return {@code null}.
     * @param aKey - property key.
     * @return - property object (null - if property not found in repository).
     * @throws IOException - If IO exceptions occurs.
     */
    Property read(String aKey) throws IOException;

    /**
     * Update property instance with new specified value by property key.
     * @param aKey - property key.
     * @param aNewValue - new property value.
     * @throws IOException - If IO exceptions occurs.
     */
    void update(String aKey, String aNewValue) throws IOException;

    /**
     * Delete property instance from repository bu property key.
     * @param aKey - property key.
     * @throws IOException - If IO exceptions occurs.
     */
    void delete(String aKey) throws IOException;

}
