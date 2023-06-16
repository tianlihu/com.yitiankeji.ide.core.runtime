package com.yitiankeji.ide.core.runtime;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.List;

public class PluginParserTest {

    private final PluginParser pluginParser = new PluginParser();

    @Test
    public void load() throws Exception {
        pluginParser.parse(List.of(new URL[]{getClass().getResource("/plugin.xml")}));
    }
}
