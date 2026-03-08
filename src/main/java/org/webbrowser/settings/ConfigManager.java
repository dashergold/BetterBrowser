package org.webbrowser.settings;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.webbrowser.accounts.AccountController;
import org.webbrowser.browser.BrowserApplication;
import org.webbrowser.browser.TabController;

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
    private static HashMap<String, String> settingsConfig = new HashMap<>();


    //todo manage xml config documents


    public static void loadConfig() {
        try {
            File file = new File(FILE_PATH);
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(file);
            List<Element> settings = doc.getRootElement().getChild("settings").getChildren();
            settings.forEach(e -> {
                settingsConfig.put(e.getAttributeValue("key"),e.getAttributeValue("value"));
            });
            Element account = doc.getRootElement().getChild("account");
            AccountController.handleAccountFromConfig(account.getAttributeValue("email"));

            TabController.setDefaultBrowser(settingsConfig.get("default-browser"));

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
                .setAttribute("email","usermail@host.com"));
        writeXMLFile(doc);

    }
    public static void editSettingsConfig(HashMap<String,String> newSettingsConfig) {
        //todo
        settingsConfig = newSettingsConfig;
        saveConfig();

    }

    private static void saveConfig() {
        Element root = new Element("configuration");
        Document doc = new Document(root);
        Element settings = new Element("settings");
        root.addContent(settings);
        for(String key: settingsConfig.keySet()) {
            settings.addContent(new Element("setting").setAttribute("key",key).setAttribute("value",settingsConfig.get(key)));
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

    public static String getDefaultBrowser() {
        return settingsConfig.get("default-browser");
    }
}
