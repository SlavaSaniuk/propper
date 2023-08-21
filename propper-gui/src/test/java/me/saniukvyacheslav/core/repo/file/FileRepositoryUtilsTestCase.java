package me.saniukvyacheslav.core.repo.file;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileRepositoryUtilsTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRepositoryContentDecorator.class); // Logger;

    // Global tests variables:
    private static File propertiesFile;

    @BeforeAll
    static void beforeAll() throws IOException {
        propertiesFile = new File("FileRepositoryUtilsTestCase.test");
        boolean isCreated = propertiesFile.createNewFile();
        if (!isCreated) throw new RuntimeException("Test properties file didn't created.");
        if (!propertiesFile.exists()) throw new RuntimeException("Test properties file didn't created.");
    }

    @AfterAll
    static void afterAll() {
        boolean isDeleted = propertiesFile.delete();
        if (!isDeleted) throw new RuntimeException("Test properties file didn't deleted.");
        if (propertiesFile.exists()) throw new RuntimeException("Test properties file didn't deleted.");
    }

    @Test
    void  writeStringsToFileLineByLine_listOfStrings_shouldWriteListOfString() {
        List<String> srcList = new ArrayList<>(Arrays.asList("Hello world", "My name is Vyacheslav.", "i'm 26 years old."));

        // Write src list:
        try {
            FileRepositoryUtils.writeStringsToFileLineByLine(srcList, FileRepositoryUtilsTestCase.propertiesFile);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        // Read strings:
        List<String> readStrings = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(FileRepositoryUtilsTestCase.propertiesFile))) {
            String line;
            while ((line = reader.readLine()) != null) readStrings.add(line);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        Assertions.assertEquals(srcList.size(), readStrings.size());
        LOGGER.debug("Read strings: ");
        readStrings.forEach((str) -> LOGGER.debug("\t" +str));
    }

    @Test
    void readStringsFromFileLineByLine_emptyFile_shouldReturnEmptyList() {

        List<String> readStrings = null;
        try {
           readStrings = FileRepositoryUtils.readStringsFromFileLineByLine(FileRepositoryUtilsTestCase.propertiesFile);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        Assertions.assertNotNull(readStrings);
        Assertions.assertTrue(readStrings.isEmpty());
    }

    @Test
    void readStringsFromFileLineByLine_fileWithOneLine_shouldReturnListWithOneElement() {
        List<String> srcList = new ArrayList<>(Collections.singletonList("Hello world"));

        // Write src list:
        try {
            FileRepositoryUtils.writeStringsToFileLineByLine(srcList, FileRepositoryUtilsTestCase.propertiesFile);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        List<String> readStrings = null;
        try {
            readStrings = FileRepositoryUtils.readStringsFromFileLineByLine(FileRepositoryUtilsTestCase.propertiesFile);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        Assertions.assertNotNull(readStrings);
        Assertions.assertEquals(srcList.size(), readStrings.size());
        LOGGER.debug("Read strings:");
        readStrings.forEach((line) -> LOGGER.debug("\t" +line));
    }

    @Test
    void readStringsFromFileLineByLine_fileWithFelLines_shouldReturnListWithFewElements() {
        List<String> srcList = new ArrayList<>(Arrays.asList("Hello world", "My name is Vyacheslav!.", "\n"));

        // Write src list:
        try {
            FileRepositoryUtils.writeStringsToFileLineByLine(srcList, FileRepositoryUtilsTestCase.propertiesFile);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        List<String> readStrings = null;
        try {
            readStrings = FileRepositoryUtils.readStringsFromFileLineByLine(FileRepositoryUtilsTestCase.propertiesFile);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        Assertions.assertNotNull(readStrings);
        Assertions.assertEquals(srcList.size(), readStrings.size());
        LOGGER.debug("Read strings:");
        readStrings.forEach((line) -> LOGGER.debug("\t" +line));
    }
}
