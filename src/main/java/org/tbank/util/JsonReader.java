package org.tbank.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@AllArgsConstructor
public class JsonReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonReader.class);

    private final ObjectMapper objectMapper;

    public <T> T readFrom(File file, Class<T> valueType) {
        try {
            return this.objectMapper.readValue(file, valueType);
        } catch (Exception e) {
            LOGGER.error("Exception during parsing JSON", e);
        }
        return null;
    }
}
