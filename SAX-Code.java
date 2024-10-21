import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

public class Main {
    public static void main(String[] args){

        Map<String,String> data =new HashMap<String, String>();
        data.put("registryId","");
        data.put("LatitudeMeasure","");
        data.put("LongitudeMeasure","");

        List<Map<String,String>> result = new ArrayList<>();


        try{
            SAXParserFactory fact = SAXParserFactory.newInstance();
            SAXParser saxParser = fact.newSAXParser();

            DefaultHandler handle = new DefaultHandler(){
                boolean location = false;
                boolean lat=false;
                boolean lon=false;
                boolean loc=false;


                public void startDocument() throws SAXException {
                        System.out.println("\n***start of the document***\n");
                }


                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{

                    if (qName.equals("LocationAddressStateCode")) loc=true;
                    if (qName.equals("LatitudeMeasure")) lat=true;
                    if (qName.equals("LongitudeMeasure")) lon=true;
                    if(attributes != null && attributes.getLength() == 1) {
                        data.replace("registryId",attributes.getValue(0));
                    }
                }



                public void characters(char[] ch,int start,int length)throws SAXException{

                    String value = new String(ch, start, length).trim();
                    // ignore white space
                    if (value.length() == 0) return;

                    if(loc){
                        if (value.contains("WY")) location = true;
                    }
                    if(lat){
                        data.replace("LatitudeMeasure",value);
                    }
                    if(lon){
                        data.replace("LongitudeMeasure",value);
                    }
                }


                public void endElement(String uri,String localName,String qName){
                    if (qName.equals("LocationAddressStateCode")) loc = false;
                    if (qName.equals("LatitudeMeasure")) lat=false;;
                    if (qName.equals("LongitudeMeasure")) lon=false;;

                    if (qName.equals("FacilitySite") && location==true){
                        location=false;
                        result.add(data);
                        String regid="",longtit="",latit="";
                        for (int i=0;i<result.size();i++){
                            regid= result.get(i).get("registryId");
                            longtit=result.get(i).get("LongitudeMeasure");
                            latit=result.get(i).get("LatitudeMeasure");
                            }
                        System.out.println(regid + " : " + latit+ " , " + longtit);
                    }
                }


                public void endDocument() throws SAXException {
                        System.out.println("\n***End of the document***");
                }

            };
            saxParser.parse("D:\\EPAXML.xml",handle);
        }
        catch (Exception e){}

    }
}