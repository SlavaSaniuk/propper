package me.saniukvyacheslav.core.fs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class FileRepositoryTestsCase {

    @Test
    void fileRepository_fileNotExist_shouldThrowIAE() {
        File testFile = new File("asdasdad.sadsa");
        Assertions.assertThrows(IllegalArgumentException.class, () -> new FileRepository(testFile));
    }

}
