package me.saniukvyacheslav.core.actions;

import me.saniukvyacheslav.args.actions.Action;
import me.saniukvyacheslav.args.actions.ActionResult;
import me.saniukvyacheslav.exceptions.PropertyIsInvalidException;
import me.saniukvyacheslav.exceptions.PropertyNotFoundException;
import me.saniukvyacheslav.services.PropertiesService;

import java.io.IOException;
import java.util.List;

/**
 *  {@link DeletePropertyAction} user action used in cases, when user wants to delete property in file.
 * In {@link #doAction(List)} arguments parameter, the second(1) element is property key, the third element(2) is path
 * on property file.
 * If {@link #doAction(List)} method completes without exceptions, method return "OK" string in
 * {@link ActionResult#getActionResult()} field.
 */
public class DeletePropertyAction implements Action {
    @Override
    public ActionResult doAction(List<String> arguments) {
        // Get property key and path to properties file:
        String propertyKey = arguments.get(1);
        String pathToPropertyFile = arguments.get(2);

        try {
            // Create properties service instance:
            PropertiesService propertiesService = PropertiesFileAction.checkPropertyKeyAndGetPropertiesService(propertyKey, pathToPropertyFile);
            // Try ro delete property in file:
            propertiesService.delete(propertyKey);
            return ActionResult.ok("OK");
        }catch (PropertyIsInvalidException e) {
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
