package com.yitiankeji.ide.core.runtime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/** 插件依赖 **/
@Getter
@ToString
@AllArgsConstructor
public class Dependency {

    /** 依赖的插件ID **/
    private String id;
    /** 依赖的插件最低版本 **/
    private String minVersion;
    /** 依赖的插件最高版本 **/
    private String maxVersion;
}
