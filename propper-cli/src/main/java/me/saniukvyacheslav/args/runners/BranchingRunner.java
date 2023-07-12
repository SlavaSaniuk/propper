package me.saniukvyacheslav.args.runners;

import lombok.Getter;
import me.saniukvyacheslav.args.actions.ActionBranch;
import me.saniukvyacheslav.args.actions.ActionExecutor;
import me.saniukvyacheslav.args.actions.ActionResult;
import me.saniukvyacheslav.args.exceptions.UnhandledException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class BranchingRunner implements Runner {

    private static BranchingRunner instance;
    @Getter
    private final Set<ActionBranch> branches = new HashSet<>();
    private static final ActionExecutor actionExecutor = ActionExecutor.getInstance();

    private BranchingRunner() {}

    public static BranchingRunner getInstance() {
        if(BranchingRunner.instance == null) BranchingRunner.instance = new BranchingRunner();
        return BranchingRunner.instance;
    }

    public void addActionBranch(ActionBranch anActionBranch) {
        this.branches.add(anActionBranch);
    }

    @Override
    public int run(String aCommand, String[] aCommandArgs) {

        // Select current branch:
        ActionBranch currentBranch = null;
        for (ActionBranch branch : this.branches) {
            if (Pattern.matches(branch.getCommandRegex(), aCommand)) {
                currentBranch = branch;
                break;
        }}
        if (currentBranch == null) return UnhandledException.unhandledExceptionCode;

        // Execute current action:
        try {
            ActionResult actionResult = BranchingRunner.actionExecutor.execute(currentBranch, Arrays.asList(aCommandArgs));

            // Check if action completed without exceptions:
            if (!(actionResult.isException())) {
                // Print result:
                System.out.println(actionResult.getActionResult());
                return actionResult.getExitCode(); // Always zero;
            }else {
                // Print exception message in out and err streams:
                System.err.println(actionResult.getExceptionMessage());
                System.out.println(actionResult.getExceptionMessage());
                return actionResult.getExitCode();
            }

        } catch (UnhandledException e) {
            // Print exception message in out and err streams:
            System.err.println(e.getUnhandledExceptionMessage());
            e.getUnhandledException().printStackTrace();
            System.out.println(e.getUnhandledExceptionMessage());
            return UnhandledException.unhandledExceptionCode;
        }
    }


}
