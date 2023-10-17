package me.saniukvyacheslav.core.util;

import java.util.ArrayList;

public class UniqueElementsList<T> extends ArrayList<T> {

    @Override
    public boolean add(T anElem) {

        for (int i=0; i<super.size(); i++) {
            T origElem = super.get(0);

            // Check if element already in list:
            if (origElem == anElem) return false;
            // Check for null:
            if (anElem == null) return super.add(null);
            // Check if element equal element in list:
            if (origElem.equals(anElem)) return false;
        }

        // Add element to list:
        return super.add(anElem);
    }
}
