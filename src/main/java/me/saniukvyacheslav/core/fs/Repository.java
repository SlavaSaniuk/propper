package me.saniukvyacheslav.core.fs;

import me.saniukvyacheslav.core.prop.Property;

import java.util.List;

public interface Repository {

    Property create(Property aProperty);

    Property read(String aKey);

    Property update(String aKey, String aNewValue);

    Property delete(String aKey);

    List<Property> list();

}
