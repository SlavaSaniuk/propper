package me.saniukvyacheslav.args.actions;

import lombok.Getter;

public class ActionResult {

    @Getter
    private final int exitCode;
    @Getter
    private final Object actionResult;
    @Getter
    private final boolean isException;
    @Getter
    private final String exceptionMessage;

    private ActionResult(int aExitCode, Object aActionResult, boolean aExceptionFlag, String anExceptionMessage) {
        this.exitCode = aExitCode;
        this.actionResult = aActionResult;
        this.isException = aExceptionFlag;
        this.exceptionMessage = anExceptionMessage;
    }

    public static ActionResult ok(Object result) {
        return new ActionResult(0, result, false, null);
    }

    public static ActionResult exception(int aCode, String anExceptionMessage) {
        return new ActionResult(aCode, null, true, anExceptionMessage);
    }

}
