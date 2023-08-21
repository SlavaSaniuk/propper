package me.saniukvyacheslav.core.repo.file;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.annotation.pattern.Singleton;
import me.saniukvyacheslav.core.repo.PropertiesRepository;
import me.saniukvyacheslav.core.repo.RepositoryTypes;
import me.saniukvyacheslav.core.repo.exception.RepositoryNotInitializedException;
import me.saniukvyacheslav.definition.Closeable;
import me.saniukvyacheslav.definition.Initializable;
import me.saniukvyacheslav.prop.Property;
import me.saniukvyacheslav.util.file.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * FileRepository class in implementation of {@link PropertiesRepository} repository for properties files.
 * This class implements singleton pattern, that's why only one instance of this class can be installed.
 * Attention:
 *  * Before using class methods, developer must initialize this repository vio {@link FileRepository#init(Object...)} method,
 *  where the first element in objects array is link to {@link File} properties file.
 *  * After using this class, developer close this repository via {@link FileRepository#close()}.
 */
@Singleton
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileRepository implements Initializable, Closeable, PropertiesRepository {

    // Class variables:
    private static FileRepository INSTANCE; // Singleton instance;
    private static final RepositoryTypes REPOSITORY_TYPE = RepositoryTypes.FileRepository; // This repository type;
    private static final Logger LOGGER = LoggerFactory.getLogger(FileRepository.class); // Logger;
    private BufferedReader contentReader;
    @Getter private boolean isInitialized = false; // Initialized state:
    private File repositoryObject;
    private final ActualProperties actualProperties = new ActualProperties();


    /**
     * Get current singleton instance of this class.
     * @return - singleton instance.
     */
    public static FileRepository getInstance() {
        if (FileRepository.INSTANCE == null) FileRepository.INSTANCE = new FileRepository();
        return INSTANCE;
    }

    /**
     * Open reader/writer for specified properties file.
     * @param objects - {@link File} properties file as first element in array.
     * @throws RepositoryNotInitializedException - If IOException occurs.
     */
    @Override
    public void init(Object... objects) throws RepositoryNotInitializedException {
        LOGGER.debug("Init current [FileRepository] singleton instance:");

        try {
            // Get argument, cast and check it:
            Objects.requireNonNull(objects[0], "File instance must be not null.");
            File file = (File) objects[0];
            LOGGER.debug(String.format("Properties file: [%s];", file));

            // Open reader, writer:
            LOGGER.debug("Open reader for properties file:");
            this.contentReader = IOUtils.openReader(file);
            this.contentReader.mark(0);
            LOGGER.debug("Open reader for properties file: SUCCESS;");

            // Map parameter:
            this.repositoryObject = file;

            // Set initialized flag:
            this.isInitialized = true;

            // Read all properties from repository:
            if (!this.actualProperties.isLoaded()) this.actualProperties.load(this.list());

        }catch (ClassCastException | IOException e) {
            throw new RepositoryNotInitializedException(RepositoryTypes.FileRepository, objects[0], e.getMessage());
        }catch (NullPointerException e) {
            throw new RepositoryNotInitializedException(e.getMessage());
        }
    }

    /**
     * Close reader/writer for properties file.
     * @throws Exception - IF IOException occur.
     */
    @Override
    public void close() throws Exception {
        LOGGER.debug("Close current [FileRepository] singleton instance:");
        LOGGER.debug("Close reader/writers for properties file:");
        this.contentReader.close();
        LOGGER.debug("Close reader/writers for properties file: SUCCESS;");

        // Reset ActualProperties:
        this.actualProperties.reset();
    }

    public List<Property> getActualProperties() {
        return this.actualProperties.getMemoryProperties();
    }

    /**
     * Save changes in repository.
     */
    @Override
    public void flush() {
        LOGGER.debug("[FileRepository] actually not work with file system files, use [FileRepositoryContentDecorator] to flush changes in file system files.");
    }

    /**
     * Read all properties from properties file.
     * @return - Set of properties.
     * @throws IOException - If IO Exception occurs.
     */
    @Override
    public List<Property> list() throws IOException {

        // Check if the repository is initialized:
        if (!this.isInitialized) throw new RepositoryNotInitializedException(REPOSITORY_TYPE, repositoryObject);

        // Check if properties already loaded:
        // Then return actual properties list:
        if(this.actualProperties.isLoaded()) return this.actualProperties.getMemoryProperties();

        // Result set:
        List<Property> set = new ArrayList<>();
        // Read file content:
        List<String> fileContent = FileRepositoryUtils.readContentByLines(this.contentReader);
        // Iterate through file content line by line:
        fileContent.forEach((line) -> {
            try { // Try to parse Property object:
                Property readProperty = Property.parse(line);
                if (readProperty != null) {
                    // Add property to result set:
                    set.add(readProperty);
                }
            }catch (RuntimeException e) { // Skip property;
            }
        });

        // Return read property list:
        return set;
    }

    @Override
    public void updateKeys(@Nullable Map<String, String> aKeysChanges) {
        // Check parameter:
        if (aKeysChanges == null) {
            LOGGER.debug("Map of keys updates in null. Nothing to do.");
            return;
        }
        if (aKeysChanges.isEmpty()) {
            LOGGER.debug("Map of keys updates is empty. Nothing to do.");
            return;
        }
        LOGGER.debug("Try to update properties keys in memory properties repository:");
        LOGGER.debug(String.format("Expect [%d] keys updates;", aKeysChanges.size()));

        // Update properties in memory table:
        // Iterate through keys changes list:
        aKeysChanges.forEach((originKey, actualKey) -> {
            // If actual properties contains key, replace with new property:
            if (this.actualProperties.contains(originKey)) {
                Property changedProperty = this.actualProperties.changePropertyKey(originKey, actualKey);
                if (changedProperty != null) LOGGER.debug(String.format("New property for origin property key [%s] is [%s];", originKey, changedProperty));
            }
        });

    }


}
