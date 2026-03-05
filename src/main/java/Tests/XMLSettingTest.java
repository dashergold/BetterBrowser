package Tests;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class XMLSettingTest {
    private static String xmlContent;
    static void main(String[] args) {
        modifyXMLSetting1("modified value");
        readXML();
        modifyXMLSetting2("Modiefied Value 2");
        readXML();

    }
    //these two could be made using a method that takes in the key value as a parameter as well as the modification
    private static void modifyXMLSetting1(String modification) {

        try {
            File file = new File("src/main/java/Tests/Files/test.xml");
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(file);
            Element root = doc.getRootElement();
            Element settings = root.getChild("settings");
            List<Element> addElements = settings.getChildren();

            for (Element add: addElements) {
                if(add.getAttributeValue("key").equals("setting1")) {
                    add.setAttribute("value",modification);
                }
            }
            XMLOutputter pretty = new XMLOutputter(Format.getPrettyFormat());
            String out = pretty.outputString(doc);

            try {
                File output = new File("src/main/java/Tests/Files/test.xml");
                FileOutputStream fos = new FileOutputStream(output);
                OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                writer.write(out);
                writer.close();
                fos.close();

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        }
    }
    private static void modifyXMLSetting2(String modification) {
        try {
            File file = new File("src/main/java/Tests/Files/test.xml");
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(file);
            Element root = doc.getRootElement();
            Element settings = root.getChild("settings");
            List<Element> addElements = settings.getChildren();

            for (Element add: addElements) {
                if(add.getAttributeValue("key").equals("setting2")) {
                    add.setAttribute("value",modification);
                }
            }
            XMLOutputter pretty = new XMLOutputter(Format.getPrettyFormat());
            String out = pretty.outputString(doc);

            try {
                File output = new File("src/main/java/Tests/Files/test.xml");
                FileOutputStream fos = new FileOutputStream(output);
                OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                writer.write(out);
                writer.close();
                fos.close();

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        }
    }

    private static void readXML() {
        try {
            File file = new File("src/main/java/Tests/Files/test.xml");
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(file);

            Element rootElement = doc.getRootElement();
            System.out.println("Root Element: " + rootElement.getName());


            Element settings = rootElement.getChild("settings");


            List<Element> addElements = settings.getChildren("add");

            for (Element add : addElements) {
                String key = add.getAttributeValue("key");
                String value = add.getAttributeValue("value");

                System.out.println("Key: " + key);
                System.out.println("Value: " + value);
                System.out.println("----------------");
            }

        } catch (IOException e) {
            System.out.println("couldnt find file. creating new");
            createDefaultConfig();
            readXML();
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        }

    }
    private static void createDefaultConfig() {
        System.out.println("attempting to create a new default xml");
        Element root = new Element("configuration");
        Document doc = new Document(root);
        Element settings = new Element("settings");
        root.addContent(settings);
        settings.addContent(new Element("add").setAttribute("key","setting1").setAttribute("value","value1"));
        settings.addContent(new Element("add").setAttribute("key","setting2").setAttribute("value","value2"));

        XMLOutputter pretty = new XMLOutputter(Format.getPrettyFormat());
        String out = pretty.outputString(doc);

        try {
            File output = new File("src/main/java/Tests/Files/test.xml");
            FileOutputStream fos = new FileOutputStream(output);
            OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer.write(out);
            writer.close();
            fos.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
