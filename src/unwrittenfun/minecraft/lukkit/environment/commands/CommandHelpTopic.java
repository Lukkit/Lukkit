package unwrittenfun.minecraft.lukkit.environment.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpTopic;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class CommandHelpTopic extends HelpTopic {
    public String usage;

    public CommandHelpTopic(String _name, String _description, String _usage) {
        this.name = "/" + _name;
        this.shortText = _description;
        this.usage = _usage;
    }

    @Override
    public boolean canSee(CommandSender player) {
        return true;
    }

    @Override
    public String getFullText(CommandSender forWho) {
        return  ChatColor.GOLD + "Description: " + ChatColor.RESET + shortText + "\n" +
                ChatColor.GOLD + "Usage: " + ChatColor.RESET + usage;
    }
}
