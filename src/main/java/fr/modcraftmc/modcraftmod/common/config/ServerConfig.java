package fr.modcraftmc.modcraftmod.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.modcraftmc.modcraftmod.ModcraftMod;

import java.io.*;

public class ServerConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    private static ServerConfig instance;

    private String botToken = "bot token here";
    private String webhookUrl = "webhook url here";
    private String channelId = "channel id here";

    private String tabHeader = "";
    private String tabFooter = "";

    public static ServerConfig loadConfig(File configFile) {

        configFile.getParentFile().mkdirs();
        if (!configFile.exists()) {

            try {
                configFile.createNewFile();
            } catch (IOException e) {
                ModcraftMod.LOGGER.info("Création du fichier de configuration " + configFile.getName());
            }

        } else ModcraftMod.LOGGER.info("Chargement du fichier de configuration " + configFile.getName());


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
            instance = GSON.fromJson(reader, ServerConfig.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (instance == null) {
            instance = new ServerConfig();
        }

        instance.save(configFile);

        return instance;
    }

    public void save(File file) {
        ModcraftMod.LOGGER.info("Sauvegarde du fichier du configuration " + file.getName());
        String jsonConfig = GSON.toJson(this);
        FileWriter writer;
        try {
            writer = new FileWriter(file);
            writer.write(jsonConfig);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            ModcraftMod.LOGGER.error("Erreur lors de la sauvegarde du fichier du configuration " + file.getName());
            ModcraftMod.LOGGER.error(e.getMessage());

        }
    }

    public String getBotToken() {
        return botToken;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getTabHeader() {
        return tabHeader;
    }

    public String getTabFooter() {
        return tabFooter;
    }
}
