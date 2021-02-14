package fr.heavenmoon.factions.commands;

import fr.heavenmoon.factions.HeavenFactions;
import fr.moon.core.bukkit.format.Message;
import fr.moon.core.common.format.message.MessageType;
import fr.moon.core.common.format.message.PrefixType;
import fr.heavenmoon.factions.storage.factions.CustomFaction;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FTopCommand implements CommandExecutor {

    private final HeavenFactions plugin;

    public FTopCommand(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            new Message(MessageType.CONSOLE).send(sender);
            return false;
        }
        Player player = (Player) sender;
        plugin.getfPlayersManager().get(player);
        if (plugin.getFactionsManager().getAllFactions().isEmpty()) {
            new Message(PrefixType.SERVER, "Il n'y a pas de fations.").send(player);
            return false;
        }
        List<CustomFaction> allFactions = new ArrayList<>(plugin.getFactionsManager().getAllFactions());
        List<CustomFaction> topTen = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            topTen.add(allFactions.get(i));
        }

        new Message(PrefixType.SERVER, "Top factions: ").send(player);
        topTen.forEach(customFaction -> new Message(ChatColor.GRAY + "  - " + ChatColor.BLUE + customFaction.getName() + ChatColor.GRAY + ": " + ChatColor.LIGHT_PURPLE + customFaction.getDusts() + ChatColor.DARK_PURPLE + " Dust(s)").send(player));
        return false;
    }
}
