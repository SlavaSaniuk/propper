package me.saniukvyacheslav.core.repo.file;

import me.saniukvyacheslav.core.repo.PropertiesRepository;
import me.saniukvyacheslav.exceptions.PropertyNotFoundException;
import me.saniukvyacheslav.prop.Property;
import me.saniukvyacheslav.services.PropertiesFileService;
import me.saniukvyacheslav.services.PropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class FileRepository implements PropertiesRepository {

    // Class variables:
    private static FileRepository INSTANCE; // Singleton instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(FileRepository.class); // Logger;
    private File propertiesFile; // Opened properties file;
    private PropertiesService propertiesService;

    /**
     * Get current singleton instance of this class.
     * @param aPropertiesFile - properties file.
     * @return - current singleton instance.
     */
    public static FileRepository getInstance(File aPropertiesFile) {
        LOGGER.debug(String.format("Get current singleton instance of [%s] class;", FileRepository.class.getName()));
        // Check properties file:
        Objects.requireNonNull(aPropertiesFile, "Properties file must be not null.");

        // Create instance if it is not created:
        if(FileRepository.INSTANCE == null) FileRepository.INSTANCE = new FileRepository();

        // Create properties service:
        if(aPropertiesFile == FileRepository.INSTANCE.propertiesFile) return FileRepository.INSTANCE;
        FileRepository.INSTANCE.propertiesService = new PropertiesFileService(aPropertiesFile.getAbsolutePath());
        FileRepository.INSTANCE.propertiesFile = aPropertiesFile;

        return FileRepository.INSTANCE;
    }

    /**
        Private default constructor
     */
    private FileRepository() {
        LOGGER.debug(String.format("Construct singleton instance of [%s] class.", FileRepository.class.getName()));
    }

    @Override
    public List<Property> update(List<Property> listOfProperties) throws IOException {
        try {
            this.propertiesService.update("123", "234");
        } catch (PropertyNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listOfProperties;
    }

    @Override
    public List<Property> list() throws IOException {
        // Load properties:
        LOGGER.debug(String.format("Load all properties from file [%s];", this.propertiesFile.getAbsolutePath()));
        List<Property> loadedProperties = this.propertiesService.list();

        LOGGER.debug(String.format("%d properties were loaded;", loadedProperties.size()));
        return loadedProperties;
    }
}
