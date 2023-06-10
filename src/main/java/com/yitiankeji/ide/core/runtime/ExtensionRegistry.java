package com.yitiankeji.ide.core.runtime;

import java.util.HashMap;
import java.util.Map;

public class ExtensionRegistry {

    private final Map<String, ExtensionElement> extensionElementMap = new HashMap<>();

    public ExtensionElement getExtensionElementById(String extensionId) {
        return extensionElementMap.get(extensionId);
    }

    public void addExtensionElement(String extensionId, ExtensionElement extensionElement) {
        extensionElementMap.put(extensionId, extensionElement);
    }
}
