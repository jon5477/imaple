/*
 * Haiiiiiiii faggot
 */
package imaple.xml;

//~--- non-JDK imports --------------------------------------------------------

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Point;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author David
 */
public class XMLDomData implements XMLData {
    private File imageDataDir;
    private Node node;

    public XMLDomData(InputStream is) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(is);

            this.node = document.getFirstChild();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private XMLDomData(Node node) {
        this.node = node;
    }

    @Override
    public XMLData getChildByPath(String path) {
        String segments[] = path.split("/");

        if (segments[0].equals("..")) {
            return ((XMLData) getParent()).getChildByPath(path.substring(path.indexOf("/") + 1));
        }

        Node myNode = node;

        for (int x = 0; x < segments.length; x++) {
            NodeList childNodes = myNode.getChildNodes();
            boolean foundChild = false;

            for (int i = 0; i < childNodes.getLength(); i++) {
                Node childNode = childNodes.item(i);

                if ((childNode.getNodeType() == Node.ELEMENT_NODE)
                        && childNode.getAttributes().getNamedItem("name").getNodeValue().equals(segments[x])) {
                    myNode = childNode;
                    foundChild = true;
                    break;
                }
            }

            if (!foundChild) {
                return null;
            }
        }

        XMLDomData ret = new XMLDomData(myNode);

        ret.imageDataDir = new File(imageDataDir, getName() + "/" + path).getParentFile();
        return ret;
    }

    @Override
    public List<XMLData> getChildren() {
        List<XMLData> ret = new ArrayList<XMLData>();
        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);

            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                XMLDomData child = new XMLDomData(childNode);

                child.imageDataDir = new File(imageDataDir, getName());
                ret.add(child);
            }
        }

        return ret;
    }

    @Override
    public Object getData() {
        NamedNodeMap attributes = node.getAttributes();
        XMLDataType type = getType();

        switch (type) {
        case DOUBLE :
        case FLOAT :
        case INT :
        case SHORT :
        case STRING :
        case UOL : {
            String value = attributes.getNamedItem("value").getNodeValue();

            switch (type) {
            case DOUBLE :
                return Double.valueOf(Double.parseDouble(value));
            case FLOAT :
                return Float.valueOf(Float.parseFloat(value));
            case INT :
                return Integer.valueOf(Integer.parseInt(value));
            case SHORT :
                return Short.valueOf(Short.parseShort(value));
            case STRING :
            case UOL :
                return value;
            }
        }
        case VECTOR : {
            String x = attributes.getNamedItem("x").getNodeValue();
            String y = attributes.getNamedItem("y").getNodeValue();

            return new Point(Integer.parseInt(x), Integer.parseInt(y));
        }
        case CANVAS : {
            String width = attributes.getNamedItem("width").getNodeValue();
            String height = attributes.getNamedItem("height").getNodeValue();

            return null;    // new FileStoredPngMapleCanvas(Integer.parseInt(width), Integer.parseInt(height), new File(

            // imageDataDir, getName() + ".png"));
        }
        }

        return null;
    }

    @Override
    public XMLDataType getType() {
        String nodeName = node.getNodeName();

        if (nodeName.equals("imgdir")) {
            return XMLDataType.PROPERTY;
        } else if (nodeName.equals("canvas")) {
            return XMLDataType.CANVAS;
        } else if (nodeName.equals("convex")) {
            return XMLDataType.CONVEX;
        } else if (nodeName.equals("sound")) {
            return XMLDataType.SOUND;
        } else if (nodeName.equals("uol")) {
            return XMLDataType.UOL;
        } else if (nodeName.equals("double")) {
            return XMLDataType.DOUBLE;
        } else if (nodeName.equals("float")) {
            return XMLDataType.FLOAT;
        } else if (nodeName.equals("int")) {
            return XMLDataType.INT;
        } else if (nodeName.equals("short")) {
            return XMLDataType.SHORT;
        } else if (nodeName.equals("string")) {
            return XMLDataType.STRING;
        } else if (nodeName.equals("vector")) {
            return XMLDataType.VECTOR;
        } else if (nodeName.equals("null")) {
            return XMLDataType.IMG_0x00;
        }

        return null;
    }

    @Override
    public DataEntity getParent() {
        Node parentNode = node.getParentNode();

        if (parentNode.getNodeType() == Node.DOCUMENT_NODE) {
            return null;
        }

        XMLDomData parentData = new XMLDomData(parentNode);

        parentData.imageDataDir = imageDataDir.getParentFile();
        return parentData;
    }

    @Override
    public String getName() {
        return node.getAttributes().getNamedItem("name").getNodeValue();
    }

    @Override
    public Iterator<XMLData> iterator() {
        return getChildren().iterator();
    }
}
