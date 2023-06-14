package com.yitiankeji.ide.core.runtime;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Plugin {

    private final String id;
    private final String version;
    private final String name;
    private final String description;
    private final String location;
    private final Log log;
    private int state;
    private final List<Plugin> dependencies = new ArrayList<>();

    public Plugin(String id, String version, String name, String description, String location) throws IOException {
        this.id = id;
        this.version = version;
        this.name = name;
        this.description = description;
        this.location = location;
        log = new Log(this);
    }

    void setState(int state) {
        this.state = state;
    }

    public URL getResource(String name) {
        return null;
    }

    public List<URL> getResources(String name) {
        return null;
    }

    public InputStream getResourceAsStream(String name) {
        return null;
    }

    public Image getImage(String name) {
        return null;
    }

    public Icon getIcon(String name) {
        return null;
    }

    public void start() {
    }

    public void stop() {
    }

    public void uninstall() {
    }
}
