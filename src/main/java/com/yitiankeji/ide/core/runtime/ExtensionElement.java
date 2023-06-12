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

    private ExtensionElement parent;
    private final Map<String, String> attributes = new HashMap<>();
    private final List<ExtensionElement> children = new ArrayList<>();

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
}
