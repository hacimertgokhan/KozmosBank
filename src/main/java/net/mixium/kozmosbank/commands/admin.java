package net.mixium.kozmosbank.commands;

import net.mixium.kozmosbank.KozmosBank;
import net.mixium.kozmosbank.files.defaultconfig;
import net.mixium.kozmosbank.files.lang;
import net.mixium.kozmosbank.files.storage;
import net.mixium.kozmosbank.mysql.manager.DataSQL;
import net.mixium.kozmosbank.tools.reload;
import net.mixium.kozmosbank.tools.storage.transactions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.mixium.kozmosbank.KozmosBank.isSQL;
import static net.mixium.kozmosbank.methods.$admin.*;
import static net.mixium.kozmosbank.tools.integer.isInteger;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class admin implements CommandExecutor {
    public admin(KozmosBank kozmosBank) {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player) {
            if(commandSender.hasPermission("kozmosbank.admin")) {
                if(strings.length == 0 || strings[0].equalsIgnoreCase("help")) {
                    help(((Player) commandSender).getPlayer());
                } else if (strings.length == 2) {
                    if(strings[0].equalsIgnoreCase("reload")) {
                        if(strings[1].equalsIgnoreCase("all")) {
                            reload.reloadAll();
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lKozmosBank: &fAll files reloaded ! plugin version: &b" + KozmosBank.get().getDescription().getVersion()));
                        } else if(strings[1].equalsIgnoreCase("lang")) {
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lKozmosBank: &flanguage.yml reloaded ! plugin version: &b" + KozmosBank.get().getDescription().getVersion()));
                            lang.reload();
                            lang.saveConfig();
                        } else if(strings[1].equalsIgnoreCase("config")) {
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lKozmosBank: &fdefault-config.yml reloaded ! plugin version: &b" + KozmosBank.get().getDescription().getVersion()));
                            defaultconfig.reload();
                            defaultconfig.saveConfig();
                        } else if(strings[1].equalsIgnoreCase("datas")) {
                            if (isSQL()) {
                                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lKozmosBank: &fYour storage type is mysql, you cant do that."));
                            } else {
                                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lKozmosBank: &fdatas.yml reloaded ! plugin version: &b" + KozmosBank.get().getDescription().getVersion()));
                                storage.reloadConfig();
                                storage.saveConfig();
                            }
                        } else {
                            help(((Player) commandSender).getPlayer());
                        }
                    } else if(strings[0].equalsIgnoreCase("reset")) {
                        Player player = Bukkit.getPlayer(strings[1]);
                        if (player.isOnline() && (!(player == null))) {
                            if(isSQL()) {
                                DataSQL.resetBalance(player.getUniqueId());
                            } else {
                                transactions.fileStorage$resetBalance(player);
                            }
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lKozmosBank: &f" + player.getName() + "`s bank account reseted."));
                        } else {
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.not-online")));
                        }
                    }
                } else if (strings.length == 3) {
                    if(strings[0].equalsIgnoreCase("set")) {
                        Player player = Bukkit.getPlayer(strings[1]);
                        if (player.isOnline() && (!(player == null))) {
                            if (isInteger(strings[2])) {
                                admin$setBalance(((Player) commandSender).getPlayer(), player, Integer.parseInt(strings[2]));
                            } else {
                                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.must-be-number")));
                            }
                        } else {
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.not-online")));
                        }
                    } else if(strings[0].equalsIgnoreCase("add")) {
                        Player player = Bukkit.getPlayer(strings[1]);
                        if (player.isOnline() && (!(player == null))) {
                            if (isInteger(strings[2])) {
                                admin$addBalance(((Player) commandSender).getPlayer(), player, Integer.parseInt(strings[2]));
                            } else {
                                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.must-be-number")));
                            }
                        } else {
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.not-online")));
                        }
                    } else if(strings[0].equalsIgnoreCase("remove")) {
                        Player player = Bukkit.getPlayer(strings[1]);
                        if (player.isOnline() && (!(player == null))) {
                            if (isInteger(strings[2])) {
                                admin$remBalance(((Player) commandSender).getPlayer(), player, Integer.parseInt(strings[2]));
                            } else {
                                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.must-be-number")));
                            }
                        } else {
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.not-online")));
                        }
                    } else {
                        help(((Player) commandSender).getPlayer());
                    }
                } else {
                    help(((Player) commandSender).getPlayer());
                }
            } else {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.no-permission")));
            }
        } else {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.in-game")));
        }

        return false;
    }

    public void help(Player player){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lKozmosBank"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "    &f/kozmosbank add <player> <amount>"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "    &f/kozmosbank remove <player> <amount>"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "    &f/kozmosbank set <player> <amount>"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "    &f/kozmosbank reset <player>"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "    &f/kozmosbank reload <all/lang/config/datas>"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
    }

}
