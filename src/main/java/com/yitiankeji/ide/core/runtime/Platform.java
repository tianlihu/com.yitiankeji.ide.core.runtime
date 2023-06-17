package com.yitiankeji.ide.core.runtime;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Properties;

/**
 * IDE平台运行时的核心类
 *
 * <p>
 * 包含：
 * 1. 已安装插件的注册表
 * 2. 已解析的扩展的注册表
 * 3. 平台的全局属性配置
 * 4. 平台的安装路径
 * </p>
 **/
public class Platform {

    /** 插件注册表 **/
    private static final PluginRegistry pluginRegistry = new PluginRegistry();
    /** 扩展注册表 **/
    private static final ExtensionRegistry extensionRegistry = new ExtensionRegistry();
    /** 全局属性配置 **/
    private static final Properties globalProperties = new Properties();

    /** 获取插件注册表 **/
    public static PluginRegistry getPluginRegistry() {
        return pluginRegistry;
    }

    /** 获取扩展注册表 **/
    public static ExtensionRegistry getExtensionRegistry() {
        return extensionRegistry;
    }

    /** 平台的安装路径 **/
    public static File getLocation() {
        String location = getProperty(Constants.IDE_LOCATION);
        if (StringUtils.isEmpty(location)) {
            location = ".";
        }
        return new File(location);
    }

    /** 根据key获取平台的全局属性 **/
    public static String getProperty(String key) {
        return globalProperties.getProperty(key);
    }

    /** 获取平台的全局属性 **/
    public static Properties getProperties() {
        return globalProperties;
    }
}
