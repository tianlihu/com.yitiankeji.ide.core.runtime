package com.yitiankeji.ide.core.runtime;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Extension {

    private final String id;
    private final String name;
    private final String point;
    private final List<ExtensionElement> extensionElements = new ArrayList<>();

    public Extension(String id, String name, String point) {
        this.id = id;
        this.name = name;
        this.point = point;
    }
}
