package org.webbrowser.settings;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.webbrowser.accounts.Account;
import org.webbrowser.accounts.service.AccountService;
import org.webbrowser.browser.controller.TabController;
import org.webbrowser.browser.service.TabService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @since 2026
 * @author Axel
 */
public class ConfigManager {
    private static final ConfigManager instance = new ConfigManager();
    private static final String FILE_PATH = "src/main/java/org/webbrowser/Configurations/config.xml";

    private AccountService accountService;
    private Map<String, String> settingsConfig = new HashMap<>();
    private Account account = new Account();

    private ConfigManager() {}

    private void writeXMLFile(Document doc) {
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

    private void saveConfig() {
        Element root = new Element("configuration");
        Document doc = new Document(root);
        Element settings = new Element("settings");
        root.addContent(settings);
        for(String key: settingsConfig.keySet()) {
            settings.addContent(new Element("setting").setAttribute("key",key).setAttribute("value",settingsConfig.get(key)));
        }

        Element accountElement = new Element("account");

        if (account.isRegistered()) {
            accountElement.setAttribute("username", account.getUsername());
            accountElement.setAttribute("email", account.getEmail());
        } else {
            accountElement.setAttribute("username", "");
            accountElement.setAttribute("email", "");
        }

        root.addContent(accountElement);
        writeXMLFile(doc);
    }

    public void loadConfig() {
        try {
            File file = new File(FILE_PATH);
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(file);
            List<Element> settings = doc.getRootElement().getChild("settings").getChildren();
            settings.forEach(e -> {
                settingsConfig.put(e.getAttributeValue("key"),e.getAttributeValue("value"));
            });
            Element accountElement = doc.getRootElement().getChild("account");

            accountService = AccountService.getInstance();
            accountService.setCurrentAccount(accountElement.getAttributeValue("email"));
            account = accountService.getCurrentAccount();


            TabController.setDefaultBrowser(settingsConfig.get("default-browser"));


        }  catch (IOException e) {
            System.out.println("couldnt find config, generating default config");
            createDefaultConfig();
            loadConfig();
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        }
    }


    public void createDefaultConfig() {
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
    public void editSettingsConfig(HashMap<String,String> newSettingsConfig) {

        settingsConfig = newSettingsConfig;
        saveConfig();

    }
    //todo try to change the account global variable to non static
    public void editAccountConfig(Account newAccount) {
        account = newAccount;
        saveConfig();
    }

    public String getDefaultBrowser() {
        return settingsConfig.get("default-browser");
    }
    public static ConfigManager getInstance() {
        return instance;
    }
}
