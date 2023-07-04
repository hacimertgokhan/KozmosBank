package net.mixium.kozmosbank;

import net.mixium.kozmosbank.events.join;
import net.mixium.kozmosbank.files.defaultconfig;
import net.mixium.kozmosbank.files.lang;
import net.mixium.kozmosbank.files.storage;
import net.mixium.kozmosbank.mysql.MySqlConnector;
import net.mixium.kozmosbank.tools.logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static net.mixium.kozmosbank.api.vault.setupEconomy;

public final class KozmosBank extends JavaPlugin {
    private static KozmosBank instance;
    public static synchronized KozmosBank get(){return instance;}
    public static synchronized void set(KozmosBank mixium){instance = mixium;}
    private final defaultconfig defaultconfig = new defaultconfig(this, "default-config.yml");
    private final lang language = new lang(this, "language.yml");
    private final storage kbstorage = new storage(this, "datas.yml", "storage");
    private final net.mixium.kozmosbank.tools.logger loggman = new logger(this);
    public PluginManager pluginManager = Bukkit.getPluginManager();
    public MySqlConnector mySqlConnector = new MySqlConnector(this);

    private void load () {
        defaultconfig.create();
        language.create();
        kbstorage.create();
        if (!setupEconomy() ) {
            loggman.send("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getPluginManager().registerEvents(new join(this), this);
    }

    @Override
    public void onEnable() {
        set(this);
        load();

    }
}
