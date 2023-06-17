package com.yitiankeji.ide.core.runtime;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 扩展的元素 **/
@Getter
@ToString
public class ExtensionElement {

    /** 扩展的元素所在的插件 **/
    @ToString.Exclude
    private final Plugin plugin;
    /** 扩展的元素的父元素 **/
    @ToString.Exclude
    private final ExtensionElement parent;
    /** 扩展的元素的子元素 **/
    private final List<ExtensionElement> children = new ArrayList<>();
    /** 扩展的元素的属性列表 **/
    private final Map<String, String> attributes = new HashMap<>();

    /** 扩展的元素的构造器 **/
    public ExtensionElement(Plugin plugin, ExtensionElement parent) {
        this.plugin = plugin;
        this.parent = parent;
    }

    /** 扩展的元素所在的插件 **/
    public Plugin getPlugin() {
        return plugin;
    }

    /** 扩展的元素的父元素 **/
    public ExtensionElement getParent() {
        return parent;
    }

    /** 获取扩展的元素的属性 **/
    public String getAttribute(String name) {
        return attributes.get(name);
    }

    /** 设置扩展的元素的属性 **/
    void setAttribute(String name, String value) {
        attributes.put(name, value);
    }

    /** 获取扩展的元素的列表 **/
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /** 获取扩展的元素的子元素 **/
    public List<ExtensionElement> getChildren() {
        return children;
    }

    /** 创建扩展的元素的属性对象的类对象 **/
    public <T> T createExecutableObject(String attributeName) {
        String className = getAttribute(attributeName);
        return plugin.createExecutableObject(className);
    }
}
