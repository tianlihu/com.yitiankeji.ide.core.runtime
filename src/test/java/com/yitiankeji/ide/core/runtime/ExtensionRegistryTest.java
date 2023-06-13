package com.yitiankeji.ide.core.runtime;

import org.junit.jupiter.api.Test;

public class ExtensionRegistryTest {

    private final ExtensionRegistry extensionRegistry = new ExtensionRegistry();

    @Test
    public void load() throws Exception {
        Plugin plugin = new Plugin("id", "name", "description", "location");
        extensionRegistry.parseExtensions(plugin, getClass().getResourceAsStream("/plugin.xml"));
    }
}
