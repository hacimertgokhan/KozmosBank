package net.mixium.kozmosbank.credit.menu;

import net.mixium.kozmosbank.files.defaultconfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Iterator;
import java.util.List;

public class loadCreditMenu {
    private static Inventory inv = null;
    public loadCreditMenu() {}
    @Deprecated
    public static void openInventory(HumanEntity ent) {
        inv = Bukkit.createInventory(null, 54, defaultconfig.getConfig().getString("kozmos-bank.credit.menu-title"));
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
            List<String> lores = defaultconfig.getConfig().getStringList("kozmos-bank.credit.credits." + credits + ".lore");

            for (int i = 0; i < lores.size(); ++i) {
                lores.set(i, ChatColor.translateAlternateColorCodes('&', lores.get(i)));
            }

            meta.setLore(lores);
            item.setItemMeta(meta);
            inv.setItem(slot, item);
        }

    }
}
