package net.mixium.kozmosbank.files;

import net.mixium.kozmosbank.KozmosBank;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import static net.mixium.kozmosbank.files.defaultconfig.loggman;

public class lang {

    private static KozmosBank main;
    private static String path;
    private static String folderpath;
    private static File file;
    private static FileConfiguration config;

    public lang(KozmosBank main, String path) {
        lang.main = main;
        lang.path = path;
        file = null;
        config = null;
    }

    public static void saveConfig() {
        try {
            config.save(file);
        } catch (IOException var1) {
            var1.printStackTrace();
        }

    }

    public void saveDefaultConfig() {
        if (file == null) {
            file = new File(main.getDataFolder(), path);
        }

        if (!file.exists()) {
            main.saveResource(path, false);
        }

    }

    public String getPath() {
        return path;
    }

    public String getFolderpath() {
        return folderpath;
    }

    public static void reload(){
        if(config == null) {
            file = new File(main.getDataFolder(), path);
        }
        config = YamlConfiguration.loadConfiguration(file);

        try {
            Reader defaultConfigStream = new InputStreamReader(main.getResource(path), StandardCharsets.UTF_8);
            if (defaultConfigStream != null) {
                YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigStream);
                config.setDefaults(defaultConfig);
            }
        } catch (NullPointerException var2) {
            var2.printStackTrace();
        }

    }

    public static FileConfiguration getConfig() {
        if (config == null) {
            reload();
        }
        return config;
    }

    public static void create () {
        file = new File(main.getDataFolder(), path);
        if(!file.exists()) {
            loggman.send("File language.yml created...");
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }



}
