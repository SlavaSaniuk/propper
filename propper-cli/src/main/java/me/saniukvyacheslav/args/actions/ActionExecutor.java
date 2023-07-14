package me.saniukvyacheslav.args.actions;

import me.saniukvyacheslav.args.exceptions.UnhandledException;

import java.util.List;

/**
 * ActionExecutor singleton instance execute specified {@link ActionBranch} branch.
 * ActionExecutor used to prevent any unhandled exceptions. If unhandled exceptions occur,
 * {@link #execute(ActionBranch, List)} method catches it. and throw new {@link UnhandledException} exception.
 */
public class ActionExecutor {

    private static ActionExecutor instance; // Singleton instance of this class;

    /**
     * Closed constructor.
     */
    private ActionExecutor() {}

    /**
     * Get current singleton instance of this class.
     * @return - this executor instance.
     */
    public static ActionExecutor getInstance() {
        if (ActionExecutor.instance == null)
            ActionExecutor.instance = new ActionExecutor();
        return ActionExecutor.instance;
    }

    /**
     * Execute specified {@link ActionBranch} with arguments from CLI.
     * @param anActionBranch - supported branch.
     * @param anArguments - arguments from CLI in list format.
     * @return - {@link ActionResult} result, if action completed with or without handled exceptions.
     * @throws UnhandledException - If developer didn't handle all possible exceptions.
     */
    public ActionResult execute(ActionBranch anActionBranch, List<String> anArguments) throws UnhandledException {

        // Get action from specified branch:
        Action action = anActionBranch.getAction();

        // Execute action:
        return this.execute(action, anArguments);
    }

    /**
     * Execute specified action instance with specified arguments.
     * @param anAction - user action.
     * @param anArguments - arguments for action.
     * @return - action result instance.
     * @throws UnhandledException - If developer didn't handle all possible exceptions.
     */
    public ActionResult execute(Action anAction, List<String> anArguments) throws UnhandledException {
        // Execute action:
        try {
            return anAction.doAction(anArguments);
        }catch (Exception e) {
            throw new UnhandledException(e);
        }
    }
}
