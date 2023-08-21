package me.saniukvyacheslav.core.repo;

import me.saniukvyacheslav.prop.Property;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This interface define common methods for working with properties in any repositories.
 * All repository implementations must implement this interface. {@link me.saniukvyacheslav.core.repo.file.FileRepository}.
 */
public interface PropertiesRepository {

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
    void updateKeys(Map<String, String> anKeysChanges);
}
