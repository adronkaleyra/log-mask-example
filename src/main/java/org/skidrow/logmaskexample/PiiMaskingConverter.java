package org.skidrow.logmaskexample;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PiiMaskingConverter extends ClassicConverter {

    private List<PiiRegexPattern> patterns;

    public PiiMaskingConverter() throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getResourceAsStream("/regexMasks.json");
            patterns = mapper.readValue(is, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new IOException("Error reading regex_patterns.json", e);
        }
    }

    @Override
    public String convert(ILoggingEvent event) {
        return maskMessage(event.getFormattedMessage());
    }

    private String maskMessage(String message) {

        boolean turnOn = getMaskingEnabled();
        if (!turnOn)
            return message;
        for (PiiRegexPattern pattern : patterns) {
            message = message.replaceAll(pattern.getRegex().toString(), "****");
        }

        return message;
    }

    private boolean getMaskingEnabled() {
        Environment env = ApplicationContextProvider.getApplicationContext().getBean(Environment.class);
        return env.getProperty("masking.enabled", Boolean.class, true);
    }
}


