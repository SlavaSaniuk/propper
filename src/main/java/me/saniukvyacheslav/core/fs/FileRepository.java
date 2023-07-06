package me.saniukvyacheslav.core.fs;

import me.saniukvyacheslav.core.prop.Property;
import me.saniukvyacheslav.core.prop.PropertyNotFoundException;

import java.io.*;
import java.util.List;

/**
 * FileRepository used for work with properties files.
 * FileRepository class implement {@link Repository} interface with CRUD methods.
 * NOTE: After work with property file, FileRepository must be CLOSED.
 */
public class FileRepository implements Repository, Closeable {

    private final File propertiesFile; // Property file;
    private final BufferedReader bufferedReader; // File reader;

    /**
     * Construct new File repository based on properties file.
     * @param aPropertiesFile - property file.
     */
    public FileRepository(File aPropertiesFile) {

        // Check property file:
        if(!aPropertiesFile.exists()) throw new IllegalArgumentException(String.format("Property file [%s] is not exist.", aPropertiesFile.getPath()));
        if(aPropertiesFile.isDirectory()) throw new IllegalArgumentException(String.format("[%s] is directory.", aPropertiesFile.getPath()));
        if(!aPropertiesFile.canRead()) throw new IllegalArgumentException(String.format("Property file [%s] cannot be read.", aPropertiesFile.getPath()));
        this.propertiesFile = aPropertiesFile;

        // Open reader and writer:
        try {
            this.bufferedReader = new BufferedReader(new FileReader(this.propertiesFile));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(String.format("Property file [%s] not found.", this.propertiesFile.getPath()));
        }
    }

    /**
     * Construct new FileRepository object based on property file with specified path.
     * @param aPropertyFilePath - path to property file.
     * @return - created FileRepository.
     */
    public static FileRepository withPropertyFile(String aPropertyFilePath) {

        if (aPropertyFilePath == null) throw new IllegalArgumentException("Property file path cannot be null.");
        if ((aPropertyFilePath.isEmpty())) throw new IllegalArgumentException("Property file path cannot be empty.");

        return new FileRepository(new File(aPropertyFilePath));
    }

    /**
     * Close inner buffered reader and writer.
     * @throws IOException - If IO exception occurs.
     */
    @Override
    public void close() throws IOException {
        if (this.bufferedReader != null) this.bufferedReader.close();
    }

    @Override
    public Property create(Property aProperty) {
        return null;
    }

    @Override
    public Property read(String aKey) throws PropertyNotFoundException, IOException {
        String readedStr;
        Property resultProperty;

        while ((readedStr = this.bufferedReader.readLine()) != null) {
            if (readedStr.startsWith(aKey)) {
            String[] keyValuePair = readedStr.split("[=]");
            return new Property(keyValuePair[0], keyValuePair[1]);
            }
        }
        throw new PropertyNotFoundException(aKey);
    }

    @Override
    public Property update(String aKey, String aNewValue) {
        return null;
    }

    @Override
    public Property delete(String aKey) {
        return null;
    }

    @Override
    public List<Property> list() {
        return null;
    }


}
