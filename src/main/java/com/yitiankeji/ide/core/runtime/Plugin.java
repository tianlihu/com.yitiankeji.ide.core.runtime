package com.yitiankeji.ide.core.runtime;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Getter
@AllArgsConstructor
public class Plugin {

    private String id;
    private String name;
    private String description;
    private String location;
    private int state;

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
