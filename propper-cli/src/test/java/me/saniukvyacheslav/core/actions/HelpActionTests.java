package me.saniukvyacheslav.core.actions;

import me.saniukvyacheslav.args.actions.ActionResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class HelpActionTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelpActionTests.class);

    @Test
    void doAction_helpCommand_shouldReturnHelpString() {
        ActionResult result = new HelpAction().doAction(new ArrayList<>());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.getExitCode());

        LOGGER.info(result.getActionResult().toString());
    }
}
