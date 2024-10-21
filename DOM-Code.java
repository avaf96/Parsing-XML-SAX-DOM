import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){

        try{
            File xmlDoc =new File("D:\\Uni-Shz\\Data Base\\Home Work\\5\\dom.xml");
            DocumentBuilderFactory dbFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuild = dbFact.newDocumentBuilder();
            Document doc = dBuild.parse(xmlDoc);
            doc.getDocumentElement().normalize();

            deleteElement(doc);
            addElement(doc);
            writeXMLFile(doc);

        }
        catch (Exception e){}
    }



    private static void deleteElement(Document doc) {
        NodeList facilitySites = doc.getElementsByTagName("FacilitySite");

        List<Element> eleList =new ArrayList<>();
        for (int j=0;j< facilitySites.getLength(); j++){
            eleList.add((Element) facilitySites.item(j));
        }

        for (int i = 0; i < eleList.size(); i++) {
            Element ele = eleList.get(i);

            if (!ele.getElementsByTagName("LocationAddressStateCode").item(0).getTextContent().contains("WY")) {

                    ele.getParentNode().removeChild(ele);

            } else {
                Node node1 = ele.getElementsByTagName("GeneralProfileElectronicAddress").item(0);
                ele.removeChild(node1);
                Node node2 = ele.getElementsByTagName("LocationAddressStateCode").item(0);
                ele.removeChild(node2);
                continue;
            }
        }
    }



    private static void addElement(Document doc) {
        NodeList facilitySites = doc.getElementsByTagName("FacilitySite");

        for (int i = 0; i < facilitySites.getLength(); i++) {
            Element emp = (Element) facilitySites.item(i);
            if (emp.getElementsByTagName("Program").item(0) == null) {
                    continue;
            }else {
                    Element newEle=null;
                    for (int z = 0; z < emp.getElementsByTagName("Program").getLength(); z++) {
                        if (emp.getElementsByTagName("Program").item(z) != null){
                        Element emp2 = (Element) emp.getElementsByTagName("Program").item(z);
                        Element emp3 = (Element) emp2.getElementsByTagName("ProgramProfileElectronicAddress").item(0);
                        String value = emp3.getElementsByTagName("ElectronicAddressText").item(0).getTextContent();
                        if (z==0) {newEle = doc.createElement("ElectronicAddressText");}
                        Element newEle1 = doc.createElement("URL");
                        newEle.appendChild(newEle1);
                        newEle1.appendChild(doc.createTextNode(value));
                        emp.appendChild(newEle);
                        }
                    }
            }
        }

            //Remove programs
        for (int j=0;j< facilitySites.getLength(); j++){
            Element e1 =(Element) facilitySites.item(j);
            if (e1.getElementsByTagName("Program").item(0) == null) {
                    continue;
            }else {
                    List<Element> programs = new ArrayList<>();
                    for (int k = 0; k < e1.getElementsByTagName("Program").getLength();k++) {
                        programs.add((Element)e1.getElementsByTagName("Program").item(k));
                    }
                    for (int v=0; v <programs.size();v++){
                        Element ele10 = programs.get(v);
                        ele10.getParentNode().removeChild(ele10);
                    }
            }
        }
    }


    private static void writeXMLFile(Document doc) throws TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException {
        doc.getDocumentElement().normalize();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("D:\\dom.xml"));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
        System.out.println("XML file updated successfully");
    }


}

