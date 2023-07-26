package me.saniukvyacheslav.gui.views;


import me.saniukvyacheslav.gui.models.PropertyModel;
import me.saniukvyacheslav.gui.nodes.PropertyField;

public class PropertyView implements Stylable {

    private final PropertyField propertyKeyField;
    private final PropertyField propertyValueField;

    public PropertyView(PropertyModel aModel) {
        this.propertyKeyField = aModel.getKeyPropertyField();
        this.propertyValueField = aModel.getValuePropertyField();
    }

    @Override
    public void stylize() {

        // Add classes:
        this.propertyKeyField.getStyleClass().addAll(
                "property-cell-common"
        );
        this.propertyValueField.getStyleClass().addAll(
                "property-cell-common"
        );
    }

    public void setUpdatedPropertyKey(boolean isUpdated) {
        this.propertyKeyField.setUpdated(isUpdated);
    }

    public void setUpdatedPropertyValue(boolean isUpdated) {
        this.propertyValueField.setUpdated(isUpdated);
    }

    /**
     * Set/unset "updated" css pseudo class to all property fields (key and value fields).
     * @param isUpdated - "updated" css pseudo class flag.
     */
    public void setUpdatedWholeProperty(boolean isUpdated) {
        this.setUpdatedPropertyKey(isUpdated);
        this.setUpdatedPropertyValue(isUpdated);
    }
}
