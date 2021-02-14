package fr.heavenmoon.factions.commands;

import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.quests.gui.MainQuestsGUI;
import fr.moon.core.bukkit.format.Message;
import fr.moon.core.common.format.message.MessageType;
import fr.heavenmoon.factions.storage.players.FactionPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuestsCommand implements CommandExecutor {

    private final HeavenFactions plugin;
    private final String syntax = "/quetes";

    public QuestsCommand(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            new Message(MessageType.CONSOLE).send(sender);
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            FactionPlayer factionPlayer = plugin.getfPlayersManager().get(player);
            plugin.getApi().getGuiManager().openGui(player, new MainQuestsGUI(plugin, factionPlayer));
        } else {
            new Message(MessageType.SYNTAXE, "%syntax%", syntax).send(sender);
        }

        return false;
    }
}
