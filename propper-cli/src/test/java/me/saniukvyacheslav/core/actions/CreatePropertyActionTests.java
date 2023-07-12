package me.saniukvyacheslav.core.actions;

import me.saniukvyacheslav.args.actions.ActionResult;
import me.saniukvyacheslav.exceptions.PropertyAlreadyExistException;
import me.saniukvyacheslav.services.PropertiesFileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class CreatePropertyActionTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreatePropertyActionTests.class);
    private final String propertiesFile = "C:\\propper-cli.properties";
    private PropertiesFileService propertiesFileService;

    @BeforeEach
    void beforeEach() {
        this.propertiesFileService = new PropertiesFileService(propertiesFile);
    }

    @Test
    void doAction_propertyKeyIsInvalid_shouldReturnResultWithExitCode2() {
        String[] args = {"/C", null, "value1", this.propertiesFile};
        ActionResult result = new CreatePropertyAction().doAction(Arrays.asList(args));

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isException());
        Assertions.assertEquals(2, result.getExitCode());
        LOGGER.debug(String.format("Message of expected exception: [%s];", result.getExceptionMessage()));
    }

    @Test
    void doAction_pathToPropertyFileIsInvalid_shouldReturnResultWithExitCode12() {
        String[] args = {"/C", "any-key", "value1", null};
        ActionResult result = new CreatePropertyAction().doAction(Arrays.asList(args));

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isException());
        Assertions.assertEquals(3, result.getExitCode());
        LOGGER.debug(String.format("Message of expected exception: [%s];", result.getExceptionMessage()));
    }

    @Test
    void doAction_propertyAlreadyExist_shouldReturnResultWithExitCode3() {
        String propertyKey = "createpropertyaction.doaction.1";
        // Create property, if it's not exist in file:
        try {
            this.propertiesFileService.create(propertyKey, "value1");
        } catch (PropertyAlreadyExistException e) {
            LOGGER.debug(e.getMessage());
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        String[] args = {"/C", propertyKey, "value2", this.propertiesFile};
        ActionResult result = new CreatePropertyAction().doAction(Arrays.asList(args));

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isException());
        Assertions.assertEquals(12, result.getExitCode());
        LOGGER.debug(String.format("Message of expected exception: [%s];", result.getExceptionMessage()));
    }

    @Test
    void doAction_propertyIsNotExist_shouldReturnResultWithExitCode0AndOkResult() {
        String propertyKey = "createpropertyaction.doaction.2";
        String propertyValue = "value1";

        String[] args = {"/C", propertyKey, propertyValue, this.propertiesFile};
        ActionResult result = new CreatePropertyAction().doAction(Arrays.asList(args));

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isException());
        Assertions.assertEquals(0, result.getExitCode());
        LOGGER.debug(String.format("Property creation result: [%s];", result.getActionResult()));
    }

}
