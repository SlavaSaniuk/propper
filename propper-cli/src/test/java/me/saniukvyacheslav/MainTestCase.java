package me.saniukvyacheslav;

import org.junit.jupiter.api.Test;

public class MainTestCase {

    @Test
    void main_anyArguments_shouldShowArguments() {
        Main.main(new String[]{"/D", "my.name", "file.properties"});
    }
}
