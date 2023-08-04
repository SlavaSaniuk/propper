package me.saniukvyacheslav.fs;

import me.saniukvyacheslav.prop.Property;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExtendedCrudRepositoryTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtendedCrudRepositoryTestCase.class); // Logger;

    // Global tests variables:
    private static File propertiesFile;
    private Repository crudRepository;
    private ExtendedCrudRepository extendedCrudRepository;

    @BeforeAll
    static void beforeAll() throws IOException {
        propertiesFile = new File("tests12sd.fil");
        boolean isCreated = propertiesFile.createNewFile();
        if (!isCreated) throw new RuntimeException("Test properties file didn't created.");
        if (!propertiesFile.exists()) throw new RuntimeException("Test properties file didn't created.");
    }

    @AfterAll
    static void afterAll() {
        boolean isDeleted = propertiesFile.delete();
        if (!isDeleted) throw new RuntimeException("Test properties file didn't deleted.");
        if (propertiesFile.exists()) throw new RuntimeException("Test properties file didn't deleted.");
    }

    @BeforeEach
    void beforeEach() {
        // Create instance of tested repository:
        this.extendedCrudRepository = new FileRepository(propertiesFile);
        this.crudRepository = new FileRepository(propertiesFile);
    }

    @AfterEach
    void afterEach() {
        this.crudRepository = null;
        this.extendedCrudRepository = null;
    }

    @Test
    void save_propertiesList_shouldSavItInRepositoryAndReturnList() {
        // Properties to save:
        Property prop1 = new Property("property.key.1","property.value.1");
        Property prop2 = new Property("property.key.2","property.value.2");
        Property prop3 = new Property("property.key.3", "property.value.3");
        List<Property> validProperties = new ArrayList<>();
        validProperties.add(prop1);
        validProperties.add(prop2);
        validProperties.add(prop3);

        // Save properties:
        try {
            List<Property> testResult = this.extendedCrudRepository.save(validProperties);
            Assertions.assertNotNull(testResult);
            Assertions.assertEquals(validProperties.size(), testResult.size());
            LOGGER.debug(String.format("Saved properties list: [%s];", testResult));
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        // Read properties from file:
        validProperties.forEach((property) -> {
            try {
                Property readProperty = this.crudRepository.read(property.getPropertyKey());
                Assertions.assertNotNull(readProperty);
                Assertions.assertEquals(property, readProperty);
                LOGGER.debug(String.format("Read property: [%s];", readProperty));
            } catch (IOException e) {
                Assertions.fail(e.getMessage());
            }
        });
    }

    @Test
    void save_somePropertiesInListAreInvalid_shouldSkipIt() {
        // Properties to save:
        Property prop1 = new Property("property.key.1","property.value.1");
        Property prop3 = new Property("property.key.3", "property.value.3");
        List<Property> validProperties = new ArrayList<>();
        validProperties.add(prop1);
        validProperties.add(null);
        validProperties.add(prop3);

        // Save properties:
        try {
            List<Property> testResult = this.extendedCrudRepository.save(validProperties);
            Assertions.assertNotNull(testResult);
            Assertions.assertEquals(2, testResult.size());
            LOGGER.debug(String.format("Saved properties list: [%s];", testResult));
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        // Read properties from file:
        for (Property property : validProperties) {
            try {
                if (property == null) continue;
                Property readProperty = this.crudRepository.read(property.getPropertyKey());
                Assertions.assertNotNull(readProperty);
                Assertions.assertEquals(property, readProperty);
                LOGGER.debug(String.format("Read property: [%s];", readProperty));
            } catch (IOException e) {
                Assertions.fail(e.getMessage());
            }
        }
    }

    @Test
    void save_listIsNull_shouldSkipIt() {
        // Save properties:
        try {
            List<Property> testResult = this.extendedCrudRepository.save(null);
            Assertions.assertNotNull(testResult);
            Assertions.assertEquals(0, testResult.size());
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void readByKeys_listOfKeys_shouldReturnReadListOfProperties() {
        // Properties to save:
        Property prop1 = new Property("readbykeys.property.key.1","property.value.1");
        Property prop2 = new Property("readbykeys.property.key.2","property.value.2");
        Property prop3 = new Property("readbykeys.property.key.3", "property.value.3");
        List<Property> validProperties = new ArrayList<>();
        validProperties.add(prop1);
        validProperties.add(prop2);
        validProperties.add(prop3);

        // Save properties:
        try {
            List<Property> testResult = this.extendedCrudRepository.save(validProperties);
            Assertions.assertNotNull(testResult);
            Assertions.assertEquals(validProperties.size(), testResult.size());
            LOGGER.debug(String.format("Saved properties list: [%s];", testResult));
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        // Read properties:
        try {
            List<String> keys = new ArrayList<>();
            keys.add(prop1.getPropertyKey());
            keys.add(prop2.getPropertyKey());
            keys.add(prop3.getPropertyKey());
            List<Property> readProperties = this.extendedCrudRepository.readByKeys(keys);
            Assertions.assertNotNull(readProperties);
            Assertions.assertEquals(3, readProperties.size());
            LOGGER.debug(String.format("Read list: [%s];", readProperties));
        }catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void readByKeys_someKeysAreInvalid_shouldSkipIt() {
        // Properties to save:
        Property prop1 = new Property("readbykeys2.property.key.1","property.value.1");
        Property prop2 = new Property("readbykeys2.property.key.2","property.value.2");
        Property prop3 = new Property("readbykeys2.property.key.3", "property.value.3");
        List<Property> validProperties = new ArrayList<>();
        validProperties.add(prop1);
        validProperties.add(prop2);
        validProperties.add(prop3);

        // Save properties:
        try {
            List<Property> testResult = this.extendedCrudRepository.save(validProperties);
            Assertions.assertNotNull(testResult);
            Assertions.assertEquals(validProperties.size(), testResult.size());
            LOGGER.debug(String.format("Saved properties list: [%s];", testResult));
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        // Read properties:
        try {
            List<String> keys = new ArrayList<>();
            keys.add(prop1.getPropertyKey());
            keys.add(null);
            keys.add("");
            List<Property> readProperties = this.extendedCrudRepository.readByKeys(keys);
            Assertions.assertNotNull(readProperties);
            Assertions.assertEquals(1, readProperties.size());
            LOGGER.debug(String.format("Read list: [%s];", readProperties));
        }catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void read_listOfProperties_shouldReturnReadListOfProperties() {
        // Properties to save:
        Property prop1 = new Property("read2.property.key.1","property.value.1");
        Property prop2 = new Property("read2.property.key.2","property.value.2");
        Property prop3 = new Property("read2.property.key.3", "property.value.3");
        List<Property> validProperties = new ArrayList<>();
        validProperties.add(prop1);
        validProperties.add(prop2);
        validProperties.add(prop3);

        // Save properties:
        try {
            List<Property> testResult = this.extendedCrudRepository.save(validProperties);
            Assertions.assertNotNull(testResult);
            Assertions.assertEquals(validProperties.size(), testResult.size());
            LOGGER.debug(String.format("Saved properties list: [%s];", testResult));
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        // Read properties:
        try {
            List<Property> readProperties = this.extendedCrudRepository.read(validProperties);
            Assertions.assertNotNull(readProperties);
            Assertions.assertEquals(3, readProperties.size());
            LOGGER.debug(String.format("Read list: [%s];", readProperties));
        }catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    private List<Property> generateTestProperties(String aPrefixForKeys, int aSize) {
        List<Property> testProperties = new ArrayList<>();

        // Generate property instances:
        String propertyKey;
        for (int i=1; i<=aSize; i++) {
            propertyKey = String.format("%s.property.key.%d", aPrefixForKeys, i);
            testProperties.add(new Property(propertyKey, "property.value." +i));
        }

        // Test properties list:
        return testProperties;

    }

    @Test
    void update_listOfProperties_shouldReturnListOfUpdatedProperties() {
        // Generate test properties:
        List<Property> testProperties = this.generateTestProperties("update1", 10);
        // Save test properties:
        try {
            List<Property> savedProperties = this.extendedCrudRepository.save(testProperties);
            LOGGER.debug(String.format("Saved properties list: [%s];", savedProperties));
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        // Change tests properties:
        // Update 1, 3, 5 properties:
        String newProperty1Value = "new-property-value-1";
        testProperties.get(1).setPropertyValue(newProperty1Value);
        String newProperty3Value = "new-property-value-1";
        testProperties.get(3).setPropertyValue(newProperty3Value);
        String newProperty5Value = "new-property-value-1";
        testProperties.get(5).setPropertyValue(newProperty5Value);
        // Update it in file:
        try {
            List<Property> updatedProperties = this.extendedCrudRepository.update(testProperties);
            Assertions.assertNotNull(updatedProperties);
            Assertions.assertEquals(3, updatedProperties.size());
            LOGGER.debug(String.format("Updated properties list: [%s];", updatedProperties));
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        // Read properties by keys:
        List<String> keys = new ArrayList<>();
        testProperties.forEach((property) -> keys.add(property.getPropertyKey()));
        try {
            List<Property> readProperties = this.extendedCrudRepository.readByKeys(keys);
            Assertions.assertNotNull(readProperties);
            Assertions.assertEquals(testProperties.size(), readProperties.size());
            // Convert readProperties to map:
            Map<String, String> keyValuePair = readProperties.stream()
                    .collect(Collectors.toMap(Property::getPropertyKey, Property::getPropertyValue));
            // Check values:
            testProperties.forEach((testProperty) -> {
                Assertions.assertTrue(keyValuePair.containsKey(testProperty.getPropertyKey()));
                Assertions.assertEquals(testProperty.getPropertyValue(), keyValuePair.get(testProperty.getPropertyKey()));
            });
            LOGGER.debug(String.format("Read properties list: [%s];", readProperties));
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

}
