package me.saniukvyacheslav.core.services;

import me.saniukvyacheslav.core.exceptions.PropertyAlreadyExistException;
import me.saniukvyacheslav.core.exceptions.PropertyIsInvalidException;
import me.saniukvyacheslav.core.prop.Property;
import me.saniukvyacheslav.core.exceptions.PropertyNotFoundException;
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

    @Test
    @Order(12)
    void create_propertyIsNull_shouldThrowPIIE() {
        Assertions.assertThrows(PropertyIsInvalidException.class, () -> this.testServiceIml.create(null));
    }

    @Test
    @Order(13)
    void create_propertyKeyIsNull_shouldThrowPIIE() {
        Assertions.assertThrows(PropertyIsInvalidException.class, () -> this.testServiceIml.create(null, null));
    }

    @Test
    @Order(14)
    void create_propertyKeyIsEmpty_shouldThrowPIIE() {
        Assertions.assertThrows(PropertyIsInvalidException.class, () -> this.testServiceIml.create("", null));
    }

    @Test
    @Order(15)
    void create_propertyAlreadyExistInFile_shouldThrowPAEE() {
        // ********************************************************************
        // In test properties file already exist property: "my.name=Vyacheslav"
        LOGGER.debug("********************************************************************");
        LOGGER.debug("In test properties file already exist property: \"my.name=Vyacheslav\"");
        LOGGER.debug("********************************************************************");

        Property propertyToCreate = new Property("my.name","Vyacheslav");
        Assertions.assertThrows(PropertyAlreadyExistException.class, ()-> this.testServiceIml.create(propertyToCreate));
    }

    // Worked method:
    @Test
    @Order(16)
    @Disabled
    void create_validPropertyNotExistInFile_shouldCreateProperty() {

        String propertyKey = "service.create.valid";
        String propertyValue = "value_1";
        Property propertyToCreate = new Property(propertyKey,propertyValue);

        // Create property in file:
        try {
            LOGGER.debug(String.format("Property to create: [%s];", propertyToCreate));
            this.testServiceIml.create(propertyToCreate);
        } catch (PropertyAlreadyExistException | IOException e) {
            Assertions.fail(e.getMessage());
        }

        // Find property in file:
        try {
            Property createdProperty = this.testServiceIml.read(propertyKey);

            Assertions.assertEquals(propertyToCreate, createdProperty);
            LOGGER.debug(String.format("Created property: [%s];", createdProperty));

        } catch (PropertyNotFoundException | IOException e) {
            Assertions.fail(e.getMessage());
        }

    }

    @Test
    @Order(17)
    @Disabled
    void create_validPropertyKey_shouldCreateProperty() {

        String propertyKey = "service.create_str_str.valid";
        String propertyValue = "value_1";

        // Create property in file:
        try {
            LOGGER.debug(String.format("Property to create: [%s = %s];", propertyKey, propertyValue));
            this.testServiceIml.create(propertyKey, propertyValue);
        } catch (PropertyAlreadyExistException | IOException e) {
            Assertions.fail(e.getMessage());
        }

        // Find property in file:
        try {
            Property createdProperty = this.testServiceIml.read(propertyKey);

            Assertions.assertEquals(propertyKey, createdProperty.getPropertyKey());
            Assertions.assertEquals(propertyValue, createdProperty.getPropertyValue());
            LOGGER.debug(String.format("Created property: [%s];", createdProperty));

        } catch (PropertyNotFoundException | IOException e) {
            Assertions.fail(e.getMessage());
        }

    }

    @Test
    @Order(18)
    void update_propertyIsExistInFile_shouldUpdateProperty() {

        // Create test property:
        String sharedPropertyKey = "service.update.property";
        Property propertyToCreate = new Property(sharedPropertyKey, "value1");
        // Create property in file:
        try {

            LOGGER.debug(String.format("Property to create: [%s];", propertyToCreate));
            this.testServiceIml.create(propertyToCreate);
        } catch (PropertyAlreadyExistException | IOException e) {
            LOGGER.debug(e.getMessage());
        }

        // Update property in file:
        String newPropertyValue = "value2";
        Property propertyToUpdate = new Property(sharedPropertyKey, newPropertyValue);
        try {
            this.testServiceIml.update(propertyToUpdate);
        } catch (PropertyNotFoundException | IOException e) {
            Assertions.fail(e.getMessage());
        }

        // Find property in file:
        try {
            Property updatedProperty = this.testServiceIml.read(sharedPropertyKey);

            Assertions.assertEquals(propertyToUpdate, updatedProperty);
            LOGGER.debug(String.format("Updated property: [%s];", updatedProperty));

        } catch (PropertyNotFoundException | IOException e) {
            Assertions.fail(e.getMessage());
        }

    }
}
