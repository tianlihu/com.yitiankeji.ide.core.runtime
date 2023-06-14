package com.yitiankeji.ide.core.runtime;

public class Platform {

    private static final ExtensionRegistry extensionRegistry = new ExtensionRegistry();
    private static final PluginRegistry pluginRegistry = new PluginRegistry();

    public static ExtensionRegistry getExtensionRegistry() {
        return extensionRegistry;
    }

    public static PluginRegistry getPluginRegistry() {
        return pluginRegistry;
    }
}
