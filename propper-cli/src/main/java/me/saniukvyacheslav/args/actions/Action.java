package me.saniukvyacheslav.args.actions;

import java.util.List;

/**
 * Action interface represent a user action, which user wants to do on properties file.
 * Developer must implement {@link Action#doAction(List)} method and return {@link ActionResult} instance with result of action.
 * Note: arguments parameter in {@link Action#doAction(List)} method specified via command line
 * (It's "args" parameter of Main method, but in list (not in array)).
 */
public interface Action {

    /**
     * Execute current action instance.
     * Developer must implement this method to execute user action.
     * @param arguments - program command line arguments (String[] args) in list.
     * @return - result of execution.
     */
    ActionResult doAction(List<String> arguments);
}
