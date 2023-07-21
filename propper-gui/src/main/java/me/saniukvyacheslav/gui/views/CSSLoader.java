package me.saniukvyacheslav.gui.views;

import me.saniukvyacheslav.Main;

import java.net.URISyntaxException;
import java.util.Objects;

public class CSSLoader {

    // Class variables:
    private static CSSLoader instance;

    // Disable default constructor:
    private CSSLoader() {}

    public static CSSLoader getInstance() {
        if (CSSLoader.instance == null)
            CSSLoader.instance = new CSSLoader();
        return CSSLoader.instance;
    }

    public String loadStylesheets(String aCssFileName) {
             return (Main.class.getResource("/css/" + aCssFileName)).toExternalForm();

    }
}
