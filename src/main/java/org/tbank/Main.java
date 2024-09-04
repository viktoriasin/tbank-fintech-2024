package org.tbank;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tbank.util.JsonReader;
import org.tbank.util.XmlWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private final static String INPUT_PATH = "./data_src";
    private final static String OUTPUT_PATH = "./data_output";
    private final static String OUTPUT_TYPE = ".xml";

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        JsonReader jsonReader = new JsonReader(new ObjectMapper());
        XmlWriter xmlWriter = new XmlWriter(new XmlMapper());

        File srcDir = new File(INPUT_PATH);
        File resultDir = new File(OUTPUT_PATH);

        cleanDirectory(resultDir);
        List<File> files = loadData(srcDir);

        for (File file : files) {
            City city = jsonReader.readFrom(file, City.class);
            LOGGER.debug("Read city: {}", city);
            if (city != null) {
                xmlWriter.writeTo(new File(constructFileOutputName(file)), city);
            }
        }
    }

    public static void cleanDirectory(File directory) {
        LOGGER.info("Start cleaning result directory ...");
        try {
            File[] resultDirFiles = directory.listFiles();
            if (resultDirFiles != null) {
                for (File f : resultDirFiles) {
                    f.delete();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception during cleaning result dir", e);
        }
        LOGGER.info("End cleaning result directory ...");
    }

    public static List<File> loadData(File directory) {
        LOGGER.info("Start loading data ...");
        List<File> files = new ArrayList<>();

        try {
            File[] directoryListing = directory.listFiles();
            if (directoryListing != null) {
                files.addAll(Arrays.asList(directoryListing));
            } else {
                LOGGER.error("Failed to get files from directory {}", directory);
            }
        } catch (Exception e) {
            LOGGER.error("Exception during data loading", e);
        }

        LOGGER.info("End loading data ...");
        return files;
    }

    public static String constructFileOutputName(File file) {
        return "./data_output/" + file.getName().split("\\.")[0] + "-result" + OUTPUT_TYPE;
    }
}
