package me.saniukvyacheslav.core.fs;

import me.saniukvyacheslav.core.prop.Property;
import me.saniukvyacheslav.core.prop.PropertyNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileRepositoryTestsCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRepositoryTestsCase.class);

    private File propertyTestFile;
    private FileRepository testsFileRepository;

    @BeforeEach
    void beforeEach() {
        try {
            this.propertyTestFile = new File("C:\\propertyTestFile.properties");
            this.propertyTestFile.createNewFile();
            LOGGER.debug("Tests property file [C:\\propertyTestFile.properties] is created.");

            this.testsFileRepository = new FileRepository(this.propertyTestFile);
        } catch (IOException e) {
            LOGGER.error("Tests property file [C:\\propertyTestFile.properties] CAN NOT be created.");
        }

        LOGGER.debug("BEFORE_EACH_RESULT: Tests property file is CREATED;");
        LOGGER.debug("BEFORE_EACH_RESULT: Tests file repository is CREATED;");
    }

    @AfterEach
    void afterEach() {
        try {
            this.testsFileRepository.close();
            if(!this.propertyTestFile.delete()) LOGGER.error("Tests property file [C:\\propertyTestFile.properties] CAN NOT be deleted.");
        } catch (IOException e) {
            LOGGER.error("Tests file repository CAN NOt be closed.");
        }

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
            writer.write("test.property.key=test.property.value");
            writer.write("hello=world");
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

        } catch (PropertyNotFoundException e) {
            LOGGER.error("Property not found.");
            Assertions.fail();
        }
    }
}
