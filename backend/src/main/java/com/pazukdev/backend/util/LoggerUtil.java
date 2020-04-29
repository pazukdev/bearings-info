package com.pazukdev.backend.util;

import com.pazukdev.backend.entity.Item;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.service.EmailSenderService;
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

    public static void warn(final String message) {
        LOGGER.warn(message);
    }

    public static void error(final String message) {
        LOGGER.error(message);
    }

    public static void warn(final String subject, final List<String> messages) {
        boolean printStartAndEnd = messages.size() > 0;
        if (printStartAndEnd) {
            warn("--- report start ---");
        }
        warn(subject);
        messages.forEach(LoggerUtil::warn);
        messages.clear();
        if (printStartAndEnd) {
            warn("--- report end ---");
        }
    }

    public static void warn(final List<String> messages,
                            final Item item,
                            final UserEntity user,
                            final EmailSenderService service) {
        final String subject = item + " changed by " + user.getName();
        if (service != null) {
            final String text = MessageUtil.toString(messages);
            service.emailToYourself(subject, text);
        }
        warn(subject, messages);
    }

}
