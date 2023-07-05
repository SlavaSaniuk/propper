package me.saniukvyacheslav.core.fs;

import java.io.File;

public class FileRepository {

    private final File propertiesFile;

    /**
     * Construct new File repository based on properties file.
     * @param aPropertiesFile - property file.
     */
    public FileRepository(File aPropertiesFile) {

        // Check property file:
        if(!aPropertiesFile.exists()) throw new IllegalArgumentException(String.format("Property file [%s] is not exist.", aPropertiesFile.getPath()));
        if(!aPropertiesFile.isDirectory()) throw new IllegalArgumentException(String.format("[%s] is directory.", aPropertiesFile.getPath()));
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



}
