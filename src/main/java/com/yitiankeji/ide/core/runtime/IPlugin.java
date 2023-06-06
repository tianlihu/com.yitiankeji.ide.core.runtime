package com.yitiankeji.ide.core.runtime;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public interface IPlugin {

    String getId();

    String getName();

    String getVersion();

    String getDescription();

    String getLocation();

    ILog getLog();

    URL getResource(String name);

    List<URL> getResources(String name);

    InputStream getResourceAsStream(String name);

    Image getImage(String name);

    Icon getIcon(String name);

    IPlugin getDefault();

    void start();

    void stop();

    void uninstall();

    int getState();
}
