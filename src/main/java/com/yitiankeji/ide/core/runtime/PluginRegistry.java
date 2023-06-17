package com.yitiankeji.ide.core.runtime;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 插件注册表 **/
public class PluginRegistry {

    /** 插件注册表 **/
    private final Map<String, Plugin> registry = new HashMap<>();

    /** 注册插件，每个插件只保留一个版本，且为最新版本 **/
    void register(Plugin plugin) {
        Plugin registedPlugin = registry.get(plugin.getId());
        if (registedPlugin == null) {
            registry.put(plugin.getId(), plugin);
            return;
        }
        // 如果当前版本比原来注册的版本新，则把当前版本注册到注册表中
        if (VersionComparator.compare(plugin.getVersion(), registedPlugin.getVersion()) < 0) {
            registry.put(plugin.getId(), plugin);
        }
    }

    /** 根据ID获取插件 **/
    public Plugin getPlugin(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("插件ID不能为空");
        }

        return registry.get(id);
    }

    /** 获取注册表中所有插件 **/
    public List<Plugin> getPlugins() {
        return new ArrayList<>(registry.values());
    }
}
