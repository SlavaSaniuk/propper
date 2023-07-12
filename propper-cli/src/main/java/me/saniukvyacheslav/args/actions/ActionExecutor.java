package me.saniukvyacheslav.args.actions;

import me.saniukvyacheslav.args.exceptions.UnhandledException;

import java.util.List;

public class ActionExecutor {

    private static ActionExecutor instance;
    private ActionExecutor() {
    }

    public static ActionExecutor getInstance() {
        if (ActionExecutor.instance == null)
            ActionExecutor.instance = new ActionExecutor();
        return ActionExecutor.instance;
    }

    public ActionResult execute(ActionBranch anActionBranch, List<String> anArguments) throws UnhandledException {

        // Get action and arguments:
        Action action = anActionBranch.getAction();

        // Execute action:
        try {
            return action.doAction(anArguments);
        }catch (Exception e) {
            throw new UnhandledException(e);
        }

    }
}
