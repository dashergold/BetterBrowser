package org.webbrowser.settings;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class ConfigManager {
    private static HashMap<String, String> config = new HashMap<>();


    //todo manage xml config documents

    public static HashMap<String, String> getConfig() {
        loadConfig();
        return null;
    }
    private static void loadConfig() {
        try {
            File file = new File("src/main/java/org/webbrowser/Configurations/config.xml");
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(file);
            List<Element> settings = doc.getRootElement().getChild("settings").getChildren();
            settings.forEach(e -> {
                config.put(e.getAttributeValue("key"),e.getAttributeValue("value"));
            });
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
    private static void createDefaultConfig() {
        Element root = new Element("configuration");
        Document doc = new Document(root);
        Element settings = new Element("settings");
        root.addContent(settings);
        settings.addContent(new Element("setting").setAttribute("key","default-browser").setAttribute("value","https://google.com"));
        XMLOutputter pretty = new XMLOutputter(Format.getPrettyFormat());
        String out = pretty.outputString(doc);

        try {
            File output = new File("src/main/java/org/webbrowser/Configurations/config.xml");
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
