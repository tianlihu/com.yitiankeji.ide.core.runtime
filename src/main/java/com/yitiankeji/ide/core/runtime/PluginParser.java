package com.yitiankeji.ide.core.runtime;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginParser {

    /** 解析plugin.jar列表 **/
    public void parse(List<File> locations) throws IOException, ParserConfigurationException, SAXException {
        Map<String, List<ParseEntry>> parseEntryMap = new HashMap<>();

        for (File location : locations) {
            try (JarFile jarFile = new JarFile(location)) {
                JarEntry pluginXmlEntry = jarFile.getJarEntry("plugin.xml");
                if (pluginXmlEntry == null) {
                    continue;
                }

                InputStream input = jarFile.getInputStream(pluginXmlEntry);
                Document document = parsePluginXml(input);
                Element root = document.getDocumentElement();
                String id = StringUtils.trimToNull(root.getAttribute("id"));
                String version = StringUtils.trimToNull(root.getAttribute("version"));
                String name = StringUtils.trimToNull(root.getAttribute("name"));
                String description = StringUtils.trimToNull(root.getAttribute("description"));
                Plugin plugin = new Plugin(id, version, name, description, location);
                parseEntryMap.computeIfAbsent(plugin.getId(), k -> new ArrayList<>()).add(new ParseEntry(location, root, plugin));
            }
        }

        List<ParseEntry> parseEntries = getLatestVersionPluginEntries(parseEntryMap);
        parseEntryMap.clear(); // 提前释放无用的临时Map占用内存

        for (ParseEntry parseEntry : parseEntries) {
            Plugin plugin = parseEntry.plugin;
            Platform.getPluginRegistry().register(plugin);
            parsePlugin(plugin, parseEntry.rootElement);
        }

        parseEntries.clear(); // 清空临时列表，加速垃圾回收
    }

    private void parsePlugin(Plugin plugin, Element root) throws SAXException {
        parseDependencies(plugin, Platform.getPluginRegistry(), root);
        parseExtensions(plugin, Platform.getExtensionRegistry(), root);
    }

    private static Document parsePluginXml(InputStream input) throws IOException, SAXException, ParserConfigurationException {
        try (input) {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            Document document = builderFactory.newDocumentBuilder().parse(input);
            Element rootElement = document.getDocumentElement();
            if (!StringUtils.equals("plugin", rootElement.getTagName())) {
                throw new RuntimeException("plugin.xml的根节点必须是plugin");
            }
            return document;
        }
    }

    private void parseDependencies(Plugin plugin, PluginRegistry pluginRegistry, Element parentNode) {
        NodeList dependenciesNode = parentNode.getElementsByTagName("dependencies");
        if (dependenciesNode.getLength() == 0) {
            return;
        }

        NodeList dependencyNodes = ((Element) dependenciesNode.item(0)).getElementsByTagName("dependency");
        for (int i = 0; i < dependencyNodes.getLength(); i++) {
            Element dependencyNode = (Element) dependencyNodes.item(i);
            String id = dependencyNode.getAttribute("id");
            String minVersion = dependencyNode.getAttribute("minVersion");
            String maxVersion = dependencyNode.getAttribute("maxVersion");
            plugin.getDependencies().add(new Dependency(id, minVersion, maxVersion));
        }
    }

    private static void parseExtensions(Plugin plugin, ExtensionRegistry extensionRegistry, Element parentNode) throws SAXException {
        NodeList extensionNodes = parentNode.getElementsByTagName("extension");
        for (int i = 0; i < extensionNodes.getLength(); i++) {
            Element extensionElement = (Element) extensionNodes.item(i);
            String point = StringUtils.trimToNull(extensionElement.getAttribute("point"));

            if (StringUtils.isEmpty(point)) {
                throw new SAXException("extension标签缺少必填属性point");
            }

            String id = StringUtils.trimToNull(extensionElement.getAttribute("id"));
            String name = StringUtils.trimToNull(extensionElement.getAttribute("name"));
            Extension extension = new Extension(id, name, point);

            List<ExtensionElement> extensionElements = parseExtensionElements(plugin, extensionElement, null);
            extension.getExtensionElements().addAll(extensionElements);

            extensionRegistry.saveExtension(extension);
        }
    }

    private static List<ExtensionElement> parseExtensionElements(Plugin plugin, Element parentNode, ExtensionElement parent) {
        NodeList children = parentNode.getChildNodes();
        List<ExtensionElement> extensionElements = new ArrayList<>();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (Node.ELEMENT_NODE != child.getNodeType()) {
                continue;
            }

            Element extensionNode = (Element) child;
            NamedNodeMap attributes = extensionNode.getAttributes();
            ExtensionElement extensionElement = new ExtensionElement(plugin, parent);
            for (int a = 0; a < attributes.getLength(); a++) {
                Attr attribute = (Attr) attributes.item(a);
                extensionElement.setAttribute(attribute.getName(), attribute.getValue());
            }

            parseExtensionElements(plugin, extensionNode, extensionElement);
            extensionElements.add(extensionElement);
        }

        if (parent != null) {
            parent.getChildren().addAll(extensionElements);
        }

        return extensionElements;
    }

    private static List<ParseEntry> getLatestVersionPluginEntries(Map<String, List<ParseEntry>> parseEntryMap) {
        ParseEntryComparator comparator = new ParseEntryComparator();
        List<ParseEntry> result = new ArrayList<>();
        parseEntryMap.values().forEach(parseEntries -> {
            if (parseEntries.isEmpty()) {
                return;
            }

            parseEntries.sort(comparator);
            result.add(parseEntries.get(0));
        });
        return result;
    }

    @AllArgsConstructor
    private static class ParseEntry {
        File location;
        Element rootElement;
        Plugin plugin;
    }

    private static class ParseEntryComparator implements Comparator<ParseEntry> {

        @Override
        public int compare(ParseEntry o1, ParseEntry o2) {
            return VersionComparator.compare(o1.plugin.getVersion(), o2.plugin.getVersion());
        }
    }
}
