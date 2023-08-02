package me.saniukvyacheslav.prop;

import me.saniukvyacheslav.exceptions.PropertyIsInvalidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class PropertyTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyTests.class);

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

    @Test
    void safeParse_strIsNull_shouldThrowPE() {
        Assertions.assertThrows(ParseException.class,() -> Property.safeParse(null));
    }

    @Test
    void safeParse_strIsEmpty_shouldThrowPE() {
        Assertions.assertThrows(ParseException.class,() -> Property.safeParse(""));
    }

    @Test
    void safeParse_strIsComment_shouldThrowPE() {
        Assertions.assertThrows(ParseException.class,() -> Property.safeParse("!anu.comment string"));
    }

    @Test
    void safeParse_strWithoutKey_shouldThrowPE() {
        Assertions.assertThrows(ParseException.class,() -> Property.safeParse("=property.value"));
    }

    @Test
    void safeParse_strStartsWithoutEqualSign_shouldThrowPE() {
        Assertions.assertThrows(ParseException.class,() -> Property.safeParse("property.keyproperty.value"));
    }

    @Test
    void safeParse_validPropertiesStrings_shouldParseToProperty() {

        List<Property> propertiesToParse = Arrays.asList(new Property("property.key", "property.value"),
                new Property("my.name", "any.name"),
                new Property("my.age", "36"));
        propertiesToParse.forEach((property) -> {
            try {
                Property parsed = Property.safeParse(property.toString());
                Assertions.assertEquals(property.getPropertyKey(), parsed.getPropertyKey());
                Assertions.assertEquals(property.getPropertyValue(), parsed.getPropertyValue());
                LOGGER.debug(String.format("Parsed property: [%s];", parsed));
            } catch (ParseException e) {
                Assertions.fail(e.getMessage());
            }
        });

    }

}
