package com.kingdee.eas.bpm.common;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.TreeWalker;
import org.xml.sax.SAXException;

import com.kingdee.eas.basedata.ws.common.TextFilter;
import com.kingdee.eas.fi.ar.app.webservice.util.WrongArgumentException;
public class XMLUtil {
    public XMLUtil(String xmlData, String billType)
    throws SAXException, IOException, WrongArgumentException
{

/*  35*/        doc = null;
/*  54*/        try
    {
/* <-MISALIGNED-> */ /*  54*/            doc = XMLUtil.builderDocument(xmlData);
    }
/* <-MISALIGNED-> */ /*  58*/        catch(ParserConfigurationException e)
    {
/* <-MISALIGNED-> */ /*  59*/            throw new SAXException(e.getMessage());
    }
}
public Map getHeadProperties(String elementName)
{
/*  71*/        Map head = XMLUtil.getBillHead(doc);
/*  72*/        return head;
}
public List getEntriesProperties(String elementXPath)
{
/*  83*/        List entries = null;

/*  85*/        try
    {
/* <-MISALIGNED-> */ /*  85*/            entries = XMLUtil.getBillEntries(doc, elementXPath);
    }
/* <-MISALIGNED-> */ /*  86*/        catch(TransformerException e) { }
/* <-MISALIGNED-> */ /*  88*/        return entries;
}
private boolean checkBillType(String billType)
{
/* <-MISALIGNED-> */ /*  92*/        Element root = doc.getDocumentElement();
/* <-MISALIGNED-> */ /*  93*/        String rootName = root.getNodeName();
/* <-MISALIGNED-> */ /*  94*/        return billType.equals(rootName);
}

public static Document builderDocument(String xmlContent)
throws SAXException, IOException, ParserConfigurationException
{

/* 119*/        DocumentBuilderFactory builder = getDocumentBuilderFactory();

/* 121*/        ByteArrayInputStream input = new ByteArrayInputStream(xmlContent.getBytes("UTF-8"));

/* 123*/        DocumentBuilder parser = builder.newDocumentBuilder();
/* 124*/        return parser.parse(input);
}
public static HashMap getBillHead(Document doc)
{
/* 134*/        if(doc == null)
/* 135*/            return null;
/* 136*/        HashMap headMap = null;
/* 137*/        NodeList childs = doc.getElementsByTagName("DATA");



/* 141*/        String value = null;
/* 142*/        if(childs != null && childs.getLength() > 0)
{/* 143*/            headMap = new HashMap();
/* 144*/            NodeList subChilds = childs.item(0).getChildNodes();
/* 145*/            for(int i = 0; subChilds != null && i < subChilds.getLength(); i++)
    {/* 146*/                if(subChilds.item(i).getNodeType() != 1)
/* 147*/                    continue;/* 147*/                Element subElement = (Element)subChilds.item(i);
/* 148*/                Node subChild = subElement.getFirstChild();
/* 149*/                if(subChild == null)
/* 150*/                    continue;/* 150*/                value = subChild.getNodeValue();

/* 152*/                if(value != null)
/* 153*/                    headMap.put(subElement.getTagName(), value.trim());
    }
}





/* 161*/        return headMap;
}
public static ArrayList getBillEntries(Document doc)
throws TransformerException
{
/* 173*/        return getBillEntries(doc, "");
}
public static ArrayList getBillEntries(Document doc, String xpath)
throws TransformerException
{
/* 226*/        if(doc == null)
/* 227*/            return null;
/* 228*/        NodeList subChilds = getNodeListFromXpath(doc, xpath);
/* 229*/        ArrayList entries = new ArrayList();



/* 233*/        HashMap entryMap = null;
/* 234*/        String value = null;
/* 235*/        for(int i = 0; subChilds != null && i < subChilds.getLength(); i++)
{/* 236*/            if(subChilds.item(i).getNodeType() != 1)
/* 237*/                continue;/* 237*/            entryMap = new HashMap();
/* 238*/            Element entryElement = (Element)subChilds.item(i);
/* 239*/            if(entryElement != null)
    {/* 240*/                NodeList entryChildList = entryElement.getChildNodes();
/* 241*/                for(int j = 0; entryChildList != null && j < entryChildList.getLength(); j++)
        {

/* 244*/                    if(entryChildList.item(j).getNodeType() != 1)
/* 245*/                        continue;/* 245*/                    Node subChild = entryChildList.item(j);
/* 246*/                    if(subChild == null || subChild.getNodeType() != 1)
/* 247*/                        continue;/* 247*/                    Node textNode = getChildTextNode(subChild);
/* 248*/                    if(textNode == null)
/* 249*/                        continue;/* 249*/                    value = textNode.getNodeValue();

/* 251*/                    if(value != null)
/* 252*/                        entryMap.put(subChild.getNodeName(), value.trim());
        }
    }
/* 262*/            entries.add(entryMap);
}
/* 267*/        return entries;
}
public static Node getChildTextNode(Node ele)
{
/* 275*/        if(ele != null)
{/* 276*/            Document doc = ele.getOwnerDocument();
/* 277*/            DOMImplementation domImpl = doc.getImplementation();
/* 278*/            if(domImpl.hasFeature("Traversal", "2.0"))
    {/* 279*/                DocumentTraversal traversal = (DocumentTraversal)doc;
/* 280*/                int whatToShow = -1;
/* 281*/                TextFilter filter = new TextFilter();
/* 282*/                TreeWalker tree = traversal.createTreeWalker(ele, whatToShow, filter, false);
/* 283*/                return tree.firstChild();
    }
}
/* 286*/        return null;
}
public static NodeList getNodeListFromXpath(Node node, String partern)
throws TransformerException
{
/* 298*/        if(partern != null && partern.lastIndexOf("/") > 0)
{/* 299*/            String elementTag = partern.substring(partern.lastIndexOf("/") + 1);
/* 300*/            if(elementTag != null)
    {/* 301*/                if(node.getNodeType() == 1)
/* 302*/                    return ((Element)node).getElementsByTagName(elementTag);
/* 303*/                if(node.getNodeType() == 9)
/* 304*/                    return ((Document)node).getElementsByTagName(elementTag);
    }
}



/* 310*/        return null;
}
public static final synchronized boolean writeXmlFile(String fileName, Document document)
throws Exception
{


/* 386*/        DOMSource doms = new DOMSource(document);
/* 387*/        File file = new File(fileName);

/* 389*/        StreamResult result = new StreamResult(file);
/* 390*/        TransformerFactory tf = TransformerFactory.newInstance();
/* 391*/        Transformer transformer = tf.newTransformer();
/* 392*/        Properties properties = transformer.getOutputProperties();
/* 393*/        properties.setProperty("encoding", "UTF-8");
/* 394*/        properties.setProperty("method", "xml");
/* 395*/        properties.setProperty("indent", "yes");
/* 396*/        transformer.setOutputProperties(properties);

/* 398*/        transformer.transform(doms, result);
/* 399*/        return true;
}
public static final String domToString(Document document)
throws Exception
{
/* 411*/        DOMSource doms = new DOMSource(document);
/* 412*/        StringWriter strWriter = new StringWriter();

/* 414*/        StreamResult result = new StreamResult(strWriter);
/* 415*/        TransformerFactory tf = TransformerFactory.newInstance();
/* 416*/        Transformer transformer = tf.newTransformer();
/* 417*/        Properties properties = transformer.getOutputProperties();
/* 418*/        properties.setProperty("encoding", "UTF-8");
/* 419*/        properties.setProperty("method", "xml");
/* 420*/        properties.setProperty("indent", "yes");
/* 421*/        transformer.setOutputProperties(properties);

/* 423*/        transformer.transform(doms, result);
/* 424*/        return strWriter.toString();
}
public static Date convertStrToDate(String strDate, String parttern)
throws ParseException
{

/* 439*/        if(strDate != null)
{/* 440*/            SimpleDateFormat dateFormat = new SimpleDateFormat(parttern);
/* 441*/            return dateFormat.parse(strDate);
} else
{/* 443*/            return null;
}
}
public static Document buildDocument(InputStream in)
throws ParserConfigurationException, SAXException, IOException
{
/* <-MISALIGNED-> */ /* 446*/        DocumentBuilderFactory factory = getDocumentBuilderFactory();
/* <-MISALIGNED-> */ /* 447*/        DocumentBuilder builder = factory.newDocumentBuilder();
/* <-MISALIGNED-> */ /* 448*/        return builder.parse(in);
}
public static DocumentBuilderFactory getDocumentBuilderFactory()
{
/*  56*/        ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
    DocumentBuilderFactory documentbuilderfactory;
/*  58*/        String oldDocBuilderFactory = null;
/*  59*/        DocumentBuilderFactory factory = null;

/*  61*/        try
    {
/* <-MISALIGNED-> */ /*  61*/            Thread.currentThread().setContextClassLoader(org.apache.xerces.jaxp.DocumentBuilderFactoryImpl.class.getClassLoader());
/* <-MISALIGNED-> */ /*  62*/            oldDocBuilderFactory = System.getProperty("javax.xml.parsers.DocumentBuilderFactory");
/* <-MISALIGNED-> */ /*  63*/            if(!xmlParserImpl.equals(oldDocBuilderFactory))
/* <-MISALIGNED-> */ /*  64*/                System.setProperty("javax.xml.parsers.DocumentBuilderFactory", xmlParserImpl);/*  66*/            factory = DocumentBuilderFactory.newInstance();
    }
/*  68*/        catch(SecurityException se) { }

/*  70*/        if(oldDocBuilderFactory != null)
/*  71*/            System.setProperty("javax.xml.parsers.DocumentBuilderFactory", oldDocBuilderFactory);
/*  72*/        documentbuilderfactory = factory;


/*  75*/        try
    {
/* <-MISALIGNED-> */ /*  75*/            Thread.currentThread().setContextClassLoader(oldCL);
    }
/* <-MISALIGNED-> */ /*  76*/        catch(SecurityException se) { }
/* <-MISALIGNED-> */ /*  78*/        return documentbuilderfactory;
}
private Document doc;
private static String WRONGBILLTYPE = "wrong bill type";
public static final String TAGBILLHEAD = "billHead";
public static final String TAGBILLENTRYS = "billEntries";
public static final String TAGBILLENTRY = "entry";
public static final String ENTRYPATH = "";
private static String xmlParserImpl = "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl";
}
