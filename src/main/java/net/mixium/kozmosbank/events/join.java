package net.mixium.kozmosbank.events;

import net.mixium.kozmosbank.KozmosBank;
import net.mixium.kozmosbank.api.vault;
import net.mixium.kozmosbank.files.defaultconfig;
import net.mixium.kozmosbank.files.storage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.PriorityQueue;
import java.util.UUID;

public class join implements Listener {
    public join(KozmosBank kozmosBank) {}

    @Deprecated
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerjoin(PlayerJoinEvent playerJoinEvent) {
        Player user = playerJoinEvent.getPlayer();
        UUID uuid = user.getUniqueId();
        String balance = vault.getEconomy().format(vault.getEconomy().getBalance(user));
        boolean joininformation = defaultconfig.getConfig().getBoolean("kozmos-bank.information-message.enable");
        if(storage.getConfig().getString("user-datas." + uuid.toString() + ".name") == null) {
            storage.getConfig().set("user-datas." + uuid.toString() + ".name", user.getName().toString());
            storage.getConfig().set("user-datas." + uuid.toString() + ".bank-balance", 0);
            storage.saveConfig();
        } else {
            if (joininformation) {
                for (String message : defaultconfig.getConfig().getStringList("kozmos-bank.information-message.message")) {
                    message = message.replace("%player_balance%", balance).replace("%name%", user.getName());
                    user.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
    }

}
