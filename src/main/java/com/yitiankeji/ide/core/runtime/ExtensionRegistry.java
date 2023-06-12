package com.yitiankeji.ide.core.runtime;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtensionRegistry {

    private final Map<String, List<Extension>> pointExtensionsMap = new HashMap<>();
    private final Map<String, Extension> idExtensionMap = new HashMap<>();

    public Extension getExtensionById(String extensionId) {
        return idExtensionMap.get(extensionId);
    }

    public List<Extension> getElementsByPoint(String elementName) {
        return pointExtensionsMap.get(elementName);
    }

    public void parseExtensions(InputStream input) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        Document document = builderFactory.newDocumentBuilder().parse(input);
        Element root = document.getDocumentElement();

        NodeList extensionElements = root.getElementsByTagName("extension");
        for (int i = 0; i < extensionElements.getLength(); i++) {
            Element extensionElement = (Element) extensionElements.item(i);
            String point = StringUtils.trimToNull(extensionElement.getAttribute("point"));

            if (StringUtils.isEmpty(point)) {
                throw new SAXException("extension标签缺少必填属性point");
            }

            String id = StringUtils.trimToNull(extensionElement.getAttribute("id"));
            String name = StringUtils.trimToNull(extensionElement.getAttribute("name"));
            Extension extension = new Extension(id, name, point);

            if (StringUtils.isNotEmpty(id)) {
                idExtensionMap.put(id, extension);
            }
            pointExtensionsMap.computeIfAbsent(point, k -> new ArrayList<>()).add(extension);

            parseExtensionElements(extensionElement, null);
        }

        System.out.println("XML is valid against the XSD.");
    }

    private static void parseExtensionElements(Element parentNode, ExtensionElement parent) {
        NodeList children = parentNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (Node.ELEMENT_NODE != child.getNodeType()) {
                continue;
            }

            Element extensionNode = (Element) child;
            NamedNodeMap attributes = extensionNode.getAttributes();
            ExtensionElement extensionElement = new ExtensionElement();
            extensionElement.setParent(parent);
            for (int a = 0; a < attributes.getLength(); a++) {
                Attr attribute = (Attr) attributes.item(a);
                extensionElement.setAttribute(attribute.getName(), attribute.getValue());
            }

            parseExtensionElements(extensionNode, extensionElement);
            if (parent != null) {
                parent.getChildren().add(extensionElement);
            }

            System.out.println(extensionElement);
        }
    }
}
