package me.saniukvyacheslav.core.repo;

import me.saniukvyacheslav.prop.Property;

import java.io.IOException;
import java.util.List;

public interface PropertiesRepository {

    List<Property> update(List<Property> listOfProperties) throws IOException;

    List<Property> list() throws IOException;
}
