package me.saniukvyacheslav;

import me.saniukvyacheslav.args.actions.ActionBranch;
import me.saniukvyacheslav.args.runners.BranchingRunner;
import me.saniukvyacheslav.core.actions.CreatePropertyAction;
import me.saniukvyacheslav.core.actions.ReadPropertyAction;
import me.saniukvyacheslav.core.actions.UpdatePropertyAction;

public class Main {

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
                .build()
        );
        // If command /U - update property:
        runner.addActionBranch(new ActionBranch.Builder()
                .onCommandRegex("[/|-][u/U]")
                .ofAction(new UpdatePropertyAction())
                .build());

        // Run:
        int exitCode = runner.run(args[0], args);
        System.exit(exitCode);
    }
}
