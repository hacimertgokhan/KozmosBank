package net.mixium.kozmosbank.commands;

import net.mixium.kozmosbank.KozmosBank;
import net.mixium.kozmosbank.customholders.bank;
import net.mixium.kozmosbank.files.lang;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class user implements CommandExecutor {

    public user(KozmosBank kozmosBank) {}

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player) {
            if(commandSender.hasPermission("kozmosbank.user")) {
                if(strings.length == 0 || strings[0].equalsIgnoreCase("help")) {
                    help(((Player) commandSender).getPlayer());
                } else if (strings.length == 1) {
                    String bankBalance = String.valueOf(bank.getBankBalance(((Player) commandSender).getPlayer()));
                    if(strings[0].equalsIgnoreCase("balance")) {
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("lang.balance").replace("%bank_balance%", bankBalance)));
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

        return true;
    }

    public void help(Player player){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lKOZMOSBANK"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "    &f/bank balance"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "    &f/bank send <player> <amount>"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "    &f/bank request <player> <amount>"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "    &f/bank accept"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "    &f/bank deny"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
    }
}
