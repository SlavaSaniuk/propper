package me.saniukvyacheslav.core.actions;

import me.saniukvyacheslav.args.actions.ActionResult;
import me.saniukvyacheslav.exceptions.PropertyAlreadyExistException;
import me.saniukvyacheslav.exceptions.PropertyNotFoundException;
import me.saniukvyacheslav.services.PropertiesFileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class DeletePropertyActionTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeletePropertyActionTests.class);
    private final String propertiesFile = "C:\\propper-cli.properties";
    private PropertiesFileService propertiesFileService;

    @BeforeEach
    void beforeEach() {
        this.propertiesFileService = new PropertiesFileService(propertiesFile);
    }

    @Test
    void doAction_propertyExistInFile_shouldDeleteProperty() {
        String propertyKey = "deletepropertyaction.delete.1";

        // Create property, if it's not exist:
        try {
            this.propertiesFileService.create(propertyKey, "value1");
        } catch (PropertyAlreadyExistException e) {
            LOGGER.debug(e.getMessage());
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        // Delete property:
        String[] args = {"/D", propertyKey, this.propertiesFile};
        ActionResult result = new DeletePropertyAction().doAction(Arrays.asList(args));
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.getExitCode());
        Assertions.assertEquals("OK", result.getActionResult());

        LOGGER.debug(String.format("Delete property action result: [%s];", result.getActionResult()));

        // Try to read property from file:
        try {
            this.propertiesFileService.read(propertyKey);
        } catch (PropertyNotFoundException e) {
            LOGGER.info(String.format("Property with key [%s] isn't exist in file.", propertyKey));
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
