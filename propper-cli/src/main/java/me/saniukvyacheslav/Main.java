package me.saniukvyacheslav;

import me.saniukvyacheslav.args.actions.Action;
import me.saniukvyacheslav.args.actions.ActionBranch;
import me.saniukvyacheslav.args.runners.BranchingRunner;
import me.saniukvyacheslav.core.actions.*;

/**
 * propper-cli application.
 */
public class Main {

    /**
     * Run propper-cli application.
     * @param args - command line arguments.
     */
    public static void main(String[] args) {

        // Branching runner:
        BranchingRunner runner = BranchingRunner.getInstance();

        // Add branches:
        // If command /R - read property:
        runner.addActionBranch(new ActionBranch.Builder()
                .onCommandRegex("[/|-][r/R]")
                .ofAction(new ReadPropertyAction())
                .build());
        // If command /C - create property:
        runner.addActionBranch(new ActionBranch.Builder()
                .onCommandRegex("[/|-][c/C]")
                .ofAction(new CreatePropertyAction())
                .build());
        // If command /U - update property:
        runner.addActionBranch(new ActionBranch.Builder()
                .onCommandRegex("[/|-][u/U]")
                .ofAction(new UpdatePropertyAction())
                .build());
        // If command /D - delete property:
        runner.addActionBranch(new ActionBranch.Builder()
                .onCommandRegex("[/|-][d/D]")
                .ofAction(new DeletePropertyAction())
                .build());
        // If command /L - print all properties:
        runner.addActionBranch(new ActionBranch.Builder()
                .onCommandRegex("[/|-][l/L]")
                .ofAction(new ListPropertiesAction())
                .build());
        // If command /H - print help:
        Action helpAction = new HelpAction();
        runner.addActionBranch(new ActionBranch.Builder()
                .onCommandRegex("[/|-][h/H]")
                .ofAction(helpAction)
                .build());
        // If command is not supported, then execute help action:
        runner.onUnknownCommand(helpAction);

        // Run:
        int exitCode = runner.run(args[0], args);
        System.exit(exitCode);
    }
}
