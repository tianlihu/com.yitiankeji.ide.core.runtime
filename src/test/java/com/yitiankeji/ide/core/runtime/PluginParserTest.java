package com.yitiankeji.ide.core.runtime;

import org.junit.jupiter.api.Test;

import java.net.URL;

public class PluginParserTest {

    private final PluginParser pluginParser = new PluginParser();

    @Test
    public void load() throws Exception {
        pluginParser.parse(getClass().getResourceAsStream("/plugin.xml"), new URL("location"));
    }
}
