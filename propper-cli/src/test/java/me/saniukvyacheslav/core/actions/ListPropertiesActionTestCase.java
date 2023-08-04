package me.saniukvyacheslav.core.actions;

import me.saniukvyacheslav.args.actions.ActionResult;
import me.saniukvyacheslav.repositories.AdvancedRepository;
import me.saniukvyacheslav.repositories.FileRepository;
import me.saniukvyacheslav.prop.Property;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListPropertiesActionTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListPropertiesActionTestCase.class); // Logger;

    // Global tests variables:
    private static File propertiesFile;
    private AdvancedRepository advancedRepository;
    private final List<Property> listOfTestProperties = new ArrayList<>();

    @BeforeAll
    static void beforeAll() throws IOException {
        propertiesFile = new File("list-properties-action.properties");
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

        // Construct new properties and add it to list:
        this.listOfTestProperties.add(new Property("property.key.1", "property.value.1"));
        this.listOfTestProperties.add(new Property("my.name", "Vyacheslav"));
        this.listOfTestProperties.add(new Property("tree.color", "green"));

        // Save properties to test file:
        this.listOfTestProperties.forEach((property) -> {
            try {
                this.advancedRepository.create(property);
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
                this.advancedRepository.delete(property.getPropertyKey());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

        // Clear properties list:
        this.listOfTestProperties.clear();

        this.advancedRepository = null;
    }

    @Test
    void doAction_linkOnPropertiesFile_shouldPrintAllPropertiesFromFile() {
        String[] args = {"/L", propertiesFile.getPath()};
        ActionResult result = new ListPropertiesAction().doAction(Arrays.asList(args));

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isException());
        LOGGER.info(String.format("doAction_list_result: \n [%s]", result.getActionResult()));
    }
}
