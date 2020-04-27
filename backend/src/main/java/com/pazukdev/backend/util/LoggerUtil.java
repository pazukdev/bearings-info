package com.pazukdev.backend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
public class LoggerUtil {

    public final static Logger LOGGER = LoggerFactory.getLogger(LoggerUtil.class);

    public static void info(final String message) {
        LOGGER.info(message);
    }

    public static void info(final List<String> messages) {
        boolean printStartAndEnd = messages.size() > 0;
        if (printStartAndEnd) {
            info("--- report start ---");
        }
        messages.forEach(LoggerUtil::info);
        if (printStartAndEnd) {
            info("--- report end ---");
        }
    }

}
