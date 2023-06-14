package com.yitiankeji.ide.core.runtime;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PluginRegistry {

    private final Map<String, TreeMap<String, Plugin>> pluginMap = new HashMap<>();

    void register(Plugin plugin) {
        pluginMap.computeIfAbsent(plugin.getId(), k -> new TreeMap<>(new VersionComparator())).put(plugin.getVersion(), plugin);
    }

    public List<Plugin> getPlugins(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("插件ID不能为空");
        }
        Map<String, Plugin> pluginVersionMap = this.pluginMap.get(id);
        if (pluginVersionMap == null || pluginVersionMap.size() == 0) {
            return new ArrayList<>();
        }
        return new ArrayList<>(pluginVersionMap.values());
    }

    public Plugin getPlugin(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("插件ID不能为空");
        }

        Map<String, Plugin> pluginVersionMap = this.pluginMap.get(id);
        if (pluginVersionMap == null || pluginVersionMap.size() == 0) {
            return null;
        }
        if (pluginVersionMap.size() == 1) {
            return pluginVersionMap.values().iterator().next();
        }
        throw new RuntimeException("当前ID有多个插件版本：" + id + "，已存在版本号" + pluginVersionMap.keySet());
    }

    public Plugin getPlugin(String id, String version) {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("插件ID不能为空");
        }
        if (StringUtils.isEmpty(version)) {
            throw new RuntimeException("插件版本不能为空");
        }

        TreeMap<String, Plugin> pluginVersionMap = this.pluginMap.get(id);
        if (pluginVersionMap == null || pluginVersionMap.size() == 0) {
            return null;
        }
        return pluginVersionMap.get(version);
    }

    public Plugin getMaxVersionPlugin(String id) {
        Map<String, Plugin> pluginVersionMap = this.pluginMap.get(id);
        if (pluginVersionMap == null || pluginVersionMap.size() == 0) {
            return null;
        }
        return pluginVersionMap.values().iterator().next();
    }

    private static class VersionComparator implements Comparator<String> {

        private static final String VERSION_PATTERN = "(\\d+|[a-zA-Z]+)(\\.(\\d+|[a-zA-Z]+))*";
        private static final Pattern pattern = Pattern.compile(VERSION_PATTERN);

        @Override
        public int compare(String version1, String version2) {
            String[] parts1 = version1.split("\\.");
            String[] parts2 = version2.split("\\.");

            int length = Math.max(parts1.length, parts2.length);
            for (int i = 0; i < length; i++) {
                String part1 = i < parts1.length ? parts1[i] : "";
                String part2 = i < parts2.length ? parts2[i] : "";

                Matcher m1 = pattern.matcher(part1);
                Matcher m2 = pattern.matcher(part2);
                if (m1.matches() && m2.matches()) {
                    boolean isNumber1 = isNumber(part1);
                    boolean isNumber2 = isNumber(part2);

                    if (isNumber1 && isNumber2) {
                        int value1 = Integer.parseInt(part1);
                        int value2 = Integer.parseInt(part2);
                        if (value1 != value2) {
                            return value2 - value1;
                        }
                    } else if (!isNumber1 && !isNumber2) {
                        int result = part2.compareToIgnoreCase(part1);
                        if (result != 0) {
                            return result;
                        }
                    } else {
                        return isNumber1 ? 1 : -1;
                    }
                } else {
                    int result = part2.compareToIgnoreCase(part1);
                    if (result != 0) {
                        return result;
                    }
                }
            }

            return 0;
        }

        private boolean isNumber(String str) {
            try {
                Integer.parseInt(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
}
