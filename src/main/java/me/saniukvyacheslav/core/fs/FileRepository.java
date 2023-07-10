package me.saniukvyacheslav.core.fs;

import me.saniukvyacheslav.core.prop.Property;
import me.saniukvyacheslav.core.prop.PropertyWrapper;

import java.io.*;

/**
 * FileRepository used for work with properties files.
 * FileRepository class implement {@link Repository} interface with CRUD methods.
 */
public class FileRepository implements Repository {

    private final File propertiesFile; // Property file;

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
     * Check whether properties file is writable.
     * @return - true, if properties file is writable.
     */
    public boolean isWritable() {
        return this.propertiesFile.canWrite();
    }

    @Override
    public Property create(Property aProperty) throws IOException {

        // Check property
        PropertyWrapper.checkProperty(aProperty);

        // Read file content before:
        StringBuilder sb = this.readFileContent();
        sb.append(aProperty);

        // Open writer:
        try (BufferedWriter writer = this.openWriter()) {
            writer.write(sb.toString());
            writer.flush();
        }

        return this.read(aProperty.getPropertyKey());

    }

    @Override
    public Property read(String aKey) throws IOException {
        Property resultProperty; // Result property (may be null);

        String readedStr;
        try(BufferedReader reader = this.openReader()) {
            while ((readedStr = reader.readLine()) != null) {
                if (readedStr.startsWith(aKey)) {
                    String[] keyValuePair = readedStr.split("=");

                    // Set property key:
                    resultProperty = new Property(keyValuePair[0], null);

                    // Check if property value not empty, and set property value:
                    if (keyValuePair.length == 1) resultProperty.setPropertyValue("");
                    else resultProperty.setPropertyValue(keyValuePair[1]);

                    // return founded property:
                    return resultProperty;
                }
            }
        }

        // If property not with specified key not found in file, then return null:
        return null;
    }

    @Override
    public Property update(String aKey, String aNewValue) {



        return null;
    }

    @Override
    public Property delete(String aKey) {
        return null;
    }


    private BufferedWriter openWriter() throws IOException {
        // Check if properties file is writable before:
        if (!this.isWritable()) throw new IOException(String.format("Properties file [%s] is not writable.", this.propertiesFile.getPath()));
        return new BufferedWriter(new FileWriter(this.propertiesFile));
    }

    private BufferedReader openReader() throws IOException {
        return new BufferedReader(new FileReader(this.propertiesFile));
    }

    private StringBuilder readFileContent() throws IOException {
        StringBuilder sb = new StringBuilder();
        String str;

        try (BufferedReader reader = this.openReader()) {
            while ((str = reader.readLine()) != null) {
                sb.append(str).append("\n");
            }
        }

        return sb;
    }


}
