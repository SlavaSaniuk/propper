package me.saniukvyacheslav.prop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyTestCase.class);

    @Test
    void parse_propertyStringWithoutValue_shouldReturnPropertyWithoutValue() {
        String testedString = "property.key=";
        Property parsedProperty = Property.parse(testedString);
        Assertions.assertNotNull(parsedProperty);
        Assertions.assertEquals("property.key", parsedProperty.getPropertyKey());
        Assertions.assertEquals("", parsedProperty.getPropertyValue());
        LOGGER.debug(String.format("Tested string: [%s]; Parsed property: [%s].", testedString, parsedProperty));
    }

    @Test
    void parse_validPropertyString_shouldReturnPropertyWithoutValue() {
        String propertyKey = "property.key";
        String propertyValue = "propertyValue";
        String testedString = propertyKey +"=" +propertyValue;

        Property parsedProperty = Property.parse(testedString);
        Assertions.assertNotNull(parsedProperty);
        Assertions.assertEquals(propertyKey, parsedProperty.getPropertyKey());
        Assertions.assertEquals(propertyValue, parsedProperty.getPropertyValue());
        LOGGER.debug(String.format("Tested string: [%s]; Parsed property: [%s].", testedString, parsedProperty));
    }

    @Test
    void parse_propertyIsValidButStartWithSpaces_shouldReturnPropertyWithoutLeadingSpaces() {
        String propertyKey = "property.key";
        String propertyValue = "propertyValue";
        String testedString = "   " +propertyKey +"=" +propertyValue;

        Property parsedProperty = Property.parse(testedString);
        Assertions.assertNotNull(parsedProperty);
        Assertions.assertEquals(propertyKey, parsedProperty.getPropertyKey());
        Assertions.assertEquals(propertyValue, parsedProperty.getPropertyValue());
        LOGGER.debug(String.format("Tested string: [%s]; Parsed property: [%s].", testedString, parsedProperty));
    }

}
