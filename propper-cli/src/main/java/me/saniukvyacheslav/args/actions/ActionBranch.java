package me.saniukvyacheslav.args.actions;

import lombok.Getter;

/**
 * ActionBranch used to map user command with {@link Action} user action.
 * Use {@link ActionBranch.Builder} to create different program branches.
 */
public class ActionBranch {

    @Getter
    private final String commandRegex;
    @Getter
    private final Action action;

    /**
     * Construct new branch by specified mapping "command - action".
     * @param aCommandRegex - command regular expression.
     * @param anAction - user action.
     */
    private ActionBranch(String aCommandRegex, Action anAction) {
        this.commandRegex = aCommandRegex;
        this.action = anAction;
    }

    /**
     * Builder used to create new instances of {@link ActionBranch} branches.
     */
    public static class Builder {

        private String commandReg;
        private Action branchAction;

        /**
         * Set command regular expression to {@link ActionBranch} branch instance.
         * Example: if you want use "/d" or "-R" arguments, you must specify [/|-][r|R] command regular expression
         * @param aCommandRegex - command regular expression.
         * @return - This builder.
         */
        public Builder onCommandRegex(String aCommandRegex) {
            this.commandReg = aCommandRegex;
            return this;
        }

        /**
         * Set command action to {@link ActionBranch} branch instance.
         * @param anAction - user action.
         * @return - This builder.
         */
        public Builder ofAction(Action anAction) {
            this.branchAction = anAction;
            return this;
        }

        /**
         * Build {@link ActionBranch} branch.
         * @return - Created {@link ActionBranch} branch.
         */
        public ActionBranch build() {
            return new ActionBranch(this.commandReg, this.branchAction);
        }
    }
}
