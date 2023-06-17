package com.yitiankeji.ide.core.runtime;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/** 扩展 **/
@Getter
@ToString
public class Extension {

    /** 扩展ID **/
    private final String id;
    /** 扩展名称 **/
    private final String name;
    /** 扩展 **/
    private final String point;
    /** 扩展的元素所在的插件 **/
    @ToString.Exclude
    private final Plugin plugin;
    /** 扩展下的子元素列表 **/
    private final List<ExtensionElement> extensionElements = new ArrayList<>();

    /** 扩展构造器 **/
    public Extension(String id, String name, String point, Plugin plugin) {
        this.id = id;
        this.name = name;
        this.point = point;
        this.plugin = plugin;
    }
}
