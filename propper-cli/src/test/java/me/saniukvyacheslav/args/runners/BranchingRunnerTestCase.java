package me.saniukvyacheslav.args.runners;

import me.saniukvyacheslav.args.actions.ActionBranch;
import me.saniukvyacheslav.core.actions.CreatePropertyAction;
import me.saniukvyacheslav.core.actions.ReadPropertyAction;
import me.saniukvyacheslav.exceptions.PropertyAlreadyExistException;
import me.saniukvyacheslav.services.PropertiesFileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class BranchingRunnerTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(BranchingRunnerTestCase.class);
    private final String propertiesFile = "C:\\propper-cli.properties";
    private PropertiesFileService propertiesFileService;

    @BeforeEach
    void beforeEach() {
        this.propertiesFileService = new PropertiesFileService(propertiesFile);
    }

    @AfterEach
    void afterEach() {
        BranchingRunner.getInstance().getBranches().clear();
    }

    @Test
    void run_propertyIsExistInFile_shouldPrintPropertyValueAndReturn0ExitCode() {
        String propertyKey = "branchingrunner.run.read1";
        String propertyValue = "value1";

        // Create property, if it is not exist:
        try {
            this.propertiesFileService.create(propertyKey, propertyValue);
        } catch (PropertyAlreadyExistException e) {
            LOGGER.debug(e.getMessage());
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        // Get runner:
        String[] args = {"-R", propertyKey, this.propertiesFile};
        BranchingRunner runner = BranchingRunner.getInstance();
        // Add ActionBranches:
        runner.addActionBranch(new ActionBranch.Builder()
                .onCommandRegex("[/|-][r/R]")
                .ofAction(new ReadPropertyAction())
                .build()
        );

        int exitCode = runner.run(args[0], args);
        Assertions.assertEquals(0, exitCode);
    }

    @Test
    void run_propertyKeyIsNull_shouldPrintExceptionMessageAndReturn2ExitCode() {
        String propertyKey = null;

        // Get runner:
        String[] args = {"-R", propertyKey, this.propertiesFile};
        BranchingRunner runner = BranchingRunner.getInstance();
        // Add ActionBranches:
        runner.addActionBranch(new ActionBranch.Builder()
                .onCommandRegex("[/|-][r/R]")
                .ofAction(new ReadPropertyAction())
                .build()
        );

        int exitCode = runner.run(args[0], args);
        Assertions.assertEquals(2, exitCode);
    }

    @Test
    void run_propertyIsNotExistInFile_shouldPrintExceptionMessageAndReturn2ExitCode() {
        String propertyKey = "not-existed-property-key";

        // Get runner:
        String[] args = {"-R", propertyKey, this.propertiesFile};
        BranchingRunner runner = BranchingRunner.getInstance();
        // Add ActionBranches:
        runner.addActionBranch(new ActionBranch.Builder()
                .onCommandRegex("[/|-][r/R]")
                .ofAction(new ReadPropertyAction())
                .build()
        );

        int exitCode = runner.run(args[0], args);
        Assertions.assertEquals(11, exitCode);
    }

    @Test
    void run_pathToPropertyFileLinkOnDirectory_shouldPrintExceptionMessageAndReturn3ExitCode() {
        String propertyKey = "not-existed-property-key";

        // Get runner:
        String[] args = {"-R", propertyKey, "C:\\Windows"};
        BranchingRunner runner = BranchingRunner.getInstance();
        // Add ActionBranches:
        runner.addActionBranch(new ActionBranch.Builder()
                .onCommandRegex("[/|-][r/R]")
                .ofAction(new ReadPropertyAction())
                .build()
        );

        int exitCode = runner.run(args[0], args);
        Assertions.assertEquals(3, exitCode);
    }

    @Test
    void run_createPropertyInFile_shouldCreatePropertyAndPrintOkAndreturn0ExitCode() {
        String propertyKey = "branchingrunner.run.create11";
        String propertyValue = "value1";

        // Get runner:
        String[] args = {"-C", propertyKey, propertyValue, this.propertiesFile};
        BranchingRunner runner = BranchingRunner.getInstance();
        // Add ActionBranches:
        runner.addActionBranch(new ActionBranch.Builder()
                .onCommandRegex("[/|-][c/C]")
                .ofAction(new CreatePropertyAction())
                .build()
        );

        int exitCode = runner.run(args[0], args);
        Assertions.assertEquals(0, exitCode);
    }
}
