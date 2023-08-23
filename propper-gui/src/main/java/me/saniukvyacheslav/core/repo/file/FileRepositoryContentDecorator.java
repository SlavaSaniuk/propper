package me.saniukvyacheslav.core.repo.file;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.annotation.pattern.Singleton;
import me.saniukvyacheslav.core.repo.PropertiesRepository;
import me.saniukvyacheslav.core.store.PropertiesStore;
import me.saniukvyacheslav.definition.Closeable;
import me.saniukvyacheslav.prop.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Decorator for {@link FileRepository} file repository. Used to read\write all file content (not only properties,
 * also comments, empty lines and etc).
 */
@Singleton
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileRepositoryContentDecorator implements PropertiesRepository, Closeable {

    private static FileRepositoryContentDecorator INSTANCE; // Singleton instance:
    private static final Logger LOGGER = LoggerFactory.getLogger(FileRepositoryContentDecorator.class); // Logger;
    private File contentFile; // Current file;

    /**
     * Get current singleton instance of this class.
     * @return - singleton instance.
     */
    public static FileRepositoryContentDecorator getInstance() {
        if (FileRepositoryContentDecorator.INSTANCE == null) FileRepositoryContentDecorator.INSTANCE = new FileRepositoryContentDecorator();
        return FileRepositoryContentDecorator.INSTANCE;
    }

    /**
     * Init inner base {@link FileRepository} repository with specified file.
     * @param aFile - properties file.
     * @return - this singleton instance.
     * @throws IOException - If IO Exception occurs.
     */
    public FileRepositoryContentDecorator initRepository(File aFile) throws IOException {
        LOGGER.debug(String.format("Init base FileRepository with File[%s] instance:", aFile));
        FileRepository.getInstance().init(aFile);
        LOGGER.debug("Base FileRepository singleton instance was initialized.");

        // Map parameter:
        this.contentFile = aFile;

        // Return current singleton instance:
        return FileRepositoryContentDecorator.INSTANCE;
    }

    /**
     * Save changes in repository.
     * @throws IOException - If IO exception occurs.
     */
    @Override
    public void flush() throws IOException {
        LOGGER.debug("Try to flush changes in file:");
        if (!FileRepository.getInstance().isInitialized()) {
            LOGGER.debug("Base [FileRepository] must be initialized before using.");
        }

        // Construct new file content from actual properties:
        LOGGER.debug("Construct file content from actual properties:");
        List<String> newFileContent = new ArrayList<>();
        FileRepository.getInstance().getActualProperties().forEach((property) -> newFileContent.add(property.toString()));

        // Write new file content:
        LOGGER.debug("Write new file content to file:");
        FileRepositoryUtils.writeStringsToFileLineByLine(newFileContent, this.contentFile);
        LOGGER.debug("Try to flush changes in file: SUCCESS;");
    }

    /**
     * Read all properties from file.
     * @param aStore - properties store.
     * @return - Set of properties.
     * @throws IOException - If IO Exception occurs.
     */
    @Override
    public List<Property> list(PropertiesStore aStore) throws IOException {
        if (!FileRepository.getInstance().isInitialized()) {
            LOGGER.debug("Base [FileRepository] must be initialized before using.");
        }

        LOGGER.debug("Read properties from file via base [FileRepository] repository:");
        return FileRepository.getInstance().list(aStore);
    }

    /**
     * Update properties keys in repository.
     * Method accept map of origin_property_key=new_property_key pairs.
     * @param anKeysChanges - map of origin_property_key-new_property_key pairs.
     */
    @Override
    public void updateKeys(Map<String, String> anKeysChanges) {
        FileRepository.getInstance().updateKeys(anKeysChanges);
    }

    /**
     * Update properties value in repository.
     * Method accept map of property_key=new_property_value pairs.
     * @param anValueChanges - map of property_key=new_property_value pairs.
     */
    @Override
    public void updateValues(Map<String, String> anValueChanges) {
        FileRepository.getInstance().updateValues(anValueChanges);
    }

    @Override
    public void close() throws Exception {

    }

}
