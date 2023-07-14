package me.saniukvyacheslav.args.runners;

/**
 * Runner interface define run() method, which start execution of application.
 */
public interface Runner {

    /**
     * Start execution of application.
     * @param aCommand - command to parse.
     * @param aCommandArgs - CLI arguments.
     * @return - application exit code.
     */
    int run(String aCommand, String[] aCommandArgs);

}
