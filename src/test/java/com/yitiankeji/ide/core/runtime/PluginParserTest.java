package com.yitiankeji.ide.core.runtime;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

public class PluginParserTest {

    private final PluginParser pluginParser = new PluginParser();

    @Test
    public void load() throws Exception {
        File jarFile1 = new File("./src/test/resources", "plugin-1.0.11.jar");
        File jarFile2 = new File("./src/test/resources", "plugin-1.0.2.jar");
        pluginParser.parse(List.of(new File[]{jarFile1, jarFile2}));

        Plugin plugin = Platform.getPluginRegistry().getPlugin("myplugin");
        System.out.println(plugin);
    }
}
