// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 04/12/2009 9:33:46
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   XMLParser.java

package agili.astock.XMLParser;

import com.sun.org.apache.xerces.internal.parsers.SAXParser;
import java.io.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

// Referenced classes of package Package:
//            Handler, XMLNode

public class XMLParser extends SAXParser
{

    public XMLParser()
    {
        lastError = "";
    }

    public boolean parseFile(String fileName)
    {
        Handler handler = new Handler(this);
        try
        {
            lastError = "";
            setContentHandler(handler);
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(fileName), "ISO-8859-15");
            InputSource in = new InputSource(inputStreamReader);
            in.setSystemId(fileName);
            parse(in);
        }
        catch(IOException ex)
        {
            lastError = ex.getMessage();
            return false;
        }
        catch(SAXException ex)
        {
            lastError = ex.getMessage();
            return false;
        }
        return true;
    }

    public boolean parseString(String xmlString)
    {
        Handler handler = new Handler(this);
        try
        {
            lastError = "";
            setContentHandler(handler);
            StringReader sr = new StringReader(xmlString);
            InputSource is = new InputSource(sr);
            parse(is);
        }
        catch(IOException ex)
        {
            lastError = ex.getMessage();
            return false;
        }
        catch(SAXException ex)
        {
            lastError = ex.getMessage();
            return false;
        }
        return true;
    }

    public XMLNode getRootNode()
    {
        return rootNode;
    }

    public void setRootNode(XMLNode rootNode)
    {
        this.rootNode = rootNode;
    }

    public String getLastError()
    {
        return lastError;
    }

    @Override
    public String toString() {
        return rootNode.getString(0);
    }


    private XMLNode rootNode;
    private String lastError;
}