package com.yitiankeji.ide.core.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtensionRegistry {

    private final Map<String, List<Extension>> pointExtensionsMap = new HashMap<>();
    private final Map<String, Extension> idExtensionMap = new HashMap<>();

    public Extension getExtensionById(String extensionId) {
        return idExtensionMap.get(extensionId);
    }

    public List<Extension> getElementsByPoint(String elementName) {
        return pointExtensionsMap.get(elementName);
    }

    void saveExtension(Extension extension) {
        if (extension == null) {
            return;
        }

        idExtensionMap.put(extension.getId(), extension);
        pointExtensionsMap.computeIfAbsent(extension.getPoint(), k -> new ArrayList<>()).add(extension);
    }
}
