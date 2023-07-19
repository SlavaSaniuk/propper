package me.saniukvyacheslav.fs;

import me.saniukvyacheslav.prop.Property;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AdvancedRepository {

    List<Property> list() throws IOException;

    Map<String, String> map() throws IOException;
}
