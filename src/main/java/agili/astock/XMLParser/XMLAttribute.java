// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 04/12/2009 9:33:35
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   XMLAttribute.java

package agili.astock.XMLParser;


public class XMLAttribute
{

    public XMLAttribute()
    {
        setName(new String());
        setValue(new Object());
    }

    public XMLAttribute(String name)
    {
        setName(name);
        setValue(new Object());
    }

    public XMLAttribute(String name, Object value)
    {
        setName(name);
        setValue(value);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof XMLAttribute)
        {
            XMLAttribute attribute = (XMLAttribute)obj;
            return name.equals(attribute.getName());
        } else
        {
            return false;
        }
    }

    public int hashCode()
    {
        int hash = 7;
        hash = 43 * hash + (name == null ? 0 : name.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return name + "=\"" + value + "\"";
    }


    private String name;
    private Object value;
}