package me.saniukvyacheslav.core.actions;

import me.saniukvyacheslav.args.actions.Action;
import me.saniukvyacheslav.args.actions.ActionResult;
import me.saniukvyacheslav.exceptions.PropertyAlreadyExistException;
import me.saniukvyacheslav.exceptions.PropertyIsInvalidException;
import me.saniukvyacheslav.prop.PropertyWrapper;
import me.saniukvyacheslav.services.PropertiesFileService;
import me.saniukvyacheslav.services.PropertiesService;

import java.io.IOException;
import java.util.List;

/**
 *  {@link CreatePropertyAction} user action used in cases, when user wants to create property in file.
 * In {@link #doAction(List)} arguments parameter, the second(1) element is property key, the third element(2) is
 * property value, and the four(3) element is path on property file.
 * If {@link #doAction(List)} method completes without exceptions, method return "OK" string in
 * {@link ActionResult#getActionResult()} field.
 */
public class CreatePropertyAction implements Action {

    @Override
    public ActionResult doAction(List<String> arguments) {
        // Get parameters:
        String propertyKey = arguments.get(1);
        String propertyValue = arguments.get(2);
        String pathToPropertyFile = arguments.get(3);

        // Try to create property in file:
        try {
            // Check property key:
            PropertyWrapper.checkPropertyKey(propertyKey);

            // Try to create property service instance:
            PropertiesService propertiesService = new PropertiesFileService(pathToPropertyFile);

            // Try to create property in file:
            propertiesService.create(propertyKey, propertyValue);

            // Return OK message:
            return ActionResult.ok("OK");

        }catch (PropertyIsInvalidException e) {
            return ActionResult.exception(2, e.getMessage());
        }catch (IllegalArgumentException e) {
            return ActionResult.exception(3, e.getMessage());
        } catch (IOException e) {
            return ActionResult.exception(1, e.getMessage());
        } catch (PropertyAlreadyExistException e) {
            return ActionResult.exception(12, e.getMessage());
        }
    }

}
