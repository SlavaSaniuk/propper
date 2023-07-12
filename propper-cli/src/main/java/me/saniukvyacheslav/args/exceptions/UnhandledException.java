package me.saniukvyacheslav.args.exceptions;

import lombok.Getter;

public class UnhandledException extends Throwable {

    public static final int unhandledExceptionCode = 999;
    @Getter
    private final Exception unhandledException;
    @Getter
    private final String unhandledExceptionMessage;

    public UnhandledException(Exception anUnhandledException) {
        super();
        this.unhandledException = anUnhandledException;
        this.unhandledExceptionMessage = String.format("Unhandled exception: [%s];", anUnhandledException.getMessage());
    }
}
