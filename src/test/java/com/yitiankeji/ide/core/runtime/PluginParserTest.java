package com.yitiankeji.ide.core.runtime;

import org.junit.jupiter.api.Test;

public class PluginParserTest {

    private final PluginParser pluginParser = new PluginParser();

    @Test
    public void load() throws Exception {
        Plugin plugin = new Plugin("id",  "1.0.0", "name", "description", "location");
        pluginParser.parse(plugin, getClass().getResourceAsStream("/plugin.xml"));
    }
}
