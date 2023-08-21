package me.saniukvyacheslav.core.property;

import lombok.Getter;
import me.saniukvyacheslav.prop.Property;
import me.saniukvyacheslav.prop.PropertyWrapper;

/**
 * Single property changes.
 * If property was removed, aChangedProperty will be null.
 */
public class PropertyChanges {

    @Getter private final String originPropertyKey;
    @Getter private final Property changedProperty;

    /**
     * Construct new instance.
     * @param anOriginKey - origin property key.
     * @param aChangedProperty - changes property key, value.
     */
    public PropertyChanges(String anOriginKey, Property aChangedProperty) {
        PropertyWrapper.checkPropertyKey(anOriginKey);

        this.originPropertyKey = anOriginKey;
        this.changedProperty = aChangedProperty;
    }

    @Override
    public String toString() {
        return String.format("PropertyChanges[originPropertyKey: %s, changes: [%s]]", originPropertyKey, changedProperty);
    }
}
