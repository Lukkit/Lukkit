package net.lukkit.lukkit.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


public class MessageSender {
    private static final String PREFIX = "&l&a[Lukkit] &r";

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + message.replace("\n", "\n\t")));
    }
}
