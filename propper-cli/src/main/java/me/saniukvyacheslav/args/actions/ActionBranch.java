package me.saniukvyacheslav.args.actions;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ActionBranch {

    @Getter
    private String commandRegex;
    @Getter
    private List<String> commandArguments;
    @Getter
    private Action action;

    private ActionBranch(String aCommandRegex, List<String> aCommandArguments, Action anAction) {
        this.commandRegex = aCommandRegex;
        this.commandArguments = aCommandArguments;
        this.action = anAction;
    }

    public static class Builder {

        private String commandReg;

        private List<String> commandArgs;
        private Action branchAction;

        public Builder() {}

        public Builder onCommandRegex(String aCommandRegex) {
            this.commandReg = aCommandRegex;
            return this;
        }

        public Builder withArguments(String[] anArguments) {
            this.commandArgs = Arrays.asList(anArguments);
            return this;
        }

        public Builder ofAction(Action anAction) {
            this.branchAction = anAction;
            return this;
        }

        public ActionBranch build() {
            return new ActionBranch(this.commandReg, this.commandArgs, this.branchAction);
        }

    }

}
