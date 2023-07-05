package net.mixium.kozmosbank.customholders;

import net.md_5.bungee.api.ChatMessageType;
import net.mixium.kozmosbank.files.lang;
import net.mixium.kozmosbank.files.storage;
import net.mixium.kozmosbank.mysql.manager.DataPlayer;
import net.mixium.kozmosbank.mysql.manager.DataSQL;
import net.mixium.kozmosbank.tools.storage.transactions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static net.mixium.kozmosbank.KozmosBank.isSQL;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class bank {



    public static float getBankBalance(Player player) {
        if(isSQL()) {
            return DataPlayer.getPlayerBalance(player);
        } else {
            return storage.getConfig().getInt("user-datas." + player.getUniqueId().toString() + ".bank-balance");
        }
    }


    public static void transaction(String type, Player p1, Player p2, int amount) {
        if(type.equalsIgnoreCase("send")) {
            if(isSQL()) {
                DataSQL.removeBalance(p1, amount);
                DataSQL.addBalance(p2, amount);
                p1.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.transaction.send.sended").replace("%player%", p2.getName()).replace("%amount%", String.valueOf(amount))));
                p2.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.transaction.send.tooked").replace("%player%", p1.getName()).replace("%amount%", String.valueOf(amount))));
            } else {
                transactions.fileStorage$removeBalance(p1, amount);
                transactions.fileStorage$addBalance(p2, amount);
                p1.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.transaction.send.sended").replace("%player%", p2.getName()).replace("%amount%", String.valueOf(amount))));
                p2.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.transaction.send.tooked").replace("%player%", p1.getName()).replace("%amount%", String.valueOf(amount))));
            }
        }
    }

}
