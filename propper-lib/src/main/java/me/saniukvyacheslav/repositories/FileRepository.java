package me.saniukvyacheslav.repositories;

import me.saniukvyacheslav.exceptions.PropertyIsInvalidException;
import me.saniukvyacheslav.prop.Property;
import me.saniukvyacheslav.prop.PropertyWrapper;

import javax.annotation.Nullable;
import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * FileRepository used for work with properties files.
 * FileRepository class implement {@link Repository} interface with CRUD methods.
 */
public class FileRepository implements AdvancedRepository {

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
    public void create(Property aProperty) throws IOException {
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

        this.read(aProperty.getPropertyKey());

    }

    @Override
    public Property read(String aKey) throws IOException {
        String readedStr;
        try(BufferedReader reader = this.openReader()) {
            while ((readedStr = reader.readLine()) != null) {
                if (readedStr.startsWith(aKey)) return FileRepository.parsePropertyString(readedStr);
            }
        }
        // If property not with specified key not found in file, then return null:
        return null;
    }

    @Override
    public void update(String aKey, String aNewValue) throws IOException {

        // Check property key:
        PropertyWrapper.checkPropertyKey(aKey);

        // Read file content:
        StringBuilder fileContent = this.readFileContent();

        // Convert string builder to list of strings:
        String[] strings = fileContent.toString().split("\n");
        List<String> strList = Arrays.asList(strings);

        // Write file content and find property:
        try(BufferedWriter writer = this.openWriter()) {
            StringBuilder newFileContent = new StringBuilder();

            // Iterate through list:
            strList.forEach((str) -> {
                // Find str which start with key:
                // If string founded, then replace with new string: aKey=aNewValue
                if (str.startsWith(aKey)) str = aKey +"=" +aNewValue;

                // Add to sb:
                newFileContent.append(str).append("\n");
            });

            writer.write(newFileContent.toString());
            writer.flush();
        }

        this.read(aKey);
    }

    @Override
    public void delete(String aKey) throws IOException {
        // Check property key:
        PropertyWrapper.checkPropertyKey(aKey);

        // Read file content:
        StringBuilder fileContent = this.readFileContent();
        // Convert string builder to list of strings:
        String[] strings = fileContent.toString().split("\n");
        List<String> strList = Arrays.asList(strings);


        // Open file writer
        try(BufferedWriter writer = this.openWriter()) {
            StringBuilder newFileContent = new StringBuilder();
            // Iterate through file content:
            strList.forEach((str) -> {
                // Find specified property key and not add it to result file content
                if (!str.startsWith(aKey)) newFileContent.append(str).append("\n");
                else newFileContent.append("\n");
            });

            // Write changes:
            writer.write(newFileContent.toString());
            writer.flush();
        }
    }

    @Override
    public List<Property> list() throws IOException {

        List<Property> propertyList = new ArrayList<>();

        // Read properties file:
        String readLine;
        try(BufferedReader reader = this.openReader()) {
            while ((readLine = reader.readLine()) != null) {
                if (readLine.isEmpty()) continue;

                try {
                    Property readProperty = Property.parse(readLine);
                    if (readProperty != null) propertyList.add(readProperty);
                }catch (PropertyIsInvalidException e) {
                    // Skip read string;
                }
            }
        }

        // Return read property list:
        return propertyList;
    }

