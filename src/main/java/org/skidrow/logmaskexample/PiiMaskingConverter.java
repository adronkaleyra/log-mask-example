package org.skidrow.logmaskexample;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.core.env.Environment;

public class PiiMaskingConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        return maskMessage(event.getFormattedMessage());
    }

    private String maskMessage(String message) {
        boolean turnOn = getMaskingEnabled();
        if (!turnOn)
            return message;
        String emailAddressRegex = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b";
        message = message.replaceAll(emailAddressRegex, "****");
        String ipAddressRegex = "\\b(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\\b";
        message = message.replaceAll(ipAddressRegex, "****");
        String phoneRegex = "\\b(1[-+]?\\s?)?(\\(\\d{3}\\)\\s?\\d{3}[-.]?\\d{4}|\\d{3}[-.]?\\d{3}[-.]?\\d{4})\\b";
        message = message.replaceAll(phoneRegex, "****");
        String internationalPhoneRegex = "\\b\\+?\\d{1,3}?[-.\\s]?\\d{1,4}?[-.\\s]?\\d{1,4}?[-.\\s]?\\d{1,4}(?:\\s*x\\d{1,5})?\\b";
        message = message.replaceAll(internationalPhoneRegex, "****");
        return message;
    }

    private boolean getMaskingEnabled() {
        Environment env = ApplicationContextProvider.getApplicationContext().getBean(Environment.class);
        return env.getProperty("masking.enabled", Boolean.class, true);
    }
}


