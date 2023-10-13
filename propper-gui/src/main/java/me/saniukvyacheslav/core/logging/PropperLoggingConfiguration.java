package me.saniukvyacheslav.core.logging;

import me.saniukvyacheslav.Logger;
import me.saniukvyacheslav.annotation.pattern.Singleton;
import me.saniukvyacheslav.conf.LoggersConfiguration;
import me.saniukvyacheslav.message.LoggingMessageLevel;

/**
 * Application logging configuration class.
 * This class automatically create "propper-loggers-configuration" loggers configuration.
 */
@Singleton
public class PropperLoggingConfiguration {

    private static PropperLoggingConfiguration INSTANCE; // Singleton instance;
    private LoggersConfiguration productionLoggersConfiguration; // production-loggers-configuration;

    /**
     * Get current singleton instance.
     * @return - singleton instance.
     */
    public static PropperLoggingConfiguration getInstance() {
        if (PropperLoggingConfiguration.INSTANCE == null) PropperLoggingConfiguration.INSTANCE = new PropperLoggingConfiguration();
        return PropperLoggingConfiguration.INSTANCE;
    }

    /**
     * Private default constructor.
     * This constructor initializes "propper-loggers-configuration" {@link LoggersConfiguration} configuration.
     */
    private PropperLoggingConfiguration() {
        // Init production loggers configuration:
        this.initProductionLoggersConfiguration();
    }

    private void initProductionLoggersConfiguration() {
        this.productionLoggersConfiguration = LoggersConfiguration.LoggerConfigurationBuilder
                .ofName("production-loggers-configuration")
                .loggingMessagePattern("[%LEVEL%] %TIME% %NAME%: %MSG%")
                .useCanonicalLoggersName(false)
                .minimalLevelOfMessages(LoggingMessageLevel.TRACE)
                .enableConsoleOutput(true)
                .enableLoggers(true)
                .build();
    }

    /**
     * Get logger for loggable class.
     * @param aLoggableClass - loggable class.
     * @return - logger.
     */
    public static Logger getLogger(Class<?> aLoggableClass) {
        return PropperLoggingConfiguration.getInstance().productionLoggersConfiguration.getLogger(aLoggableClass);
    }

}
