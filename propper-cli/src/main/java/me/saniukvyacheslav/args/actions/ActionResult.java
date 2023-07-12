package me.saniukvyacheslav.args.actions;

import lombok.Getter;

/**
 * {@link ActionResult} instance represent result of action execution.
 * Use {@link ActionResult#ok(Object)} method to create normal result with Object parameter as user action result.
 * Use {@link ActionResult#exception(int, String)} method to create not normal result (If exception occurs)
 * with program exit code and exception message.
 */
public class ActionResult {

    @Getter
    private final int exitCode; // Program exit code;
    @Getter
    private final Object actionResult; // Normal action result;
    @Getter
    private final boolean isException; // Exception flag;
    @Getter
    private final String exceptionMessage; // Exception message;

    /**
     * Construct new action result with specified parameters.
     * @param anExitCode - program exit code.
     * @param anActionResult - normal action result.
     * @param anExceptionFlag - isException flag.
     * @param anExceptionMessage - exception message.
     */
    private ActionResult(int anExitCode, Object anActionResult, boolean anExceptionFlag, String anExceptionMessage) {
        this.exitCode = anExitCode;
        this.actionResult = anActionResult;
        this.isException = anExceptionFlag;
        this.exceptionMessage = anExceptionMessage;
    }

    /**
     * Construct new normal action result instance with specified result and exit code - 0.
     * @param result - user action result.
     * @return - normal action result.
     */
    public static ActionResult ok(Object result) {
        return new ActionResult(0, result, false, null);
    }

    /**
     * Construct new not normal action result with specified program exit code and exception message.
     * @param aCode - program exit code.
     * @param anExceptionMessage - exception message.
     * @return - not normal action result.
     */
    public static ActionResult exception(int aCode, String anExceptionMessage) {
        return new ActionResult(aCode, null, true, anExceptionMessage);
    }

}
