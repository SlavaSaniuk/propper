package me.saniukvyacheslav.core.property;

import me.saniukvyacheslav.prop.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PropertiesChangesTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesChanges.class);

    @Test
    void getPropertiesKeysUpdates_3KeysUpdates_shouldReturnMap() {
        // Src:
        PropertyChanges changes1 = new PropertyChanges("origin.property.key.1", new Property("actual.property.key.1", null));
        PropertyChanges changes2 = new PropertyChanges("origin.property.key.2", new Property("actual.property.key.2", null));
        PropertyChanges changes3 = new PropertyChanges("origin.property.key.3", new Property("actual.property.key.3", null));
        List<PropertyChanges> allChanges = new ArrayList<>(Arrays.asList(changes1, changes2, changes3));
        LOGGER.debug("Source changes:");
        allChanges.forEach(change -> LOGGER.debug("\t" +change.toString()));

        PropertiesChanges testChanges = new PropertiesChanges();
        testChanges.getPropertiesUpdates().addAll(allChanges);

        Map<String, String> testMap = testChanges.getPropertiesKeysUpdates();
        Assertions.assertNotNull(testMap);
        Assertions.assertEquals(allChanges.size(), testMap.size());
        LOGGER.debug("Keys changes:");
        testMap.forEach((origin, actual) -> LOGGER.debug(String.format("\t [origin key: %s, actual key: %s];", origin, actual)));

    }
}
