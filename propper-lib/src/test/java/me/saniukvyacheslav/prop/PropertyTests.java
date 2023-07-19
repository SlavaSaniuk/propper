package me.saniukvyacheslav.prop;

import me.saniukvyacheslav.exceptions.PropertyIsInvalidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PropertyTests {

    @Test
    void parse_stringIsNull_shouldThrowNullPointerException() {
        String testedString = null;
        Assertions.assertThrows(NullPointerException.class, () -> Property.parse(testedString));
    }

    @Test
    void parse_stringIsEmpty_shouldThrowIAE() {
        String testedString = "";
        Assertions.assertThrows(IllegalArgumentException.class, () -> Property.parse(testedString));
    }

    @Test
    void parse_stringStartWithAt_shouldReturnNull() {
        String testedString = "@_any_text";
        Assertions.assertNull(Property.parse(testedString));
    }

    @Test
    void parse_stringStartWithExclamationMark_shouldReturnNull() {
        String testedString = "!_any_text";
        Assertions.assertNull(Property.parse(testedString));
    }

    @Test
    void parse_propertyKeyIsEmpty_shouldThrowPIIE() {
        String testedString = "=property_value_1";
        Assertions.assertThrows(PropertyIsInvalidException.class, () ->Property.parse(testedString));
    }

    @Test
    void parse_stringStartWithSpace_shouldThrowPIIE() {
        String testedString = " =property_value_1";
        Assertions.assertThrows(PropertyIsInvalidException.class, () ->Property.parse(testedString));
    }

    @Test
    void parse_stringWithoutEqualSign_shouldReturnNull() {
        String testedString = "any_text";
        Assertions.assertNull(Property.parse(testedString));
    }

}