    @Override
    public Map<String, String> map() throws IOException {
        // Result map:
        Map<String, String> resultMap = new HashMap<>();

        // Read property:
        List<Property> list = this.list();
        list.forEach(Property -> resultMap.put(Property.getPropertyKey(), Property.getPropertyValue()));

        // Return result map:
        return resultMap;
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

    public static Property parsePropertyString(String aStr) {
        String[] keyValuePair = aStr.split("=");

        // Create property object:
        if (keyValuePair.length == 0) return null;
        else if (keyValuePair.length == 1) return new Property(keyValuePair[0], null);
        else return new Property(keyValuePair[0], keyValuePair[1]);

    }

    /**
     * Save list of {@link Property} properties in repository.
     * If any property in list is invalid, method skip it, and not save it in repository.
     * If specified list is null or empty, method skip it, and return new empty list.
     * @param aPropertiesList - list of {@link Property} properties.
     * @return - list of saved properties.
     * @throws IOException - If IO exceptions occur.
     */
    @Override
    public List<Property> save(@Nullable List<Property> aPropertiesList) throws IOException {

        // Result list:
        List<Property> checkedProperties = new ArrayList<>();

        // Check specified properties list:
        if (aPropertiesList == null) return checkedProperties;
        if (aPropertiesList.size() == 0) return checkedProperties;

        // Read file content before:
        StringBuilder fileContent = this.readFileContent();

        // Iterate through properties list:
        for (int i=0; (i<aPropertiesList.size()); i++) {
            Property property = aPropertiesList.get(i);
            // Check property before:
            try {
                PropertyWrapper.checkProperty(property);
            }catch (PropertyIsInvalidException e) {
                // Skip property if it's invalid:
                continue;
            }

            // Append properties to end of file:
            // If last property:
            if (i == (aPropertiesList.size()-1)) fileContent.append(property);
            else fileContent.append(property).append("\n");
            checkedProperties.add(property);
        }

        // Write new file content:
        // Open writer:
        try (BufferedWriter writer = this.openWriter()) {
            writer.write(fileContent.toString());
            writer.flush();
        }

        // Saved properties:
        return checkedProperties;
    }

    /**
     * Read specified properties from repository.
     * This method used {@link ExtendedCrudRepository#readByKeys(List)} method under the hood.
     * @param aPropertiesList - list of properties will be read.
     * @return - list of read properties.
     * @throws IOException - If IO exceptions occur.
     */
    @Override
    public List<Property> read(@Nullable List<Property> aPropertiesList) throws IOException {
        // Check specified list:
        if (aPropertiesList == null) return null;
        // List of properties keys:
        List<String> propertiesKeys = new ArrayList<>();
        if (aPropertiesList.isEmpty()) return this.readByKeys(propertiesKeys); // Return empty list;

        // Check properties in list:
        aPropertiesList.stream().filter(Objects::nonNull).forEach((property -> propertiesKeys.add(property.getPropertyKey())));

        // Read by keys:
        return this.readByKeys(propertiesKeys);
    }

    /**
     * Read properties from repository by properties keys.
     * If any key in specified list is invalid, method skip it.
     * If specified list is null or empty, method return null or empty list.
     * @param aPropertiesKeys - list of properties keys.
     * @return - list of read properties.
     * @throws IOException - If IO exceptions occur.
     */
    @Override
    public List<Property> readByKeys(@Nullable List<String> aPropertiesKeys) throws IOException {

        // Result list:
        List<Property> resultList = new ArrayList<>();

        // Check specified list:
        if (aPropertiesKeys == null) return null;
        if (aPropertiesKeys.isEmpty()) return resultList;
        List<String> checkedKeys = new ArrayList<>();
        for (String propertyKey: aPropertiesKeys) {
            // Check key:
            try {
                PropertyWrapper.checkPropertyKey(propertyKey);
            } catch (PropertyIsInvalidException exc) {
                // Skip property:
            }
            checkedKeys.add(propertyKey);
        }

        // Get properties list:
        List<Property> allProperties = this.list();
        // Iterate through all read properties:
        allProperties.forEach((property -> {
            if (checkedKeys.contains(property.getPropertyKey()))
                resultList.add(property);
        }));

        // Result list:
        return resultList;
    }

    @Override
    public List<Property> update(@Nullable List<Property> aPropertiesList) throws IOException {
        // Result list:
        List<Property> resultList = new ArrayList<>();

        // Check specified list:
        if (aPropertiesList == null) return null;
        if (aPropertiesList.isEmpty()) return resultList;

        // Convert specified list to map of property_key=new_property_value:
        Map<String, String> key_newValue_Pair = aPropertiesList.stream().collect(Collectors.toMap((Property::getPropertyKey), (Property::getPropertyValue)));

        // Read file content:
        StringBuilder fileContent = this.readFileContent();
        // Convert string builder to array of strings:
        String[] strings = fileContent.toString().split("\n");

        // New file content:
        StringBuilder newFileContent = new StringBuilder();
        // Iterate through old file content:
        for (String str : strings) {
            // Try to parse string to Property:
            try {
                Property parsedProperty = Property.safeParse(str);
                // If parsed string is property:
                // Check for update for parsed property:
                if (key_newValue_Pair.containsKey(parsedProperty.getPropertyKey())) {
                    // Check for origin property value is not equal to new property value:
                    if (!(parsedProperty.getPropertyValue().equals(key_newValue_Pair.get(parsedProperty.getPropertyKey())))) {
                        parsedProperty.setPropertyValue(key_newValue_Pair.get(parsedProperty.getPropertyKey()));
                        newFileContent.append(parsedProperty).append(System.lineSeparator());
                        resultList.add(parsedProperty);
                    }else newFileContent.append(parsedProperty).append(System.lineSeparator());
                } else newFileContent.append(str).append(System.lineSeparator());

            } catch (ParseException e) {
                // If string cannot be parsed to property, add it to new file content:
                newFileContent.append(str).append(System.lineSeparator());
            }
        }

        // Write new file content to file:
        // Write file content and find property:
        try(BufferedWriter writer = this.openWriter()) {
            writer.write(newFileContent.toString());
            writer.flush();
        }

        return resultList;
    }

}