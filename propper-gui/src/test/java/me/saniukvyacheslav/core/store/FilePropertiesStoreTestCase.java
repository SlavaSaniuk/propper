package me.saniukvyacheslav.core.store;

import me.saniukvyacheslav.prop.Property;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class FilePropertiesStoreTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilePropertiesStore.class); // Logger;

    @AfterEach
    void afterEach() {
        LOGGER.debug("Reset [FilePropertiesStore] singleton instance;");
        FilePropertiesStore.getInstance().close();
    }

    @Test
    void load_fewPropertiesToLoad_shouldSetFlagAndStoreProperties() {
        Property property1 = new Property("property.key.1", "property.value.1");
        Property property2 = new Property("property.key.2", "property.value.2");
        Property property3 = new Property("property.key.3", "property.value.3");
        List<Property> propertiesToLoad = Arrays.asList(property1, property2, property3);

        FilePropertiesStore.getInstance().load(propertiesToLoad);

        Assertions.assertTrue(FilePropertiesStore.getInstance().isLoaded());
        FilePropertiesStore.getInstance().getProperties().forEach((property -> LOGGER.debug(String.format("Loaded property: [%s];", property))));
    }

    @Test
    void load_propertiesAreNotLoaded_shouldReturnNull() {
        Assertions.assertFalse(FilePropertiesStore.getInstance().isLoaded());
        Assertions.assertNull(FilePropertiesStore.getInstance().getProperties());
    }


}
