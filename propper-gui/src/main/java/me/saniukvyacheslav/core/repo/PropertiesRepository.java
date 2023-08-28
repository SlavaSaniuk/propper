package me.saniukvyacheslav.core.repo;

import me.saniukvyacheslav.prop.Property;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This interface define common methods for working with properties in any repositories.
 * All repository implementations must implement this interface.
 * Known implementations: {@link me.saniukvyacheslav.core.repo.file.FileRepository}.
 */
public interface PropertiesRepository extends Closeable {

    /**
     * Save changes in repository.
     * @throws IOException - If IOException occurs.
     */
    void flush() throws IOException;

    /**
     * Read all properties from repository.
     * @return - Set of properties.
     * @throws IOException - If IO Exception occurs.
     */
    List<Property> list() throws IOException;

    /**
     * Update properties keys in repository.
     * Method accept map of origin_property_key=new_property_key pairs.
     * @param anKeysChanges - map of origin_property_key-new_property_key pairs.
     */
    void updateKeys(Map<String, String> anKeysChanges) throws IOException;

    /**
     * Update properties value in repository.
     * Method accept map of property_key=new_property_value pairs.
     * @param anValueChanges - map of property_key=new_property_value pairs.
     */
    void updateValues(Map<String, String> anValueChanges) throws IOException;

    /**
     * Get repository object.
     * @return - repository object.
     */
    Object getRepositoryObject();
}
