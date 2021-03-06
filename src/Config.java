import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


    public class Config
    {
    	static String configPath = "/uitval/config.xml";
        static File config = new File(configPath);
 
        public static String Get(String setting)
        {
        	try{
        	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        	Document doc = dBuilder.parse(config);
        	
        	doc.getDocumentElement().normalize();
        	NodeList settings = doc.getFirstChild().getChildNodes();
        	
        	for (int temp = 0; temp < settings.getLength(); temp++) {
        		Node node = settings.item(temp);
        		if(node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName() == setting){
        			return node.getNodeValue();
        			
        		}
        	}
        	}
        	catch(Exception e){
        		//TODO : Error handling
        	}
        	return null;
            
        }
 
        public static void Set(String setting, String value)
        {
        	try{
        	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    		Document doc = docBuilder.parse(configPath);
    		
    		Node root = doc.getFirstChild();
    		NodeList settings = root.getChildNodes();
    		
    		boolean alreadyExists = false;
    		
    		for (int temp = 0; temp < settings.getLength(); temp++) {
        		Node node = settings.item(temp);
        		if(node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName() == setting){
        			node.setNodeValue(value);
        			alreadyExists = true;
        		}
    		}
    		
    		if(!alreadyExists){
    			Element el = doc.createElement(setting);
    			el.setNodeValue(value);
        		root.appendChild(el);
    			
    		}
    		
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer = transformerFactory.newTransformer();
    		DOMSource source = new DOMSource(doc);
    		StreamResult result = new StreamResult(new File(configPath));
    		transformer.transform(source, result);
        	}
        	catch(Exception e){
        		//TODO Error handling
        		
        	}
           
        }
 
        public static void Reset()
        {
        	try{
        	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    		
    		Document doc = docBuilder.newDocument();
    		Element rootElement = doc.createElement("config");
    		doc.appendChild(rootElement);
    		
    		Element klas = doc.createElement("klas");
    		rootElement.appendChild(klas);
    		
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer = transformerFactory.newTransformer();
    		DOMSource source = new DOMSource(doc);
    		StreamResult result = new StreamResult(config);
    		
    		transformer.transform(source, result);
        	}
        	catch(Exception e){
        		//TODO : error handling
        	}
        }
    }