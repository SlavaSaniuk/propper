package me.saniukvyacheslav.core.actions;

import me.saniukvyacheslav.args.actions.Action;
import me.saniukvyacheslav.args.actions.ActionResult;
import me.saniukvyacheslav.exceptions.PropertyIsInvalidException;
import me.saniukvyacheslav.exceptions.PropertyNotFoundException;
import me.saniukvyacheslav.services.PropertiesService;

import java.io.IOException;
import java.util.List;

/**
 *  {@link UpdatePropertyAction} user action used in cases, when user wants to update property in file.
 * In {@link #doAction(List)} arguments parameter, the second(1) element is property key, the third element(2) is
 * property value, and the four(3) element is path on property file.
 * If {@link #doAction(List)} method completes without exceptions, method return "OK" string in
 * {@link ActionResult#getActionResult()} field.
 */
public class UpdatePropertyAction implements Action {

    @Override
    public ActionResult doAction(List<String> arguments) {

        // Get parameters from arguments:
        String propertyKey = arguments.get(1);
        String propertyNewValue = arguments.get(2);
        String pathToPropertyFile = arguments.get(3);

        try {
            // Check property key and get properties service:
            PropertiesService propertiesService = PropertiesFileAction.checkPropertyKeyAndGetPropertiesService(propertyKey, pathToPropertyFile);

            // Update property in file:
            propertiesService.update(propertyKey, propertyNewValue);
            return ActionResult.ok("OK");

        } catch (PropertyIsInvalidException e) {
            return ActionResult.exception(2, e.getMessage());
        }catch (IllegalArgumentException e) {
            return ActionResult.exception(3, e.getMessage());
        } catch (PropertyNotFoundException e) {
            return ActionResult.exception(11, e.getMessage());
        } catch (IOException e) {
            return ActionResult.exception(1, e.getMessage());
        }
    }

}
