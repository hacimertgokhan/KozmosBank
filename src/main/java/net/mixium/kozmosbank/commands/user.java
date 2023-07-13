package net.mixium.kozmosbank.commands;

import net.mixium.kozmosbank.KozmosBank;
import net.mixium.kozmosbank.files.lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.mixium.kozmosbank.methods.$bank.getBankBalance;
import static net.mixium.kozmosbank.methods.$bank.transaction;
import static net.mixium.kozmosbank.tools.integer.isInteger;

public class user implements CommandExecutor {

    public user(KozmosBank kozmosBank) {}

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player) {
            if(commandSender.hasPermission("kozmosbank.user")) {
                if(strings.length == 0 || strings[0].equalsIgnoreCase("help")) {
                    help(((Player) commandSender).getPlayer());
                } else if (strings.length == 1) {
                    String bankBalance = String.valueOf(getBankBalance(((Player) commandSender).getPlayer()));
                    if(strings[0].equalsIgnoreCase("balance")) {
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.balance").replace("%bank_balance%", bankBalance)));
                    } else {
                        help(((Player) commandSender).getPlayer());
                    }
                } else if (strings.length == 3) {
                    if(strings[0].equalsIgnoreCase("send")) {
                        Player player = Bukkit.getPlayer(strings[1]);
                        if (player.getName().equalsIgnoreCase(commandSender.getName())) {
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.unpermed-transaction")));
                        } else {
                            if (player == null) {
                                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.not-online")));
                            } else {
                                if (player.isOnline()) {
                                    if (isInteger(strings[2])) {
                                        if (getBankBalance(player) >= Integer.valueOf(strings[2])) {
                                            transaction("send", ((Player) commandSender).getPlayer(), player, Integer.valueOf(strings[2]));
                                            return true;
                                        } else {
                                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.not-enough-money")));
                                        }
                                    } else {
                                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.must-be-number")));
                                    }
                                } else {
                                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.not-online")));
                                }
                            }
                        }
                    }
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
        for(String message : lang.getConfig().getStringList("lang.help")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }
}
