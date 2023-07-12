package me.saniukvyacheslav.core.actions;

import me.saniukvyacheslav.args.actions.Action;
import me.saniukvyacheslav.args.actions.ActionResult;
import me.saniukvyacheslav.exceptions.PropertyIsInvalidException;
import me.saniukvyacheslav.exceptions.PropertyNotFoundException;
import me.saniukvyacheslav.prop.Property;
import me.saniukvyacheslav.prop.PropertyWrapper;
import me.saniukvyacheslav.services.PropertiesFileService;
import me.saniukvyacheslav.services.PropertiesService;

import java.io.IOException;
import java.util.List;

/**
 * {@link ReadPropertyAction} action instance used in cases, when user wants to read property from properties file.
 * Developer must put:
 *  [PROPERTY_KEY] value in {@link ReadPropertyAction#doAction(List)} arguments parameter at first position.
 *  [PATH_TO_PROPERTY_FILE] value in {@link ReadPropertyAction#doAction(List)} arguments parameter at second position.
 * Note: The 0 argument will be action command [/r].
 */
public class ReadPropertyAction implements Action {

    @Override
    public ActionResult doAction(List<String> arguments) {

        // Get arguments:
        String propertyKey = arguments.get(1);
        String pathToPropertyFile = arguments.get(2);

        try {
            // Check property key:
            PropertyWrapper.checkPropertyKey(propertyKey);

            // Try to create PropertyService instance:
            PropertiesService propertiesService = new PropertiesFileService(pathToPropertyFile);

            // Do action:
            Property readProperty = propertiesService.read(propertyKey);
            return ActionResult.ok(readProperty.getPropertyValue());

        }catch (PropertyIsInvalidException e) {
            return ActionResult.exception(2, e.getMessage());
        }catch (IllegalArgumentException e) {
            return ActionResult.exception(3, e.getMessage());
        }catch (PropertyNotFoundException e) {
            return ActionResult.exception(11, e.getMessage());
        } catch (IOException e) {
            return ActionResult.exception(1, e.getMessage());
        }
    }
}
