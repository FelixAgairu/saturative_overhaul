package dev.felixagairu.saturative_overhaul.util;

import static dev.felixagairu.saturative_overhaul.Saturative.LOGGER;
import static dev.felixagairu.saturative_overhaul.Saturative.MOD_ID;

public class LogHelper {
    private static final String LOGGER_PREFIX = "[" + MOD_ID + "] ";

    private LogHelper() {}

    public static void info(String message, Object... args) {
        String prefixMessage = LOGGER_PREFIX + message;
        LOGGER.info(prefixMessage, args);
    }

    public static void warn(String message, Object... args) {
        String prefixMessage = LOGGER_PREFIX + message;
        LOGGER.warn(prefixMessage, args);
    }

    public static void error(String message, Object... args) {
        String prefixMessage = LOGGER_PREFIX + message;
        LOGGER.error(prefixMessage, args);
    }
}
