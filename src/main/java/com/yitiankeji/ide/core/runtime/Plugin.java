package com.yitiankeji.ide.core.runtime;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * 插件类。IDE模块化管理的最小单元
 *
 * <p>
 * Plugin类是IDE插件的配置类。Plugin 类提供了一些常用的方法，例如获取插件 ID、获取日志器、获取插件状态等。
 * 插件被视为IDE平台中的模块化单元。在启动时，每个插件都会被加载到平台中，并由平台负责管理和卸载。
 * Plugin类提供了相应的生命周期方法，以便插件可以在特定时机执行相应的操作，例如在启动时初始化，在停止时清理资源等。
 * 需要注意的是，Plugin类的实例只有一个，因此不建议在插件中自行实例化此类。
 * 如果Plugin在平台中存在多个版本，只有最高版本会被注册到IDE。
 * </p>
 **/
@Getter
@ToString
public class Plugin {

    /** 插件ID **/
    private final String id;
    /** 插件版本 **/
    private final String version;
    /** 插件名称 **/
    private final String name;
    /** 插件描述 **/
    private final String description;
    /** 插件所在的路径 **/
    private final File location;
    /** 插件的日志对象 **/
    private final Log log;
    /** 插件的依赖列表 **/
    private final List<Dependency> dependencies = new ArrayList<>();
    /** 插件的扩展列表 **/
    private final List<Extension> extensions = new ArrayList<>();
    /** 插件的类加载器 **/
    private final ClassLoader classLoader;

    /** 插件的生命周期状态 **/
    private int state;

    public Plugin(String id, String version, String name, String description, File location) throws IOException {
        this.id = id;
        this.version = version;
        this.name = name;
        this.description = description;
        this.location = location;
        classLoader = new URLClassLoader(new URL[]{location.toURI().toURL()}, Plugin.class.getClassLoader());
        log = new Log(this);
    }

    /** 设置插件的生命周期状态 **/
    void setState(int state) {
        this.state = state;
    }

    /**
     * 根据类名创建插件中类对象(含依赖插件)
     *
     * <p>
     * 1. 使用类的无参构造器构建对象。
     * 2. 如果当前插件和所依赖的插件都有相同的类，优先使用当前插件的类来创建对象
     * 3. 如果未找到类，或创建对象不成功，返回null
     * </p>
     */
    @SuppressWarnings("unchecked")
    public <T> T createExecutableObject(String className) {
        if (StringUtils.isEmpty(className)) {
            return null;
        }

        try {
            Class<?> clazz = loadClass(className);
            return (T) clazz.getConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据类名加载类对象
     *
     * <p>
     * 1. 使用类加载器加载类对象。
     * 2. 如果当前插件和所依赖的插件都有相同的类对象，优先使用当前插件的类对象，当前插件中无此类，则在依赖插件中加载此类
     * 3. 如果未找到类，返回<b> null </b>
     * </p>
     */
    public Class<?> loadClass(String className) {
        try {
            return classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            for (Dependency dependency : dependencies) {
                Plugin dependencyPlugin = Platform.getPluginRegistry().getPlugin(dependency.getId());
                if (dependencyPlugin == null) {
                    continue;
                }
                Class<?> clazz = dependencyPlugin.loadClass(className);
                if (clazz == null) {
                    continue;
                }
                return clazz;
            }
            return null;
        }
    }

    /**
     * 根据相对路径查找资源
     *
     * <p>
     * 1. 使用类加载器查找资源。
     * 2. 如果当前插件和所依赖的插件都有相同的资源，优先使用当前插件的资源，当前插件中无此资源，则在依赖插件中加载此资源
     * 3. 如果未找到资源，返回<b> null </b>
     * </p>
     */
    public URL getResource(String path) {
        URL resource = classLoader.getResource(path);
        if (resource != null) {
            return resource;
        }

        for (Dependency dependency : dependencies) {
            Plugin dependencyPlugin = Platform.getPluginRegistry().getPlugin(dependency.getId());
            if (dependencyPlugin == null) {
                continue;
            }
            resource = dependencyPlugin.getResource(path);
            if (resource == null) {
                continue;
            }
            return resource;
        }

        return null;
    }

    /**
     * 根据相对路径查找资源列表
     *
     * <p>
     * 1. 使用类加载器查找资源列表。
     * 2. 如果当前插件和所依赖的插件都有相同的资源，会把所有插件中的资源全部查询出来并返回
     * 3. 如果未找到资源，返回长度为0的列表，而不是<code> null </code>
     * </p>
     */
    public List<URL> getResources(String path) {
        List<URL> resources = new ArrayList<>();
        URL resource = classLoader.getResource(path);
        if (resource != null) {
            resources.add(resource);
        }

        for (Dependency dependency : dependencies) {
            Plugin dependencyPlugin = Platform.getPluginRegistry().getPlugin(dependency.getId());
            if (dependencyPlugin == null) {
                continue;
            }
            resource = dependencyPlugin.getResource(path);
            if (resource == null) {
                continue;
            }
            resources.add(resource);
        }

        return resources;
    }

    /**
     * 根据相对路径查找资源，并返回流对象
     *
     * <p>
     * 1. 使用类加载器查找资源。
     * 2. 如果当前插件和所依赖的插件都有相同的资源，优先使用当前插件的资源，当前插件中无此资源，则在依赖插件中加载此资源
     * 3. 如果未找到资源，返回<b> null </b>
     * </p>
     */
    public InputStream getResourceAsStream(String path) {
        URL resource = getResource(path);
        if (resource == null) {
            return null;
        }
        try {
            return resource.openStream();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 根据相对路径查找图片资源，并返回图片对象
     *
     * <p>
     * 1. 使用类加载器查找资源。
     * 2. 如果当前插件和所依赖的插件都有相同的资源，优先使用当前插件的资源，当前插件中无此资源，则在依赖插件中加载此资源
     * 3. 如果未找到资源，返回<b> null </b>
     * </p>
     */
    public Image getImage(String name) {
        URL resource = getResource(name);
        if (resource == null) {
            return null;
        }

        try {
            return ImageIO.read(resource);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 根据相对路径查找图片资源，并返回图标对象
     *
     * <p>
     * 1. 使用类加载器查找资源。
     * 2. 如果当前插件和所依赖的插件都有相同的资源，优先使用当前插件的资源，当前插件中无此资源，则在依赖插件中加载此资源
     * 3. 如果未找到资源，返回<b> null </b>
     * </p>
     */
    public Icon getIcon(String name) {
        Image image = getImage(name);
        if (image == null) {
            return null;
        }
        return new ImageIcon(image);
    }

    /** 启动插件 **/
    public void start() {
    }

    /** 停止插件 **/
    public void stop() {
    }

    /** 卸载插件 **/
    public void uninstall() {
    }
}
