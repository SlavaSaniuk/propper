package me.saniukvyacheslav.args.actions;

import me.saniukvyacheslav.args.exceptions.UnhandledException;
import me.saniukvyacheslav.core.actions.CreatePropertyAction;
import me.saniukvyacheslav.core.actions.ReadPropertyAction;
import me.saniukvyacheslav.exceptions.PropertyAlreadyExistException;
import me.saniukvyacheslav.exceptions.PropertyNotFoundException;
import me.saniukvyacheslav.services.PropertiesFileService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ActionExecutorTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionExecutorTestCase.class);
    private final String propertiesFile = "C:\\propper-cli.properties";
    private PropertiesFileService propertiesFileService;

    @BeforeEach
    void beforeEach() {
        this.propertiesFileService = new PropertiesFileService(propertiesFile);
    }

    @Test
    @Order(1)
    void execute_readPropertyActionWithValidArguments_shouldReturnActionResult() {
        String propertyKey = "actionexecutor.execute.readaction.1";
        String propertyValue = "value1";

        // If property not exist, then create it;
        try {
            this.propertiesFileService.create(propertyKey, propertyValue);
        } catch (PropertyAlreadyExistException e) {
            LOGGER.debug(e.getMessage());
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        // Create action branch:
        ActionBranch actionBranch = new ActionBranch.Builder()
                .onCommandRegex("/r")
                .ofAction(new ReadPropertyAction())
                .build();

        try {
            ActionResult actionResult = ActionExecutor.getInstance().execute(actionBranch, Arrays.asList("/r", propertyKey, this.propertiesFile));
            Assertions.assertNotNull(actionResult);
            Assertions.assertFalse(actionResult.isException());
            Assertions.assertEquals(0, actionResult.getExitCode());
            Assertions.assertEquals(propertyValue, actionResult.getActionResult());

            LOGGER.debug(String.format("Read value: [%s];", actionResult.getActionResult()));
        } catch (UnhandledException e) {
            Assertions.fail(e.getUnhandledExceptionMessage());
        }
    }

    @Test
    @Order(2)
    void execute_createPropertyActionWithValidArguments_shouldReturnActionResult() {
        String propertyKey = "actionexecutor.execute.createaction.1";
        String propertyValue = "value1";

        try {
            this.propertiesFileService.read(propertyKey);
            return;
        } catch (PropertyNotFoundException e) {
            LOGGER.debug(e.getMessage());
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        // Create action branch:
        ActionBranch actionBranch = new ActionBranch.Builder()
                .onCommandRegex("[/|-][c|C]")
                .ofAction(new CreatePropertyAction())
                .build();

        try {
            ActionResult actionResult = ActionExecutor.getInstance().execute(actionBranch, Arrays.asList("/C", propertyKey, propertyValue, this.propertiesFile));
            Assertions.assertNotNull(actionResult);
            Assertions.assertFalse(actionResult.isException());
            Assertions.assertEquals(0, actionResult.getExitCode());

            LOGGER.debug(String.format("Property creation result: [%s];", actionResult.getActionResult()));
        } catch (UnhandledException e) {
            Assertions.fail(e.getUnhandledExceptionMessage());
        }
    }

}
