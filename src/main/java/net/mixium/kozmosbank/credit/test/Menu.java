package net.mixium.kozmosbank.credit.test;

import net.mixium.kozmosbank.credit.credit;
import net.mixium.kozmosbank.files.defaultconfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Iterator;

public class Menu {
    private static Inventory inv = null;

    public Menu() {
    }

    @Deprecated
    public static void openInventory(HumanEntity ent) {
        inv = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', defaultconfig.getConfig().getString("kozmos-bank.credit.menu-title").replace("%player%", ent.getName())));
        loadItems();
        ent.openInventory(inv);
    }

    @Deprecated
    public static void loadItems() {
        Iterator var0 = defaultconfig.getConfig().getConfigurationSection("kozmos-bank.credit.credits").getKeys(false).iterator();

        while (var0.hasNext()) {
            String credits = (String) var0.next();
            String material = defaultconfig.getConfig().getString("kozmos-bank.credit.credits." + credits + ".material");
            String display = defaultconfig.getConfig().getString("kozmos-bank.credit.credits." + credits + ".display");
            int slot = defaultconfig.getConfig().getInt("kozmos-bank.credit.credits." + credits + ".slot");
            ItemStack item = new ItemStack(Material.getMaterial(material), 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', display));
            item.setItemMeta(meta);
            int $refundtax = defaultconfig.getConfig().getInt("kozmos-bank.credit.credits." + credits + ".refund-tax");
            int $amount = defaultconfig.getConfig().getInt("kozmos-bank.credit.credits." + credits + ".credit-to-be-given");
            double calculatedRefundAmount = credit.calculatedRefundAmount($amount, $refundtax);
            String formatted_calculatedRefundAmount = String.valueOf(calculatedRefundAmount);
            String $oneLineLore = defaultconfig.getConfig().getString("kozmos-bank.credit.credits." + credits + ".lore").replace("%credit_refund_amount%", formatted_calculatedRefundAmount);


            /*
                for (int i = 0; i < lores.size(); ++i) {
                    lores.set(i, ChatColor.translateAlternateColorCodes('&', lores.get(i)));
                }
             */

            //meta.;
            item.setItemMeta(meta);
            inv.setItem(slot, item);
        }

    }
}