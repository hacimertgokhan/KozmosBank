package net.mixium.kozmosbank.tools.storage;

import net.md_5.bungee.api.ChatMessageType;
import net.mixium.kozmosbank.files.lang;
import net.mixium.kozmosbank.files.storage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class transactions {

    public static void fileStorage$removeBalance(Player player, int amount) {
        int bankBalance = storage.getConfig().getInt("user-datas." + player.getUniqueId().toString() + ".bank-balance");
        storage.getConfig().set("user-datas." + player.getUniqueId().toString() + ".bank-balance", (bankBalance - amount));
        storage.saveConfig();
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.new-balance").replace("%balance%", String.valueOf(bankBalance))));
    }

    public static void fileStorage$addBalance(Player player, int amount) {
        int bankBalance = storage.getConfig().getInt("user-datas." + player.getUniqueId().toString() + ".bank-balance");
        storage.getConfig().set("user-datas." + player.getUniqueId().toString() + ".bank-balance", (bankBalance + amount));
        storage.saveConfig();
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.new-balance").replace("%balance%", String.valueOf(bankBalance))));
    }

    public static void fileStorage$resetBalance(Player player) {
        int bankBalance = storage.getConfig().getInt("user-datas." + player.getUniqueId().toString() + ".bank-balance");
        storage.getConfig().set("user-datas." + player.getUniqueId().toString() + ".bank-balance", 0);
        storage.saveConfig();
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.account-reseted").replace("%balance%", String.valueOf(bankBalance))));
    }


}
