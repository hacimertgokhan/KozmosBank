package net.mixium.kozmosbank.methods;

import net.md_5.bungee.api.ChatMessageType;
import net.mixium.kozmosbank.files.lang;
import net.mixium.kozmosbank.files.storage;
import net.mixium.kozmosbank.mysql.manager.DataPlayer;
import net.mixium.kozmosbank.mysql.manager.DataSQL;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static net.mixium.kozmosbank.KozmosBank.isSQL;

public class $admin {

    public static void admin$setBalance(Player admin, Player player, int amount) {
        if(isSQL()) {
            DataSQL.setBalance(player.getUniqueId(), amount);
        } else {
            storage.getConfig().set("user-datas." + player.getUniqueId().toString() + ".bank-balance", amount);
            storage.saveConfig();
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.admin.set-balance").replace("%bank_balance%", String.valueOf($bank.getBankBalance(player))).replace("%admin%", admin.getName())));
        admin.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lKozmosBank: &f" + player.getName() + "`s new bank balance is: &b" + amount + "$"));

    }

    public static void admin$remBalance(Player admin, Player player, int amount) {
        if(isSQL()) {
            DataSQL.removeBalance(player, amount);
        } else {
            storage.getConfig().set("user-datas." + player.getUniqueId().toString() + ".bank-balance", ($bank.getBankBalance(player) - amount));
            storage.saveConfig();
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.admin.remove-balance").replace("%bank_balance%", String.valueOf($bank.getBankBalance(player))).replace("%admin%", admin.getName()).replace("%amount%", String.valueOf(amount))));
        admin.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lKozmosBank: &fRemoved " + amount +"$ " + player.getName() + "`s new bank balance is: &b" + $bank.getBankBalance(player) + "$"));

    }

    public static void admin$addBalance(Player admin, Player player, int amount) {
        if(isSQL()) {
            DataSQL.addBalance(player, amount);
        } else {
            storage.getConfig().set("user-datas." + player.getUniqueId().toString() + ".bank-balance", ($bank.getBankBalance(player) + amount));
            storage.saveConfig();
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.admin.add-balance").replace("%bank_balance%", String.valueOf($bank.getBankBalance(player))).replace("%admin%", admin.getName()).replace("%amount%", String.valueOf(amount))));
        admin.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lKozmosBank: &fAdded " + amount +"$ " + player.getName() + "`s new bank balance is: &b" + $bank.getBankBalance(player) + "$"));

    }

}
