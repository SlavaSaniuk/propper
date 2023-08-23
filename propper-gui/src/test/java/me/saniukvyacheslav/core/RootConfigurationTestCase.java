package me.saniukvyacheslav.core;

import me.saniukvyacheslav.core.store.FilePropertiesStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RootConfigurationTestCase {

    @Test
    void getPropertiesStore_storeNotInitialized_shouldThrowISE() {
        Assertions.assertThrows(IllegalStateException.class, () -> RootConfiguration.getInstance().getPropertiesStore());
    }

    @Test
    void getPropertiesStore_storeIsInitialized_shouldReturnStoreImpl() {
        RootConfiguration.getInstance().initPropertiesStore(FilePropertiesStore.getInstance());
        Assertions.assertNotNull(RootConfiguration.getInstance().getPropertiesStore());
    }
}
