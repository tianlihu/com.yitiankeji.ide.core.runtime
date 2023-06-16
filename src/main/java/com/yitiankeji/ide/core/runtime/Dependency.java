package com.yitiankeji.ide.core.runtime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Dependency {

    private String id;
    private String minVersion;
    private String maxVersion;
}
