package com.yitiankeji.ide.core.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtensionElement {

    private String id;
    private String name;
    private ExtensionElement parent;
    private final Map<String, String> attributes = new HashMap<>();
    private final List<ExtensionElement> children = new ArrayList<>();

    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public ExtensionElement getParent() {
        return parent;
    }

    void setParent(ExtensionElement parent) {
        this.parent = parent;
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

    public Object createExtentionObject() {
        return null;
    }
}
