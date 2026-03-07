package org.webbrowser.settings;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.webbrowser.browser.BrowserApplication;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
/**
 * @since 2026
 * @author Axel
 */
public class ConfigManager {
    private static final String FILE_PATH = "src/main/java/org/webbrowser/Configurations/config.xml";

    private static HashMap<String, String> config = new HashMap<>();


    //todo manage xml config documents

    public static HashMap<String, String> getConfig() {
        loadConfig();
        return config;
    }
    private static void loadConfig() {
        try {
            File file = new File(FILE_PATH);
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(file);
            List<Element> settings = doc.getRootElement().getChild("settings").getChildren();
            settings.forEach(e -> {
                config.put(e.getAttributeValue("key"),e.getAttributeValue("value"));
            });
            Element account = doc.getRootElement().getChild("account");

            for(String s: config.keySet()) {
                System.out.println(s + " : " +config.get(s));
            }

        }  catch (IOException e) {
            System.out.println("couldnt find config, generating default config");
            createDefaultConfig();
            loadConfig();
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createDefaultConfig() {
        Element root = new Element("configuration");
        Document doc = new Document(root);
        Element settings = new Element("settings");
        root.addContent(settings);
        settings.addContent(new Element("setting")
                .setAttribute("key","default-browser")
                .setAttribute("value","https://google.com"));
        //contains placeholder data for now because of testing purposes
        root.addContent(new Element("account")
                .setAttribute("username","USERNAME")
                .setAttribute("email","axel@gmail.com")
                .setAttribute("password","password123"));
        writeXMLFile(doc);

    }
    public static void editConfig(HashMap<String,String> newConfig) {
        config = newConfig;
        saveConfig();

    }
    private static void saveConfig() {
        Element root = new Element("configuration");
        Document doc = new Document(root);
        Element settings = new Element("settings");
        root.addContent(settings);
        for(String key: config.keySet()) {
            settings.addContent(new Element("setting").setAttribute("key",key).setAttribute("value",config.get(key)));
        }
        writeXMLFile(doc);
    }

    private static void writeXMLFile(Document doc) {
        XMLOutputter pretty = new XMLOutputter(Format.getPrettyFormat());
        String out = pretty.outputString(doc);
        try {
            File output = new File(FILE_PATH);
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
