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
import java.util.ArrayList;
import java.util.List;

public class ReadPropertyActionTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadPropertyActionTests.class);
    private final String propertiesFile = "C:\\propper-cli.properties";
    private PropertiesFileService propertiesFileService;

    @BeforeEach
    void beforeEach() {
        this.propertiesFileService = new PropertiesFileService(propertiesFile);
    }

    @Test
    void doAction_propertyKeyIsNull_shouldReturnActionResultWithExitCode2() {
        List<String> arguments = new ArrayList<>();
        arguments.add(0, "/r");
        arguments.add(1, null);
        arguments.add(2, "C:\\propper-cli.properties");
        try {
            ActionResult result = new ReadPropertyAction().doAction(arguments);
            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.isException());
            Assertions.assertEquals(2, result.getExitCode());
            LOGGER.debug("Message of expected exception: " +result.getExceptionMessage());
        }catch (RuntimeException exc) {
            Assertions.fail(exc.getMessage());
        }
    }

    @Test
    void doAction_propertyKeyIsEmpty_shouldReturnActionResultWithExitCode2() {
        List<String> arguments = new ArrayList<>();
        arguments.add(0, "/r");
        arguments.add(1, "");
        arguments.add(2, "C:\\propper-cli.properties");
        try {
            ActionResult result = new ReadPropertyAction().doAction(arguments);
            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.isException());
            Assertions.assertEquals(2, result.getExitCode());
            LOGGER.debug("Message of expected exception: " +result.getExceptionMessage());
        }catch (RuntimeException exc) {
            Assertions.fail(exc.getMessage());
        }
    }

    @Test
    void doAction_pathToPropertyFileLinkOnDirectory_shouldReturnActionResultWithExitCode3() {
        List<String> arguments = new ArrayList<>();
        arguments.add(0, "/r");
        arguments.add(1, "my.name");
        arguments.add(2, "C:\\Windows");
        try {
            ActionResult result = new ReadPropertyAction().doAction(arguments);
            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.isException());
            Assertions.assertEquals(3, result.getExitCode());
            LOGGER.debug("Message of expected exception: " +result.getExceptionMessage());
        }catch (RuntimeException exc) {
            Assertions.fail(exc.getMessage());
        }
    }

    @Test
    void doAction_propertyFileInNotExist_shouldReturnActionResultWithExitCode3() {
        List<String> arguments = new ArrayList<>();
        arguments.add(0, "/r");
        arguments.add(1, "my.name");
        arguments.add(2, "not-existed-properties-file.properties");
        try {
            ActionResult result = new ReadPropertyAction().doAction(arguments);
            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.isException());
            Assertions.assertEquals(3, result.getExitCode());
            LOGGER.debug("Message of expected exception: " +result.getExceptionMessage());
        }catch (RuntimeException exc) {
            Assertions.fail(exc.getMessage());
        }
    }

    @Test
    void doAction_propertyWithSpecifiedKeyIsNotExist_shouldReturnActionResultWithExitCode11() {
        String propertyKey = "readpropertyaction.doaction.1";
        List<String> arguments = new ArrayList<>();
        arguments.add(0, "/r");
        arguments.add(1, propertyKey);
        arguments.add(2, "C:\\propper-cli.properties");

        // Delete property if it's already exist:
        try {
            this.propertiesFileService.delete(propertyKey);
        } catch (PropertyNotFoundException e) {
            LOGGER.debug(String.format("Property [%s] is not exist in [%s] property file.", propertyKey, this.propertiesFile));
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        try {
            ActionResult result = new ReadPropertyAction().doAction(arguments);
            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.isException());
            Assertions.assertEquals(11, result.getExitCode());
            LOGGER.debug("Message of expected exception: " +result.getExceptionMessage());
        }catch (RuntimeException exc) {
            Assertions.fail(exc.getMessage());
        }
    }

    @Test
    void doAction_propertyWithSpecifiedKeyIsExist_shouldReturnActionResultWithExitCode0AndResult() {
        String propertyKey = "readpropertyaction.doaction.2";
        List<String> arguments = new ArrayList<>();
        arguments.add(0, "/r");
        arguments.add(1, propertyKey);
        arguments.add(2, "C:\\propper-cli.properties");

        String expectedPropertyValue = "value1";

        // Create property if it is not already exist:
        try {
            this.propertiesFileService.create(propertyKey, expectedPropertyValue);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        } catch (PropertyAlreadyExistException e) {
            LOGGER.debug(String.format("Property with key [%s] already exist in properties file [%s];", propertyKey, this.propertiesFile));
        }

        try {
            ActionResult result = new ReadPropertyAction().doAction(arguments);
            Assertions.assertNotNull(result);
            Assertions.assertFalse(result.isException());
            Assertions.assertEquals(0, result.getExitCode());
            Assertions.assertEquals(expectedPropertyValue, result.getActionResult());
            LOGGER.debug("Property value: " +result.getActionResult());
        }catch (RuntimeException exc) {
            Assertions.fail(exc.getMessage());
        }
    }

}
