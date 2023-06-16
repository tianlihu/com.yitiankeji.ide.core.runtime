package com.yitiankeji.ide.core.runtime;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginRegistry {

    private final Map<String, Plugin> pluginMap = new HashMap<>();

    void register(Plugin plugin) {
        pluginMap.put(plugin.getId(), plugin);
    }

    public Plugin getPlugin(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("插件ID不能为空");
        }

        return pluginMap.get(id);
    }

    public List<Plugin> getAllPlugins() {
        return new ArrayList<>(pluginMap.values());
    }
}
