package me.saniukvyacheslav.core.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Utilities class for working with {@link ExtendedBaseProperty} objects.
 */
public class ExtendedBasePropertiesHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtendedBasePropertiesHelper.class); // Logger;
    private static final Set<Long> generatedId = new HashSet<>(); // Generated ID store;

    /**
     * Private default constructor.
     * @throws InstantiationException - If someone try to create class instance.
     */
    private ExtendedBasePropertiesHelper() throws InstantiationException {
        throw new InstantiationException("Cannot create instance of [ExtendedBasePropertiesHelper] utilities class.");
    }

    private static long generatedIdL(long aLeftLimit, long aRightLimit) {
        return aLeftLimit + (long) (Math.random() * (aRightLimit - aLeftLimit));
    }

    /**
     * Generate unique application specific long id from interval.
     * @param aLeftLimit - left limit.
     * @param aRightLimit - right limit.
     * @return - generated unique id.
     */
    public static long generateId(long aLeftLimit, long aRightLimit) {
        LOGGER.debug(String.format("Generate long random number from interval [%d-%d];", aLeftLimit, aRightLimit));
        long generatedId;

        // Check if generated ID exist in Set:
        while (true) {
            generatedId = ExtendedBasePropertiesHelper.generatedIdL(aLeftLimit, aRightLimit);
            LOGGER.debug(String.format("Generated ID: [%d];", generatedId));
            if (ExtendedBasePropertiesHelper.generatedId.contains(generatedId)) LOGGER.debug(String.format("Generated id [%d] already was generated; Regenerate;", generatedId));
            else {
                ExtendedBasePropertiesHelper.generatedId.add(generatedId);
                return generatedId;
            }
        }

    }





}
