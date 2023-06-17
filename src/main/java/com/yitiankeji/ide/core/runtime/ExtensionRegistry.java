package com.yitiankeji.ide.core.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 扩展注册表 **/
public class ExtensionRegistry {

    /** 扩展注册表Map: 按扩展缓存 **/
    private final Map<String, List<Extension>> pointRegistry = new HashMap<>();
    /** 扩展注册表Map: 按ID缓存 **/
    private final Map<String, Extension> idRegistry = new HashMap<>();

    /** 根据ID查找扩展 **/
    public Extension getExtensionById(String extensionId) {
        return idRegistry.get(extensionId);
    }

    /** 根据扩展点查找扩展 **/
    public List<Extension> getElementsByPoint(String elementName) {
        return pointRegistry.get(elementName);
    }

    /** 把扩展缓存到注册表中 **/
    void saveExtension(Extension extension) {
        if (extension == null) {
            return;
        }

        idRegistry.put(extension.getId(), extension);
        pointRegistry.computeIfAbsent(extension.getPoint(), k -> new ArrayList<>()).add(extension);
    }
}
