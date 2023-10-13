package me.saniukvyacheslav.core.util;

import me.saniukvyacheslav.prop.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PropertiesListTests {


    @Test
    void ofList_originListIsNull_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, () -> PropertiesList.ofList(null));
    }

    @Test
    void ofList_emptyOriginList_shouldReturnEmptyOriginList() {
        PropertiesList list = PropertiesList.ofList(new ArrayList<>());
        Assertions.assertNotNull(list);
        Assertions.assertTrue(list.getProperties().isEmpty());
    }

    @Test
    void ofList_originListHasNullValues_shouldSkipIt() {
        List<Property> originList = new ArrayList<>(Arrays.asList(null, new Property("my.name", null), null));
        PropertiesList list = PropertiesList.ofList(originList);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(1, list.getProperties().size());

    }

    @Test
    void ofList_standardList_shouldReturnPropertiesList() {
        List<Property> originList =
                new ArrayList<>(Arrays.asList(new Property("my.age", "26"), new Property("my.name", null)));
        PropertiesList list = PropertiesList.ofList(originList);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(2, list.getProperties().size());
    }

    private PropertiesList standardList() {
        List<Property> originList = new ArrayList<>(
                Arrays.asList(new Property("my.name", "Slava"), new Property("my.age", "26"), new Property("i.like", "beer"))
        );
        return PropertiesList.ofList(originList);
    }

    @Test
    void getByKey_keyIsNull_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, () -> this.standardList().getByKey(null));
    }

    @Test
    void getByKey_propertyWithSpecifiedKeyHasNotFound_shouldReturnNull() {
        Assertions.assertNull(this.standardList().getByKey("i.do.not.like"));
    }

    @Test
    void getByKey_propertyHasBeenFounded_shouldReturnProperty() {
        String expectedKey = "my.name";
        String expectedValue = "Slava";
        Property property = this.standardList().getByKey(expectedKey);
        Assertions.assertNotNull(property);
        Assertions.assertEquals(expectedKey, property.getPropertyKey());
        Assertions.assertEquals(expectedValue, property.getPropertyValue());
    }
}
