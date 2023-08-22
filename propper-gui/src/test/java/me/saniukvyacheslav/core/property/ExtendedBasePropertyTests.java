package me.saniukvyacheslav.core.property;

import me.saniukvyacheslav.prop.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtendedBasePropertyTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtendedBasePropertyTests.class);

    @Test
    void getPropertyId_3NewInstances_shouldReturn3uniqueId() {
        Property property1 = new Property("test.property.1", "test.value.1");
        Property property2 = new Property("test.property.2", "test.value.1");
        Property property3 = new Property("test.property.3", "test.value.1");

        ExtendedBaseProperty ebp1 = new ExtendedBaseProperty(property1);
        ExtendedBaseProperty ebp2 = new ExtendedBaseProperty(property2);
        ExtendedBaseProperty ebp3 = new ExtendedBaseProperty(property3);

        Assertions.assertNotEquals(0L, ebp1.getPropertyId());
        Assertions.assertNotEquals(0L, ebp2.getPropertyId());
        Assertions.assertNotEquals(0L, ebp3.getPropertyId());

        LOGGER.debug(String.format("Unique ID: [%d] of property [%s];", ebp1.getPropertyId(), ebp1.getOriginalKey()));
        LOGGER.debug(String.format("Unique ID: [%d] of property [%s];", ebp2.getPropertyId(), ebp2.getOriginalKey()));
        LOGGER.debug(String.format("Unique ID: [%d] of property [%s];", ebp3.getPropertyId(), ebp3.getOriginalKey()));
    }
}
