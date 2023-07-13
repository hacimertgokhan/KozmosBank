package net.mixium.kozmosbank.credit.command;

import net.mixium.kozmosbank.KozmosBank;
import net.mixium.kozmosbank.credit.menu.loadCreditMenu;
import net.mixium.kozmosbank.files.defaultconfig;
import net.mixium.kozmosbank.files.lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class CreditRequest implements CommandExecutor {
    public CreditRequest(KozmosBank kozmosBank) {}

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player) {
            if(commandSender.hasPermission("kozmosbank.creditrequest")) {
                if(strings.length == 0 || strings[0].equalsIgnoreCase("help")) {
                    help((Player) commandSender);
                } else if (strings.length == 1) {
                    if(strings[0].equalsIgnoreCase("list")) {
                        loadCreditMenu.openInventory((HumanEntity) commandSender);
                        if(defaultconfig.getConfig().getBoolean("kozmos-bank.credit.send-menu-message")) {
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.menu-opened")));
                        }
                    } else if (strings[0].equalsIgnoreCase("online-credit-officers")) {
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', defaultconfig.getConfig().getString("kozmos-bank.credit.credit-officers.title")));
                        for(String creditOfficers : defaultconfig.getConfig().getStringList("kozmos-bank.credit.credit-officers.list")) {
                            commandSender.sendMessage(creditOfficers);
                        }
                    } else {
                        help((Player) commandSender);
                    }
                } else if (strings.length == 2) {
                    if(strings[0].equalsIgnoreCase("credit-request")) {
                        Iterator var0 = defaultconfig.getConfig().getConfigurationSection("kozmos-bank.credit.credits").getKeys(false).iterator();

                        while (var0.hasNext()) {
                            String credits = (String) var0.next();
                            String name = defaultconfig.getConfig().getString("kozmos-bank.credit.credits." + credits);
                            if (strings[1].equalsIgnoreCase(name.toString())) {
                                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "Potato"));
                            } else {
                                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.cannot-found")));
                            }
                        }
                    } else {
                        help((Player) commandSender);
                    }
                } else {
                    help((Player) commandSender);
                }
            } else {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.no-permission")));
            }
        } else {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.in-game")));
        }

        return false;
    }

    public void help(Player player) {
        for (String message : lang.getConfig().getStringList("lang.credit-request-help")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }
}
