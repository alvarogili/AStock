// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 04/12/2009 9:33:41
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   XMLNode.java
package agili.astock.XMLParser;

import java.util.HashMap;
import java.util.Vector;

// Referenced classes of package Package:
//            XMLAttribute
public class XMLNode {

    public XMLNode() {
        setChild(new Vector());
        setAttributes(new Vector());
    }

    public XMLNode(String name) {
        this();
        setName(name);
        setValue(new String());
        setParent(null);
    }

    public XMLNode(String name, String value) {
        this();
        setName(name);
        setValue(value);
        setParent(null);
    }

    public XMLNode(String name, String value, XMLNode parent) {
        this();
        setName(name);
        setValue(value);
        setParent(parent);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public XMLNode getParent() {
        return parent;
    }

    public void setParent(XMLNode parent) {
        this.parent = parent;
    }

    public Vector getChild() {
        return child;
    }

    public void setChild(Vector child) {
        this.child = child;
    }

    public Vector getAttributes() {
        return attributes;
    }

    public void setAttributes(Vector attributes) {
        this.attributes = attributes;
    }

    public void addChild(XMLNode node) {
        child.add(node);
    }

    public void addChild(String nodeName) {
        child.add(new XMLNode(nodeName));
    }

    public void addChild(String nodeName, String nodeValue) {
        child.add(new XMLNode(nodeName, nodeValue));
    }

    public void addChild(String nodeName, String nodeValue, XMLNode nodeParent) {
        child.add(new XMLNode(nodeName, nodeValue, nodeParent));
    }

    public void addAttribute(XMLAttribute attribute) {
        attributes.add(attribute);
    }

    public void addAttribute(String attributeName) {
        attributes.add(new XMLAttribute(attributeName));
    }

    public void addAttribute(String attributeName, Object attributeValue) {
        attributes.add(new XMLAttribute(attributeName, attributeValue));
    }

    public XMLNode findChild(String childName) {
        for (int i = 0; i < getChildCount(); i++) {
            if (getChild(i).getName().compareTo(childName) != 0) {
                XMLNode temp = getChild(i).findChild(childName);
                if (temp != null) {
                    return temp;
                }
            } else {
                return getChild(i);
            }
        }

        return null;
    }

    public XMLNode getChild(String childName) {
        int index = child.indexOf(new XMLNode(childName));
        if (index == -1) {
            return null;
        } else {
            return (XMLNode) child.get(index);
        }
    }

    public XMLNode getChild(int index) {
        if (index < 0 || index > child.size()) {
            return null;
        } else {
            return (XMLNode) child.get(index);
        }
    }

    public XMLAttribute getAttribute(String attributeName) {
        XMLAttribute attribute = new XMLAttribute(attributeName);
        int index = attributes.indexOf(attribute);
        return (XMLAttribute) attributes.get(index);
    }

    public XMLAttribute getAttribute(int index) {
        if (index < 0 || index > attributes.size()) {
            return null;
        } else {
            return (XMLAttribute) attributes.get(index);
        }
    }

    public int getChildCount() {
        return child.size();
    }

    public int getAttributesCount() {
        return attributes.size();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (name == null ? 0 : name.hashCode());
        hash = 97 * hash + (value == null ? 0 : value.hashCode());
        hash = 97 * hash + (parent == null ? 0 : parent.hashCode());
        hash = 97 * hash + (child == null ? 0 : child.hashCode());
        hash = 97 * hash + (attributes == null ? 0 : attributes.hashCode());
        return hash;
    }

    public String getString(int tabs) {
        String result = new String();
        for (int j = 0; j < tabs; j++) {
            result += "\t";
        }
        result += "<";
        result += name;
        //write the attributes
        for (int i = 0; i < attributes.size(); i++) {
            result += " " + attributes.get(i).toString();
        }
        if (!value.isEmpty() || child.size() > 0) {
            result += ">";

            if (child.size() > 0) {
                result += "\n";
                //write the child
                for (int i = 0; i < child.size(); i++) {
                    result += ((XMLNode) child.get(i)).getString(tabs + 1) + "\n";
                }
            } else {
                result += value;
            }
            if (child.size() > 0) {
                for (int j = 0; j < tabs; j++) {
                    result += "\t";
                }                
            }
            result += "</" + name + ">";
        } else {
            result += "/>";
        }
        return result;
    }

    @Override
    public String toString() {
        return getString(0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof XMLNode) {
            XMLNode xMLNode = (XMLNode) obj;
            return getName().compareTo(xMLNode.getName()) == 0;
        } else {
            return false;
        }
    }

    public HashMap getChildSet() {
        HashMap res = new HashMap();
        for (int i = 0; i < child.size(); i++) {
            if (!res.containsKey(((XMLNode) child.get(i)).getName())) {
                res.put(((XMLNode) child.get(i)).getName(), new Vector());
            }
            ((Vector) res.get(((XMLNode) child.get(i)).getName())).add(child.get(i));
        }

        return res;
    }
    private String name;
    private String value;
    private XMLNode parent;
    private Vector child;
    private Vector attributes;
}
