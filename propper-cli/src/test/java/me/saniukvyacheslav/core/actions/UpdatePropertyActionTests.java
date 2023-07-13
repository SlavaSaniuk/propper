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

public class UpdatePropertyActionTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePropertyActionTests.class);
    private final String propertiesFile = "C:\\propper-cli.properties";
    private PropertiesFileService propertiesFileService;

    @BeforeEach
    void beforeEach() {
        this.propertiesFileService = new PropertiesFileService(propertiesFile);
    }

    @Test
    void doAction_propertyKeyIsExist_shouldUpdatePropertyValueAndReturnResultWithExitCode0() {
        String propertyKey = "updatepropertyaction.doaction.1";
        String propertyValue = "value1";
        String propertyNewValue = "value2";

        // Create property, if it's not exist in file:
        try {
            this.propertiesFileService.create(propertyKey, propertyValue);
        } catch (PropertyAlreadyExistException e) {
            LOGGER.debug(e.getMessage());
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        // Update property:
        String[] args = {"/U", propertyKey, propertyNewValue, this.propertiesFile};
        ActionResult result = new UpdatePropertyAction().doAction(Arrays.asList(args));
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isException());
        Assertions.assertEquals(0, result.getExitCode());
        Assertions.assertEquals("OK", result.getActionResult());

        // Read property value:
        try {
            String readPropertyValue = this.propertiesFileService.readValue(propertyKey);
            Assertions.assertEquals(propertyNewValue, readPropertyValue);
            LOGGER.debug(String.format("New property value: [%s];", readPropertyValue));
        } catch (PropertyNotFoundException | IOException e) {
            Assertions.fail(e.getMessage());
        }

    }
}
