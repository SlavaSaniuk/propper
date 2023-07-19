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

public class AdvancedRepositoryTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdvancedRepositoryTestCase.class); // Logger;

    // Global tests variables:
    private static File propertiesFile;
    private Repository crudRepository;
    private AdvancedRepository advancedRepository;
    private final List<Property> listOfTestProperties = new ArrayList<>();

    @BeforeAll
    static void beforeAll() throws IOException {
        propertiesFile = new File("advanced-repository-tests.properties");
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
        this.advancedRepository = new FileRepository(propertiesFile);
        this.crudRepository = new FileRepository(propertiesFile);

        // Construct new properties and add it to list:
        this.listOfTestProperties.add(new Property("property.key.1", "property.value.1"));
        this.listOfTestProperties.add(new Property("my.name", "Vyacheslav"));
        this.listOfTestProperties.add(new Property("tree.color", "green"));

        // Save properties to test file:
        this.listOfTestProperties.forEach((property) -> {
            try {
                this.crudRepository.create(property);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @AfterEach
    void afterEach() {
        // Delete all created property:
        this.listOfTestProperties.forEach((property -> {
            try {
                this.crudRepository.delete(property.getPropertyKey());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

        // Clear properties list:
        this.listOfTestProperties.clear();

        this.crudRepository = null;
        this.advancedRepository = null;
    }

    @Test
    void list_propertiesFileHas3Properties_shouldReturnListOfProperties() {

        // Read list of properties:
        try {
            List<Property> testList = this.advancedRepository.list();
            Assertions.assertNotEquals(0, testList.size());
            Assertions.assertEquals(this.listOfTestProperties.size(), testList.size());

            testList.forEach((property -> LOGGER.debug(String.format("Read property: [%s];", property))));

        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void map_propertiesFileHas3Properties_shouldReturnMapOfProperties() {

        // Read list of properties:
        try {
            Map<String, String> testMap = this.advancedRepository.map();
            Assertions.assertNotEquals(0, testMap.size());
            Assertions.assertEquals(this.listOfTestProperties.size(), testMap.size());

            testMap.forEach(((key, value) -> LOGGER.debug(String.format("Read property: [%s=%s];", key, value))));

        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
