package me.saniukvyacheslav.core.actions;

import me.saniukvyacheslav.args.actions.Action;
import me.saniukvyacheslav.args.actions.ActionResult;

import java.util.List;

/**
 * HelpAction action instance used to get help about application.
 */
public class HelpAction implements Action {

    private static final String applicationHelp = "propper-cli appication uses for working with properties in properties files.\n" +
            "Usage:\n" +
            "\t* To read property:\n" +
            "\tpropper-cli.jar -R [PROPERTY_KEY] [PROPERTIES_FILE]\n" +
            "\t* To create property:\n" +
            "\tpropper-cli.jar -C [PROPERTY_KEY] [PROPERTY_VALUE] [PROPERTIES_FILE]\n" +
            "\t* To update property:\t\n" +
            "\tpropper-cli.jar -U [PROPERTY_KEY] [PROPERTY_NEW_VALUE] [PROPERTIES_FILE]\n" +
            "\t* To delete property:\n" +
            "\tpropper-cli.jar -D [PROPERTY_KEY] [PROPERTIES_FILE]\n" +
            "\t* Show help:\n" +
            "\tpropper-cli.jar -H\n" +
            "Example:\n" +
            "\tpropper-cli.jar -C application.version 1.0 C:\\filename.properties\n" +
            "\tCreate property applicaiton.version=1.0 in properties file C:\\filename.properties.";

    @Override
    public ActionResult doAction(List<String> arguments) {
        return ActionResult.ok(HelpAction.applicationHelp);
    }
}
