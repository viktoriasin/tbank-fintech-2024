package org.tbank.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@AllArgsConstructor
public class XmlWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlWriter.class);

    private final XmlMapper xmlMapper;

    public <T> void writeTo(File file, T object) {
        try {
            xmlMapper.writeValue(file, object);
        } catch (Exception e) {
            LOGGER.error("Error while writing XML", e);
        }
    }
}
