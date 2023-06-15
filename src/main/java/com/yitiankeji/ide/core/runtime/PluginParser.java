package com.yitiankeji.ide.core.runtime;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PluginParser {

    public void parse(InputStream input, URL location) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        Document document = builderFactory.newDocumentBuilder().parse(input);
        Element root = document.getDocumentElement();
        if (StringUtils.equals("plugin", root.getTagName())) {
            throw new RuntimeException("plugin.xml的根节点必须是plugin");
        }

        String id = StringUtils.trimToNull(root.getAttribute("id"));
        String version = StringUtils.trimToNull(root.getAttribute("version"));
        String name = StringUtils.trimToNull(root.getAttribute("name"));
        String description = StringUtils.trimToNull(root.getAttribute("description"));
        Plugin plugin = new Plugin(id, version, name, description, location);
        Platform.getPluginRegistry().register(plugin);

        parseDependencies(plugin, Platform.getPluginRegistry(), root);
        parseExtensions(plugin, Platform.getExtensionRegistry(), root);
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
            System.out.println(id + "|" + minVersion + "|" + maxVersion);
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
}
