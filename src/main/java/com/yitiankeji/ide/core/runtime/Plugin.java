package com.yitiankeji.ide.core.runtime;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Getter
public class Plugin {

    private final String id;
    private final String name;
    private final String description;
    private final String location;
    private final Log log;
    private int state;

    public Plugin(String id, String name, String description, String location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        log = new Log(this);
    }

    void setState(int state) {
        this.state = state;
    }

    public Log getLog() {
        return log;
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
