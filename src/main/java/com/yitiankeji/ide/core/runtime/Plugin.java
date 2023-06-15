package com.yitiankeji.ide.core.runtime;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Plugin {

    private final String id;
    private final String version;
    private final String name;
    private final String description;
    private final URL location;
    private final Log log;
    private final List<Plugin> dependencies = new ArrayList<>();
    private final ClassLoader classLoader;

    private int state;

    public Plugin(String id, String version, String name, String description, URL location) throws IOException {
        this.id = id;
        this.version = version;
        this.name = name;
        this.description = description;
        this.location = location;
        classLoader = new URLClassLoader(new URL[]{location}, Plugin.class.getClassLoader());
        log = new Log(this);
    }

    void setState(int state) {
        this.state = state;
    }

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

    public Class<?> loadClass(String className) throws ClassNotFoundException {
        try {
            return classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            for (Plugin dependency : dependencies) {
                try {
                    return dependency.loadClass(className);
                } catch (ClassNotFoundException ignored) {
                }
            }
            throw e;
        }
    }

    public URL getResource(String name) {
        URL resource = classLoader.getResource(name);
        if (resource != null) {
            return resource;
        }

        for (Plugin dependency : dependencies) {
            resource = dependency.getResource(name);
            if (resource != null) {
                return resource;
            }
        }

        return null;
    }

    public List<URL> getResources(String name) {
        List<URL> resources = new ArrayList<>();
        URL resource = classLoader.getResource(name);
        if (resource != null) {
            resources.add(resource);
        }

        for (Plugin dependency : dependencies) {
            resource = dependency.getResource(name);
            if (resource != null) {
                resources.add(resource);
            }
        }

        return resources;
    }

    public InputStream getResourceAsStream(String name) {
        URL resource = getResource(name);
        if (resource == null) {
            return null;
        }
        try {
            return resource.openStream();
        } catch (IOException e) {
            return null;
        }
    }

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

    public Icon getIcon(String name) {
        Image image = getImage(name);
        if (image == null) {
            return null;
        }
        return new ImageIcon(image);
    }

    public void start() {
    }

    public void stop() {
    }

    public void uninstall() {
    }
}
