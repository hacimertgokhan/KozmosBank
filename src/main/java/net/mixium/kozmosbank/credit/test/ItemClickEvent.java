package net.mixium.kozmosbank.credit.test;

import net.mixium.kozmosbank.files.defaultconfig;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class ItemClickEvent implements Listener {

    @Deprecated
    @EventHandler
    public void drag(InventoryDragEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', defaultconfig.getConfig().getString("kozmos-bank.credit.menu-title").replace("%player%", event.getWhoClicked().getName())))) {
            event.setCancelled(true);
        }

    }

}
