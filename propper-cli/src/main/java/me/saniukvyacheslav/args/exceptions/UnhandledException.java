package me.saniukvyacheslav.args.exceptions;

import lombok.Getter;
import me.saniukvyacheslav.args.actions.ActionBranch;

import java.util.List;

/**
 *  This exception instances used in cases, when developer didn't handle all supported exceptions.
 * If in {@link  me.saniukvyacheslav.args.actions.ActionExecutor#execute(ActionBranch, List)} method,
 * any {@link ActionBranch} throw exception, method catch it, and throw new {@link UnhandledException} exception.
 */
public class UnhandledException extends Throwable {

    public static final int unhandledExceptionCode = 999;
    @Getter
    private final Exception unhandledException; // Inner exception object;
    @Getter
    private final String unhandledExceptionMessage;

    /**
     * Construct new {@link UnhandledException} exception instance associated with specified exception instance.
     * @param anUnhandledException - any exception instance.
     */
    public UnhandledException(Exception anUnhandledException) {
        super();
        this.unhandledException = anUnhandledException;
        this.unhandledExceptionMessage = String.format("Unhandled exception: [%s];", anUnhandledException.getMessage());
    }
}
