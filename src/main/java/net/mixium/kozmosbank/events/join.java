package net.mixium.kozmosbank.events;

import net.mixium.kozmosbank.KozmosBank;
import net.mixium.kozmosbank.api.vault;
import net.mixium.kozmosbank.customholders.bank;
import net.mixium.kozmosbank.files.defaultconfig;
import net.mixium.kozmosbank.files.storage;
import net.mixium.kozmosbank.mysql.MySqlConnector;
import net.mixium.kozmosbank.mysql.manager.DataPlayer;
import net.mixium.kozmosbank.mysql.manager.DataSQL;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.PriorityQueue;
import java.util.UUID;

import static net.mixium.kozmosbank.KozmosBank.isSQL;
import static net.mixium.kozmosbank.api.vault.getEconomy;
import static net.mixium.kozmosbank.mysql.manager.DataSQL.playerExists;
import static net.mixium.kozmosbank.mysql.manager.DataSQL.setStartupBalance;

public class join implements Listener {
    public join(KozmosBank kozmosBank) {}

    @Deprecated
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerjoin(PlayerJoinEvent playerJoinEvent) {
        Player user = playerJoinEvent.getPlayer();
        UUID uuid = user.getUniqueId();
        String balance = getEconomy().format(getEconomy().getBalance(user));
        String bankBalance = String.valueOf(bank.getBankBalance(user));
        boolean joininformation = defaultconfig.getConfig().getBoolean("kozmos-bank.information-message.enable");

        if(isSQL()) {
            if(!playerExists(user.getUniqueId())) {
                DataSQL.createPlayer(uuid, user);
            } else {
                if (joininformation) {
                    for (String message : defaultconfig.getConfig().getStringList("kozmos-bank.information-message.message")) {
                        message = message.replace("%player_bank_balance%", bankBalance).replace("%player_balance%", balance).replace("%name%", user.getName());
                        user.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    }
                }
            }
        } else {
            if (storage.getConfig().getString("user-datas." + uuid.toString() + ".name") == null) {
                storage.getConfig().set("user-datas." + uuid.toString() + ".name", user.getName().toString());
                storage.getConfig().set("user-datas." + uuid.toString() + ".uuid", user.getUniqueId().toString());
                storage.getConfig().set("user-datas." + uuid.toString() + ".bank-balance", 0);
                storage.saveConfig();
            } else {
                if (joininformation) {
                    for (String message : defaultconfig.getConfig().getStringList("kozmos-bank.information-message.message")) {
                        message = message.replace("%player_bank_balance%", bankBalance).replace("%player_balance%", balance).replace("%name%", user.getName());
                        user.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    }
                }
            }
        }
    }

}
