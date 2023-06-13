package com.yitiankeji.ide.core.runtime;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExtensionParser {

    public void parseExtensions(Plugin plugin, InputStream input) throws ParserConfigurationException, IOException, SAXException {
        ExtensionRegistry extensionRegistry = ExtensionRegistry.getDefault();
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        Document document = builderFactory.newDocumentBuilder().parse(input);
        Element root = document.getDocumentElement();

        NodeList extensionNodes = root.getElementsByTagName("extension");
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
