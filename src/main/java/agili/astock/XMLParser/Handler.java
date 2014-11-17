package agili.astock.XMLParser;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

// Referenced classes of package Package:
//            XMLNode, XMLAttribute, XMLParser
public class Handler
        implements ContentHandler {

    private boolean uriWritted = false;

    public Handler(XMLParser parser) {
        setParser(parser);
    }

    public void startDocument() {
    }

    public void endDocument()
            throws SAXException {
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (parser.getRootNode() == null) {
            parser.setRootNode(new XMLNode(qName));
            parser.getRootNode().setParent(null);
            node = parser.getRootNode();
        } else {
            XMLNode temp = new XMLNode(qName);
            temp.setParent(node);
            node.addChild(temp);
            node = temp;
        }
        for (int i = 0; i < attributes.getLength(); i++) {
            node.addAttribute(new XMLAttribute(attributes.getLocalName(i), attributes.getValue(i)));
        }
        if(!uri.isEmpty() && !uriWritted){
            node.addAttribute(new XMLAttribute("xmlns", uri));
            uriWritted = true;
        }

    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (node.getParent() != null) {
            node = node.getParent();
        }
    }

    public void characters(char ch[], int start, int length)
            throws SAXException {
        String value = (new String(ch, start, length)).trim();
        if(value.startsWith("<![CDATA") && !value.endsWith(">")){
            value += ">";
        }
        if (value.length() > 0) {
            node.setValue(node.getValue() == null ? "" : node.getValue().toString() + value);
        }
    }

    public void setDocumentLocator(Locator locator1) {
    }

    public void startPrefixMapping(String s, String s1)
            throws SAXException {
    }

    public void endPrefixMapping(String s)
            throws SAXException {
    }

    public void ignorableWhitespace(char ac[], int i, int j)
            throws SAXException {
    }

    public void processingInstruction(String s, String s1)
            throws SAXException {
    }

    public void skippedEntity(String s)
            throws SAXException {
    }

    public XMLParser getParser() {
        return parser;
    }

    public void setParser(XMLParser parser) {
        this.parser = parser;
    }
    private XMLParser parser;
    private XMLNode node;
}
