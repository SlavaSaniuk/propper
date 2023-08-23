package me.saniukvyacheslav.core.repo.file;

import me.saniukvyacheslav.core.repo.PropertiesRepository;
import me.saniukvyacheslav.definition.Closeable;
import me.saniukvyacheslav.prop.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Decorator for {@link FileRepository} file repository. Used to read\write all file content (not only properties,
 * also comments, empty lines and etc).
 */
public class FileRepositoryContentDecorator implements PropertiesRepository, Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRepositoryContentDecorator.class); // Logger;
    private final FileRepository fileRepository; // FileRepository;
    private final File contentFile; // Current file;

    public FileRepositoryContentDecorator(FileRepository aFileRepository) {
        LOGGER.debug("Try to construct new [FileRepositoryContentDecorator] instance.");
        Objects.requireNonNull(aFileRepository, "Repository [FileRepository] must be not null.");

        // Map parameters:
        this.fileRepository = aFileRepository;
        this.contentFile = this.fileRepository.getRepositoryObject();
    }



    /**
     * Save changes in repository.
     * @throws IOException - If IO exception occurs.
     */
    @Override
    public void flush() throws IOException {
        LOGGER.debug("Try to flush changes in file:");
        if (!this.fileRepository.isInitialized()) {
            LOGGER.debug("Base [FileRepository] must be initialized before using.");
        }

        // Construct new file content from actual properties:
        LOGGER.debug("Construct file content from actual properties:");
        List<String> newFileContent = new ArrayList<>();
        this.fileRepository.getActualProperties().forEach((property) -> newFileContent.add(property.toString()));

        // Write new file content:
        LOGGER.debug("Write new file content to file:");
        FileRepositoryUtils.writeStringsToFileLineByLine(newFileContent, this.contentFile);
        LOGGER.debug("Try to flush changes in file: SUCCESS;");
    }

    /**
     * Read all properties from file.
     * @return - Set of properties.
     * @throws IOException - If IO Exception occurs.
     */
    @Override
    public List<Property> list() throws IOException {
        if (!this.fileRepository.isInitialized()) {
            LOGGER.debug("Base [FileRepository] must be initialized before using.");
        }

        LOGGER.debug("Read properties from file via base [FileRepository] repository:");
        return this.fileRepository.list();
    }

    /**
     * Update properties keys in repository.
     * Method accept map of origin_property_key=new_property_key pairs.
     * @param anKeysChanges - map of origin_property_key-new_property_key pairs.
     */
    @Override
    public void updateKeys(Map<String, String> anKeysChanges) {
        this.fileRepository.updateKeys(anKeysChanges);
    }

    /**
     * Update properties value in repository.
     * Method accept map of property_key=new_property_value pairs.
     * @param anValueChanges - map of property_key=new_property_value pairs.
     */
    @Override
    public void updateValues(Map<String, String> anValueChanges) {
        this.fileRepository.updateValues(anValueChanges);
    }

    @Override
    public void close() throws Exception {

    }

}
