package com.yitiankeji.ide.core.runtime;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.io.File;
import java.util.List;

public class PluginParserTest {

    @Test
    public void parseJars() throws Exception {
        File jarFile1 = new File("./src/test/resources", "plugin-1.0.11.jar");
        File jarFile2 = new File("./src/test/resources", "plugin-1.0.2.jar");
        PluginParser.parse(List.of(new File[]{jarFile1, jarFile2}));

        Plugin plugin = Platform.getPluginRegistry().getPlugin("myplugin");
        System.out.println(plugin);
    }

    @Test
    public void parsePluginXml() throws Exception {
        Document document = PluginParser.parsePluginXml(getClass().getResourceAsStream("/plugin.xml"));
        Plugin plugin = PluginParser.parsePlugin(new File("test"), document.getDocumentElement());
        PluginParser.parsePlugin(plugin, document.getDocumentElement());
        System.out.println(plugin);
    }
}
