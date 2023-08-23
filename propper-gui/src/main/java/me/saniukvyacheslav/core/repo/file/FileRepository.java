package me.saniukvyacheslav.core.repo.file;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.saniukvyacheslav.annotation.pattern.Singleton;
import me.saniukvyacheslav.core.RootConfiguration;
import me.saniukvyacheslav.core.property.ExtendedBaseProperty;
import me.saniukvyacheslav.core.repo.PropertiesRepository;
import me.saniukvyacheslav.core.repo.RepositoryTypes;
import me.saniukvyacheslav.core.repo.exception.RepositoryNotInitializedException;
import me.saniukvyacheslav.core.store.FilePropertiesStore;
import me.saniukvyacheslav.definition.Closeable;
import me.saniukvyacheslav.definition.Initializable;
import me.saniukvyacheslav.prop.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.*;
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
    @Getter private boolean isInitialized = false; // Initialized state:
    @Getter private File repositoryObject;
    @Getter private FilePropertiesStore filePropertiesStore; // Properties store instance:

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
        LOGGER.debug(" Try to init current [FileRepository] singleton instance. Check parameters:");

        // Cast arguments and check it:
        try {
            // File:
            Objects.requireNonNull(objects[0], "File [objects[0] argument] instance must be not null.");
            File file = (File) objects[0]; // Cast;
            LOGGER.debug(String.format("File: [%s];", file.getAbsolutePath()));

            // Try to init:
            this.initFileRepository(file);

        }catch (ClassCastException | IOException e) {
            throw new RepositoryNotInitializedException(RepositoryTypes.FileRepository, objects[0], e.getMessage());
        }catch (NullPointerException e) {
            throw new RepositoryNotInitializedException(e.getMessage());
        }
    }

    /**
     * Init this FileRepository repository with file and store.
     * @param aFile - properties file.
     * @throws IOException - If IO Exception occurs.
     */
    private void initFileRepository(File aFile) throws IOException {
        LOGGER.debug("Try to init this [FileRepository] instance with [File] argument:");
        // Check parameters:
        Objects.requireNonNull(aFile, "File [aFile] instance must be not null.");

        // Try to open reader:
        LOGGER.debug(String.format("Try to open reader for file [%s]:", aFile.getAbsolutePath()));
        try {
            BufferedReader reader = this.openReader(aFile);
            reader.close();
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        }

        // Set initialized flag:
        this.isInitialized = true;
        this.repositoryObject = aFile;
        LOGGER.debug("This repository was initialized;");

        // Init FilePropertiesStore:
        RootConfiguration.getInstance().initPropertiesStore(FilePropertiesStore.getInstance());
        this.filePropertiesStore = (FilePropertiesStore) RootConfiguration.getInstance().getPropertiesStore();


        // Load properties to store:
        LOGGER.debug("Try to load properties in [PropertiesStore]:");
        this.filePropertiesStore.load(this.list());
    }

    /**
     * Open reader for file.
     * @param aFile - file to be read.
     * @return - reader.
     * @throws FileNotFoundException - If file not found.
     */
    private BufferedReader openReader(File aFile) throws FileNotFoundException {
        return new BufferedReader(new FileReader(aFile));
    }

    /**
     * Close reader/writer for properties file.
     * @throws Exception - IF IOException occur.
     */
    @Override
    public void close() throws Exception {
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
     * @return - List of properties.
     * @throws IOException - If IO Exception occurs.
     */
    @Override
    public List<Property> list() throws IOException {

        // Check if the repository is initialized:
        if (!this.isInitialized) throw new RepositoryNotInitializedException(REPOSITORY_TYPE, repositoryObject);

        // Check if properties already loaded:
        if(this.filePropertiesStore.isLoaded()) return this.filePropertiesStore.getProperties();

        // Result set:
        List<Property> set = new ArrayList<>();
        // Read file content:
        List<String> fileContent = FileRepositoryUtils.readContentByLines(this.openReader(this.repositoryObject));
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

    /**
     * Update properties keys in repository.
     * Method accept map of origin_property_key=new_property_key pairs.
     * @param aKeysChanges - map of origin_property_key-new_property_key pairs.
     */
    @Override
    public void updateKeys(@Nullable Map<String, String> aKeysChanges) throws IOException {
        LOGGER.debug("Try to update properties keys in [FileRepository] repository:");

        // If repository is not initialized:
        if (!(this.isInitialized)) throw new RepositoryNotInitializedException(RepositoryTypes.FileRepository, this.repositoryObject);
        // Check parameter:
        Objects.requireNonNull(aKeysChanges, "Map [aKeysChanges] must be not [null]");
        if (aKeysChanges.isEmpty()) return;
        LOGGER.debug(String.format("Expect [%d] keys updates;", aKeysChanges.size()));

        // Get stored properties:
        List<ExtendedBaseProperty> storedProperties = this.filePropertiesStore.getStored();
        // Update keys:
        aKeysChanges.forEach((original_key, actual_key) -> storedProperties.stream().filter(ebp -> ebp.getOriginalKey().equals(original_key)).findFirst()
                .ifPresent(extendedProperty -> {
                    LOGGER.debug(String.format("Update property key of property[%s] with new value: [%s];", extendedProperty.getOriginalKey(), actual_key));
                    extendedProperty.setActualProperty(new Property(actual_key, extendedProperty.getActualProperty().getPropertyValue()));
                }));

    }

    /**
     * Update properties value in repository.
     * Method accept map of property_key=new_property_value pairs.
     * @param aValueChanges - map of property_key=new_property_value pairs.
     */
    @Override
    public void updateValues(@Nullable Map<String, String> aValueChanges) throws IOException {
        LOGGER.debug("Try to update properties values in [FileRepository] repository:");
        // If repository is not initialized:
        if (!(this.isInitialized)) throw new RepositoryNotInitializedException(RepositoryTypes.FileRepository, this.repositoryObject);
        // Check parameters:
        Objects.requireNonNull(aValueChanges, "Map [aValueChanges] must be not null.");
        if (aValueChanges.isEmpty()) return;
        LOGGER.debug(String.format("Expect [%d] values updates;", aValueChanges.size()));

        // Get stored properties:
        List<ExtendedBaseProperty> storedProperties = this.filePropertiesStore.getStored();
        // Update keys:
        aValueChanges.forEach((original_key, changed_value) -> storedProperties.stream().filter(ebp -> ebp.getActualProperty().getPropertyKey().equals(original_key)).findFirst()
                .ifPresent(extendedProperty -> {
                    if (!extendedProperty.getActualProperty().getPropertyValue().equals(changed_value)) {
                        LOGGER.debug(String.format("Update property value of property[%s] with new value: [%s];", extendedProperty.getOriginalKey(), changed_value));
                        extendedProperty.getActualProperty().setPropertyValue(changed_value);
                    }
                }));
    }

}
