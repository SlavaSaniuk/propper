package me.saniukvyacheslav.core.actions;

import me.saniukvyacheslav.args.actions.Action;
import me.saniukvyacheslav.args.actions.ActionResult;
import me.saniukvyacheslav.prop.Property;
import me.saniukvyacheslav.services.PropertiesFileService;
import me.saniukvyacheslav.services.PropertiesService;

import java.io.IOException;
import java.util.List;

/**
 * {@link ListPropertiesAction} action instance used in cases, when user wants to read all properties from properties file.
 * Developer must put:
 *  [PATH_TO_PROPERTY_FILE] value in {@link ListPropertiesAction#doAction(List)} arguments parameter at first position.
 * Note: The 0 argument will be action command [/L].
 */
public class ListPropertiesAction implements Action {
    @Override
    public ActionResult doAction(List<String> arguments) {

        // Get argument:
        String pathToPropertiesFile = arguments.get(1);

        // Create property service instance:
        PropertiesService propertiesService = new PropertiesFileService(pathToPropertiesFile);

        // Read properties from properties file:
        try {
            List<Property> readProperties = propertiesService.list();

            // Create action result:
            StringBuilder actionResultSb = new StringBuilder();
            readProperties.forEach((property -> actionResultSb.append(property.toString()).append(System.lineSeparator())));
            return ActionResult.ok(actionResultSb.toString());
        } catch (IOException e) {
            return ActionResult.exception(1, e.getMessage());
        }
    }
}
