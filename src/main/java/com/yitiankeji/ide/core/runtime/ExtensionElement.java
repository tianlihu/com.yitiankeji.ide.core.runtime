package com.yitiankeji.ide.core.runtime;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@ToString
public class ExtensionElement {

    private final Plugin plugin;
    private final ExtensionElement parent;
    private final Map<String, String> attributes = new HashMap<>();
    private final List<ExtensionElement> children = new ArrayList<>();

    public ExtensionElement(Plugin plugin, ExtensionElement parent) {
        this.plugin = plugin;
        this.parent = parent;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public ExtensionElement getParent() {
        return parent;
    }

    public String getAttribute(String name) {
        return attributes.get(name);
    }

    void setAttribute(String name, String value) {
        attributes.put(name, value);
    }

    public List<ExtensionElement> getChildren() {
        return children;
    }

    public <T> T createExecutableObject(String attributeName) {
        String className = getAttribute(attributeName);
        return plugin.createExecutableObject(className);
    }
}
