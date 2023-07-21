package me.saniukvyacheslav.core.properties;

import me.saniukvyacheslav.services.PropertiesFileService;
import me.saniukvyacheslav.services.PropertiesService;

import java.io.File;

public class PropertiesServiceFactory {

    public static PropertiesService fileService(File aPropertiesFile) throws IllegalArgumentException {
        return new PropertiesFileService(aPropertiesFile.getPath());
    }
}
