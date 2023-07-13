package net.mixium.kozmosbank.credit.test;

import net.mixium.kozmosbank.credit.credit;
import net.mixium.kozmosbank.files.defaultconfig;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Iterator;

public class InteractEvent implements Listener {

    @Deprecated
    @EventHandler
    public void click(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', defaultconfig.getConfig().getString("kozmos-bank.credit.menu-title").replace("%player%", event.getWhoClicked().getName())))) {
            Iterator var = defaultconfig.getConfig().getConfigurationSection("kozmos-bank.credit.credits").getKeys(false).iterator();
            // event.setCancelled(true);
            while (var.hasNext()) {
                String credits = (String) var.next();
                String $displayitem = defaultconfig.getConfig().getString("kozmos-bank.credit.credits." + credits + ".material");
                String $paybacktime = defaultconfig.getConfig().getString("kozmos-bank.credit.credits." + credits + ".payback-time");
                int $refundtax = defaultconfig.getConfig().getInt("kozmos-bank.credit.credits." + credits + ".refund-tax");
                int $amount = defaultconfig.getConfig().getInt("kozmos-bank.credit.credits." + credits + ".credit-to-be-given");
                double calculatedRefundAmount = credit.calculatedRefundAmount($amount, $refundtax);
                String formatted_calculatedRefundAmount = formatted_calculatedRefundAmount = String.valueOf(calculatedRefundAmount);
                String $oneLineLore = defaultconfig.getConfig().getString("kozmos-bank.credit.credits." + credits + ".lore").replace("%credit_refund_amount%", formatted_calculatedRefundAmount);
                String $displayname = defaultconfig.getConfig().getString("kozmos-bank.credit.credits." + credits + ".display-name").replace("%credit_refund_amount%", formatted_calculatedRefundAmount);
                int slot = defaultconfig.getConfig().getInt("kozmos-bank.credit.credits." + credits +".slot");

            }


        }

    }

}
