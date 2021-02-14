package fr.heavenmoon.factions.commands;

import fr.heavenmoon.factions.HeavenFactions;
import fr.moon.core.bukkit.format.Message;
import fr.moon.core.common.format.message.MessageType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScoreboardCommand implements CommandExecutor {

    private final HeavenFactions plugin;
    private String syntax = "/sb <on|off|refresh>";

    public ScoreboardCommand(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            new Message(MessageType.SYNTAXE, "%syntax%", syntax).send(sender);
            return false;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("off")) {
                plugin.getScoreboardManager().onLogout(player);
                player.sendMessage(ChatColor.RED + "Scoreboard désactivé");
                return false;
            } else if (args[0].equalsIgnoreCase("on")) {
                plugin.getScoreboardManager().onLogin(player);
                player.sendMessage(ChatColor.GREEN + "Scoreboard activé");
                return false;
            } else if (args[0].equalsIgnoreCase("refresh")) {
                plugin.getScoreboardManager().update(player);
            }
        }
        return false;
    }
}
