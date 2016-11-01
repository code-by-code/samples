package org.scalable.web.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Logger factory class to generate logger instances for the project.
 *
 * @author Jino George
 */
public class LoggerFactory
{
    /**
     * Holds the logger instances.
     */
    private static Map<Class<?>, Logger> loggers = new HashMap<>();

    /**
     * Holds the console logging handler.
     */
    private static Handler systemOut = new ConsoleHandler();
    static
    {
        systemOut.setFormatter(getFormator());
    }

    /**
     * Gets an instance of the {@link Logger} with the common settings.
     *
     * @param clazz
     * @return {@link Logger}
     */
    public static Logger getLogger(Class<?> clazz)
    {
        return loggers.computeIfAbsent(clazz, LoggerFactory::createLogger);
    }

    /**
     * Gets an instance of the {@link Logger} with the common settings.
     *
     * @param clazz
     * @return {@link Logger}
     */
    private static Logger createLogger(Class<?> clazz)
    {
        Logger logger = Logger.getLogger(clazz.getName());
        logger.addHandler(systemOut);
        logger.setLevel(systemOut.getLevel());
        logger.setUseParentHandlers(false);
        return logger;
    }

    /**
     * Sets (updates) the logging level on the console.
     *
     * @param level
     */
    public static void setConsoleLoggingLevel(Level level)
    {
        systemOut.setLevel(level);
        for(Entry<Class<?>, Logger> logger : loggers.entrySet())
        {
            logger.getValue().setLevel(level);
        }
    }

    /**
     * Gets the {@link Formatter} for the logger.
     *
     * @return {@link Formatter}
     */
    public static Formatter getFormator()
    {
        return new Formatter()
        {
            @Override
            public String format(LogRecord record)
            {
                return record.getLevel() + ":" + record.getMessage() + "\n";
            }
        };
    }
}
