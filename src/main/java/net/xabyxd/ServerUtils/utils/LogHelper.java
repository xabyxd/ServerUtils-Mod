package net.xabyxd.ServerUtils.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.xabyxd.ServerUtils.Serverutils;

public class LogHelper {

    private static final Logger LOGGER = LogManager.getLogger(Serverutils.MODID);

    private LogHelper() {} // Utility class, doesn't need to be instantiated

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static void info(String message, Object... params) {
        LOGGER.info(message, params);
    }

    public static void warn(String message) {
        LOGGER.warn(message);
    }

    public static void warn(String message, Throwable throwable) {
        LOGGER.warn(message, throwable);
    }

    public static void warn(String message, Object... params) {
        LOGGER.warn(message, params);
    }

    public static void error(String message) {
        LOGGER.error(message);
    }

    public static void error(String message, Throwable throwable) {
        LOGGER.error(message, throwable);
    }

    public static void error(String message, Object... params) {
        LOGGER.error(message, params);
    }

    public static void debug(String message) {
        LOGGER.debug(message);
    }

    public static void debug(String message, Object... params) {
        LOGGER.debug(message, params);
    }
}