package me.saniukvyacheslav.core.services;

import me.saniukvyacheslav.core.prop.Property;
import me.saniukvyacheslav.core.prop.PropertyNotFoundException;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PropertiesFileServiceTestsCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesFileServiceTestsCase.class); // Logger;

    private PropertiesFileService testServiceIml; // Test service;

    @BeforeEach
    void beforeEach() {
        this.testServiceIml = new PropertiesFileService("C:\\property-file-service.properties");
    }

    @AfterEach
    void afterEach() {
        this.testServiceIml = null;
    }

    @Test
    @Order(1)
    void propertiesFileService_pathToPropertyFileIsEmpty_shouldThrowIAE() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PropertiesFileService(""));
    }

    @Test
    @Order(2)
    void propertiesFileService_pathToPropertyFileIsNull_shouldThrowIAE() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PropertiesFileService(null));
    }

    @Test
    @Order(3)
    void propertiesFileService_propertyFileIsDirectory_shouldThrowIAE() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PropertiesFileService("C:\\Windows"));
    }

    @Test
    @Order(4)
    void propertiesFileService_propertyFileNotExist_shouldThrowIAE() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PropertiesFileService("H:\\abc.xyz"));
    }

    @Test
    @Order(5)
    void propertiesFileService_pathToPropertyFileIsRight_shouldCreateService() {
        PropertiesFileService propertiesFileService = new PropertiesFileService("C:\\property-file-service.properties");
        Assertions.assertNotNull(propertiesFileService);
    }

    @Test
    @Order(6)
    void read_propertyKeyIsNull_shouldThrowIAE() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.testServiceIml.read(null));
    }

    @Test
    @Order(7)
    void read_propertyKeyIsEmpty_shouldThrowIAE() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.testServiceIml.read(""));
    }

    @Test
    @Order(8)
    void read_propertyNotFound_shouldThrowPropertyNotFoundException() {
        Assertions.assertThrows(PropertyNotFoundException.class, () -> this.testServiceIml.read("not-existed-property-key"));
    }

    @Test
    @Order(9)
    void read_propertyWithEmptyValue_shouldReturnPropertyWithEmptyValue() {
        // ********************************************************************
        // In test properties file already exist property: "service.read.empty.value="
        LOGGER.debug("********************************************************************");
        LOGGER.debug("In test properties file already exist property: \"service.read.empty.value=\"");
        LOGGER.debug("********************************************************************");
        String expectedPropertyKey = "service.read.empty.value";
        String expectedPropertyValue = "";

        try {
            Property readedProperty = this.testServiceIml.read(expectedPropertyKey);

            Assertions.assertEquals(expectedPropertyKey, readedProperty.getPropertyKey());
            Assertions.assertEquals(expectedPropertyValue, readedProperty.getPropertyValue());

            LOGGER.info(String.format("Readed property: [%s];", readedProperty));

        } catch (PropertyNotFoundException | IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(10)
    void read_propertyExistInPropertiesFile_shouldReturnPropertyObject() {
        // ********************************************************************
        // In test properties file already exist property: "my.name=Vyacheslav"
        LOGGER.debug("********************************************************************");
        LOGGER.debug("In test properties file already exist property: \"my.name=Vyacheslav\"");
        LOGGER.debug("********************************************************************");
        String expectedPropertyKey = "my.name";
        String expectedPropertyValue = "Vyacheslav";

        try {
            Property readedProperty = this.testServiceIml.read(expectedPropertyKey);

            Assertions.assertEquals(expectedPropertyKey, readedProperty.getPropertyKey());
            Assertions.assertEquals(expectedPropertyValue, readedProperty.getPropertyValue());

            LOGGER.info(String.format("Readed property: [%s];", readedProperty));

        } catch (PropertyNotFoundException | IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(11)
    void readValue_propertyExistInPropertiesFile_shouldReturnPropertyValue() {
        // ********************************************************************
        // In test properties file already exist property: "my.name=Vyacheslav"
        LOGGER.debug("********************************************************************");
        LOGGER.debug("In test properties file already exist property: \"my.name=Vyacheslav\"");
        LOGGER.debug("********************************************************************");
        String expectedPropertyValue = "Vyacheslav";

        try {
            String actualPropertyValue = this.testServiceIml.readValue("my.name");
            Assertions.assertEquals(expectedPropertyValue, actualPropertyValue);
            LOGGER.info(String.format("Readed property [my.name] value: [%s];", actualPropertyValue));

        } catch (PropertyNotFoundException | IOException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
