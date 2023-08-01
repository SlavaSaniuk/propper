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

}
