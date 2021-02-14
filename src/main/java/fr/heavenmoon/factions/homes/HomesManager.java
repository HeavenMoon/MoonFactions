package fr.heavenmoon.factions.homes;

import fr.moon.core.bukkit.MoonBukkitCore;
import fr.moon.core.bukkit.format.Message;
import fr.moon.core.bukkit.utils.ActionbarBuilder;
import fr.moon.core.common.data.player.CustomPlayer;
import fr.moon.core.common.data.player.rank.RankList;
import fr.moon.core.common.format.message.PrefixType;
import fr.moon.core.common.utils.wrappers.LambdaWrapper;
import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.storage.players.FactionPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomesManager {

    private final HeavenFactions plugin;
    private final RankList rank = RankList.ADMINISTRATEUR;
    private final Map<Player, Integer> tpCooldowns;

    public HomesManager(HeavenFactions plugin) {
        this.plugin = plugin;
        tpCooldowns = new HashMap<>();
    }

    public List<CustomHome> getHomesOfPlayer(FactionPlayer player) {
        return player.getHomes();
    }

    public void getHomes(Player sender, FactionPlayer target) {
        if (target.getHomes().isEmpty()) {
            if (sender.getName().equals(target.getName())) {
                new Message(PrefixType.SERVER, "Vous n'avez pas de home.").send(sender);
            } else {
                new Message(PrefixType.MODO, ChatColor.GRAY + target.getName() + ChatColor.LIGHT_PURPLE + " n'as pas de home.").send(sender);
            }
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (CustomHome home : target.getHomes()) {
            sb.append(ChatColor.AQUA + home.getName()).append(ChatColor.GRAY + ", ");
        }
        String message = sb.toString();
        if (sender.getName().equals(target.getName())) {
            new Message(PrefixType.SERVER, "Vos homes: " + ChatColor.AQUA + message.substring(0, sb.toString().length() - 2)).send(sender);
        } else {
            new Message(PrefixType.MODO, "Home de " + ChatColor.GRAY + target.getName() + ChatColor.LIGHT_PURPLE + ": " + message.substring(0, sb.toString().length() - 2)).send(sender);
        }
    }

    public void addHomes(Player sender, FactionPlayer target, String homeName, Location location) {
        if (target.getHomes().size() >= target.getMaxHomes() && MoonBukkitCore.get().getCommons().getPlayerManager().get(sender.getName(), sender.getUniqueId().toString()).getRank().getRank() != rank) {
            new Message(PrefixType.ERROR, "Vous ne pouvez pas avoir plus de home.").send(sender);
            return;
        }
        if (exist(target, homeName)) {
            if (sender.getName().equals(target.getName())) {
                new Message(PrefixType.SERVER, "Vous avez déjà un home avec ce nom.").send(sender);
            } else {
                new Message(PrefixType.MODO, ChatColor.GRAY + sender.getName() + ChatColor.LIGHT_PURPLE + " a déjà un home avec ce nom.").send(sender);
            }
            return;
        }
        target.getHomes().add(new CustomHome(homeName, location));
        plugin.getfPlayersManager().commit(target);
        if (sender.getName().equals(target.getName())) {
            new Message(PrefixType.SERVER, "Home " + ChatColor.AQUA + homeName + ChatColor.GRAY + " crée.").send(sender);
        } else {
            new Message(PrefixType.MODO, "Home " + ChatColor.GREEN + homeName + ChatColor.LIGHT_PURPLE + " crée pour " + ChatColor.GRAY + target.getName()).send(sender);
        }
    }

    public void removeHomes(Player sender, FactionPlayer target, String homeName) {
        if (!exist(target, homeName)) {
            if (sender.getName().equals(target.getName())) {
                new Message(PrefixType.SERVER, "Vous n'avez pas de home avec ce nom.").send(sender);
            } else {
                new Message(PrefixType.MODO, ChatColor.GRAY + target.getName() + ChatColor.LIGHT_PURPLE + " n'a pas de home avec ce nom.").send(sender);
            }
            return;
        }
        target.getHomes().remove(get(target, homeName));
        plugin.getfPlayersManager().commit(target);
        if (sender.getName().equals(target.getName())) {
            new Message(PrefixType.SERVER, "Home " + ChatColor.AQUA + homeName + ChatColor.GRAY + " supprimé.").send(sender);
        } else {
            new Message(PrefixType.MODO, "Home " + ChatColor.GREEN + homeName + ChatColor.LIGHT_PURPLE + " supprimé pour " + ChatColor.GRAY + target.getName()).send(sender);
        }

    }

    public void teleportToHome(Player sender, FactionPlayer target, String homeName) {
        if (exist(target, homeName)) {
            startCooldownTp(sender, sender.getLocation(), 5, get(target, homeName).getLocation());
            return;
        }

        if (sender.getName().equals(target.getName())) {
            new Message(PrefixType.SERVER, "Vous n'avez pas de home intitulé " + ChatColor.AQUA + homeName + ChatColor.GRAY + ".").send(sender);
        } else {
            new Message(PrefixType.MODO, ChatColor.GRAY + target.getName() + ChatColor.LIGHT_PURPLE + " n'a pas de home intitulé " + ChatColor.GREEN + homeName + ChatColor.LIGHT_PURPLE + ".").send(sender);
        }
    }

    public boolean exist(FactionPlayer target, String homeName) {
        for (CustomHome home : target.getHomes()) {
            if (home.getName().equalsIgnoreCase(homeName)) return true;
        }
        return false;
    }

    public CustomHome get(FactionPlayer target, String homeName) {
        for (CustomHome home : target.getHomes()) {
            if (home.getName().equalsIgnoreCase(homeName)) return home;
        }
        return null;
    }

    public void startCooldownTp(Player player, Location loc, int seconds, Location dest) {
        LambdaWrapper<Integer> cooldownTime = new LambdaWrapper<>(seconds);
        CustomPlayer customPlayer = plugin.getApi().getCommons().getPlayerManager().get(player.getName(), player.getUniqueId().toString());

        customPlayer.setCanTp(false);
        if (customPlayer.hasOnlyPermission(RankList.MODERATEUR)) {
            player.teleport(dest);
            return;
        }
        int scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            new ActionbarBuilder( "Téléportation dans " + ChatColor.LIGHT_PURPLE + cooldownTime.getData() + ChatColor.GRAY + " seconde(s)...", 2).send(player);
            cooldownTime.setData(cooldownTime.getData() - 1);
            if (cooldownTime.getData() == 0) {
                customPlayer.setCanTp(true);
                Bukkit.getScheduler().cancelTask(tpCooldowns.get(player));
                player.teleport(dest);
                return;
            }
            if (player.getLocation().getX() != loc.getX() || player.getLocation().getY() != loc.getY() || player.getLocation().getZ() != loc.getZ()) {
                new Message(PrefixType.ERROR, "Vous vous êtes déplacé ce qui a annulé votre téléportation.").send(player);
                customPlayer.setCanTp(true);
                Bukkit.getScheduler().cancelTask(tpCooldowns.get(player));
            }

        }, 0L, 20L);
        tpCooldowns.put(player, scheduler);
    }
}

