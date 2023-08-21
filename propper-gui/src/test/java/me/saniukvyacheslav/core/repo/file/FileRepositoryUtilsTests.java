package me.saniukvyacheslav.core.repo.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class FileRepositoryUtilsTests {

    @Test
    void  writeStringsToFileLineByLine_parameter1IsNull_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, () -> FileRepositoryUtils.writeStringsToFileLineByLine(null, null));
    }

    @Test
    void  writeStringsToFileLineByLine_parameter2IsNull_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, () -> FileRepositoryUtils.writeStringsToFileLineByLine(new ArrayList<>(), null));
    }
}
