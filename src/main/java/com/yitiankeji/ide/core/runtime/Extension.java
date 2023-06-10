package com.yitiankeji.ide.core.runtime;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Extension {

    private String id;
    private String name;
    private String point;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPoint() {
        return point;
    }
}
