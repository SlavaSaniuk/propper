package me.saniukvyacheslav.core.fs;

import me.saniukvyacheslav.core.prop.Property;
import me.saniukvyacheslav.core.prop.PropertyAlreadyExistException;
import me.saniukvyacheslav.core.prop.PropertyNotFoundException;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@SuppressWarnings({"ResultOfMethodCallIgnored"})
public class FileRepositoryTestsCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRepositoryTestsCase.class);

    private File propertyTestFile;
    private File propertyTestFile2;
    private FileRepository testsFileRepository;
    private FileRepository testsFileRepository2;

    @BeforeEach
    void beforeEach() {
        try {
            this.propertyTestFile = new File("C:\\propertyTestFile.properties");
            this.propertyTestFile.createNewFile();
            LOGGER.debug("Tests property file [C:\\propertyTestFile.properties] is created.");

            this.propertyTestFile2 = new File("C:\\testsPropertiesFile2.properties");

            this.testsFileRepository = new FileRepository(this.propertyTestFile);
            this.testsFileRepository2 = new FileRepository(this.propertyTestFile2);
        } catch (IOException e) {
            LOGGER.error("Tests property file [C:\\propertyTestFile.properties] CAN NOT be created.");
        }

        LOGGER.debug("BEFORE_EACH_RESULT: Tests property file is CREATED;");
        LOGGER.debug("BEFORE_EACH_RESULT: Tests file repository is CREATED;");
    }

    @AfterEach
    void afterEach() {

        if(!this.propertyTestFile.delete()) LOGGER.error("Tests property file [C:\\propertyTestFile.properties] CAN NOT be deleted.");
        LOGGER.error("Tests file repository CAN NOt be closed.");

        LOGGER.debug("AFTER_EACH_RESULT: Tests file repository is CLOSED.");
        LOGGER.debug("AFTER_EACH_RESULT: Tests property file is DELETED.");
    }

    @Test
    void fileRepository_fileNotExist_shouldThrowIAE() {
        File testFile = new File("asdasdad.sadsa");
        Assertions.assertThrows(IllegalArgumentException.class, () -> new FileRepository(testFile));
    }

    @Test
    void fileRepository_fileIsDirectory_shouldThrowIAE() {
        File testFile = new File("C:\\Windows");
        Assertions.assertThrows(IllegalArgumentException.class, () -> new FileRepository(testFile));
    }

    @Test
    void withPropertyFile_fileIsExist_shouldReturnFileRepository() {
        File testFile = new File("C:\\tests.props");
        try {
            testFile.createNewFile();
            FileRepository fr = FileRepository.withPropertyFile(testFile.getPath());
            Assertions.assertNotNull(fr);
        } catch (IOException e) {
            Assertions.fail();
        } finally {
            testFile.delete();
        }
    }

    @Test
    void read_propertyIsExist_shouldReturnPropertyObject() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.propertyTestFile));
            String propKey = "test.property.key";
            String expectedValue = "test.property.value";
            writer.write("test.property.key=test.property.value\n");
            writer.write("hello=world\n");
            writer.flush();
            writer.close();

            Property property = this.testsFileRepository.read(propKey);
            Assertions.assertNotNull(property);
            Assertions.assertEquals(propKey, property.getPropertyKey());
            Assertions.assertEquals(expectedValue, property.getPropertyValue());

            LOGGER.info(String.format("Property [%s];", property));

        } catch (IOException e) {
            LOGGER.error("Cannot open testsPropertyFile for writing.");
            Assertions.fail();

        }
    }

    @Test
    void read_propertyWithoutValue_shouldReturnPropertyWithEmptyValue() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.propertyTestFile));
            String propKey = "test.property.key";
            String expectedValue = "";
            writer.write("test.property.key=\n");
            writer.write("hello=world\n");
            writer.flush();
            writer.close();

            Property property = this.testsFileRepository.read(propKey);
            Assertions.assertNotNull(property);
            Assertions.assertEquals(propKey, property.getPropertyKey());
            Assertions.assertEquals(expectedValue, property.getPropertyValue());

            LOGGER.info(String.format("Property [%s];", property));

        } catch (IOException e) {
            LOGGER.error("Cannot open testsPropertyFile for writing.");
            Assertions.fail();

        }
    }

    @Test
    void read_propertyValueIsNull_shouldReturnPropertyWithEmptyValue() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.propertyTestFile));
            String propKey = "test.property.key";
            writer.write("test.property.key=");
            writer.flush();
            writer.close();

            Property property = this.testsFileRepository.read(propKey);
            Assertions.assertNotNull(property);
            Assertions.assertEquals(propKey, property.getPropertyKey());
            Assertions.assertEquals("", property.getPropertyValue());

            LOGGER.info(String.format("Property [%s];", property));

        } catch (IOException e) {
            LOGGER.error("Cannot open testsPropertyFile for writing.");
            Assertions.fail();

        }
    }

    @Test
    void read_propertyIsNotExist_shouldThrowPropertyNotFoundException() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.propertyTestFile));
            writer.write("test.property.key=\n");
            writer.write("hello=world\n");
            writer.flush();
            writer.close();

            Assertions.assertThrows(PropertyNotFoundException.class,() -> this.testsFileRepository.read("not-existed-key"));

        } catch (IOException e) {
            LOGGER.error("Cannot open testsPropertyFile for writing.");
            Assertions.fail();
        }
    }

    @Test
    void isWritable_fileIsWritable_shouldReturnTrue() {
        Assertions.assertTrue(this.testsFileRepository.isWritable());
    }

    @Test
    void create_newProperty_shouldCreateNewPropertyInFile() {
        Property testProperty = new Property("my.position", "engineer");
        try {
            this.testsFileRepository2.create(testProperty);

            Property readedProperty = this.testsFileRepository2.read(testProperty.getPropertyKey());
            Assertions.assertNotNull(readedProperty);
            Assertions.assertEquals(testProperty.getPropertyKey(), readedProperty.getPropertyKey());
            Assertions.assertEquals(testProperty.getPropertyValue(), readedProperty.getPropertyValue());
            LOGGER.debug(String.format("Readed property: %s;", readedProperty));

        } catch (IOException e) {
            LOGGER.error(" IO Exception throws, when program try to create property in file.");
            LOGGER.error(e.getMessage());
            Assertions.fail();
        } catch (PropertyAlreadyExistException e) {
            LOGGER.debug(e.getMessage());
        }
    }

    @Test
    void create_propertyAlreadyExistInFile_shouldThrowPropertyAlreadyExistException_2() {
        String propertyKey = "test.create.2"; // Shared property key;

        // Create property in file:
        try {
            Property propertyToCreate = new Property(propertyKey, "value_1");
            this.testsFileRepository2.create(propertyToCreate);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        } catch (PropertyAlreadyExistException e) {
            LOGGER.debug("Property already created.");
        }

        // Try to create property with key, which already exist in repo:
        try {
            this.testsFileRepository2.create(new Property(propertyKey, "value_2"));
            Assertions.fail("Property with key, which already exist in repo, created again.");
        } catch (PropertyAlreadyExistException e) {
            LOGGER.info("Success. Exception [PropertyAlreadyExistException] caught.");
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

    }

    @Test
    @Disabled
    void update_propertyIsExist_shouldUpdatePropertyValue_1() {

        Property testProperty = new Property("test.update.1", "value_1");

        try {
            // Create property in file:
            this.testsFileRepository2.create(testProperty);
            Property readedProperty = this.testsFileRepository2.read("test.update.1");
            Assertions.assertEquals(testProperty, readedProperty);
        } catch (IOException e) {
            LOGGER.error("IO exception occur!");
            Assertions.fail();
        } catch (PropertyAlreadyExistException e) {
            LOGGER.debug("Property already exist in repo.");
        }

        // Update existing property:
            String expectedVal = "value_2";
            Property updatedProperty = this.testsFileRepository2.update(testProperty.getPropertyKey(), expectedVal);
            Assertions.assertNotNull(updatedProperty);
            Assertions.assertEquals(testProperty.getPropertyKey(), updatedProperty.getPropertyKey());
            Assertions.assertEquals(expectedVal, updatedProperty.getPropertyValue());
            LOGGER.info(String.format("update_1: Updated_property: [%s];", updatedProperty));




    }
}
