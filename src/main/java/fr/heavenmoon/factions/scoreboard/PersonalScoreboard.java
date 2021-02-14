package fr.heavenmoon.factions.scoreboard;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import fr.heavenmoon.factions.HeavenFactions;
import fr.moon.core.bukkit.scoreboard.ObjectiveSign;
import fr.moon.core.bukkit.scoreboard.ScoreboardTeam;
import fr.moon.core.common.data.player.CustomPlayer;
import fr.moon.core.common.data.player.rank.RankList;
import fr.moon.core.common.utils.NumberUtils;
import fr.heavenmoon.factions.storage.players.FactionPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class PersonalScoreboard {

    private final HeavenFactions plugin;
    private final ObjectiveSign objectiveSign;

    private CustomPlayer customPlayer;
    private FactionPlayer factionPlayer;
    private Player player;

    private RankList rank;
    private long stars;
    private long gemmes;

    private Faction faction;

    public PersonalScoreboard(HeavenFactions plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        objectiveSign = new ObjectiveSign("heavenmoon", "Heavenmoon");

        reloadData();
        objectiveSign.addReceiver(player);
        refreshTeams();
    }

    public void reloadData() {
        this.customPlayer = plugin.getApi().getCommons().getPlayerManager().get(this.player.getName(), this.player.getUniqueId().toString());
        this.rank = customPlayer.getRank().getRank();
        this.stars = customPlayer.getStars();
        this.gemmes = customPlayer.getGemmes();

        this.factionPlayer = plugin.getfPlayersManager().get(this.player);
        if (factionPlayer.hasFaction()) {
            this.faction = FactionColl.get().get(factionPlayer.getCustomFaction().getId());
        }
    }

    public void setLines(String ip) {
        objectiveSign.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + "❃" + ChatColor.LIGHT_PURPLE + " Heaven" + ChatColor.DARK_PURPLE + "Moon " + ChatColor.AQUA + "❃");

        objectiveSign.setLine(0, ChatColor.GRAY + "");
        if (factionPlayer.hasActiveQuest()) {
            objectiveSign.setLine(1, ChatColor.AQUA + factionPlayer.getQuestData().getActiveQuest().getQuest().getQuestType().getIcon() + " " + factionPlayer.getQuestData().getActiveQuest().getQuest().getQuestLevel().getLevelColor() + factionPlayer.getQuestData().getActiveQuest().getQuest().getDisplayName());
            objectiveSign.setLine(2, " " + factionPlayer.getQuestData().pourcentToString() + ChatColor.GRAY + " - " + factionPlayer.getQuestData().getPourcentOfProgress());
            objectiveSign.setLine(3, ChatColor.GREEN + "");
            objectiveSign.setLine(4, ChatColor.GRAY + "Compte: " + this.rank.getStyle().getColor() + customPlayer.getName());
            objectiveSign.setLine(5, ChatColor.GRAY + "Rank: " + this.rank.getPrefix());
            objectiveSign.setLine(6, ChatColor.GRAY + "Etoiles: " + ChatColor.LIGHT_PURPLE + NumberUtils.format(this.stars) + " ✯");
            objectiveSign.setLine(7, ChatColor.GRAY + "Gemmes: " + ChatColor.DARK_PURPLE + NumberUtils.format(this.gemmes) + " ◈");
            objectiveSign.setLine(8, ChatColor.AQUA + "");
            if (faction != null) {
                objectiveSign.setLine(9, ChatColor.AQUA + "✪ " + ChatColor.LIGHT_PURPLE + faction.getName());
                objectiveSign.setLine(10, ChatColor.GRAY + "Membres: " + ChatColor.LIGHT_PURPLE + faction.getOnlinePlayers().size() + "/" + faction.getMPlayers().size());
                objectiveSign.setLine(11, ChatColor.GRAY + "Power: " + ChatColor.LIGHT_PURPLE + faction.getPower() + "/" + faction.getPowerMax());
                objectiveSign.setLine(12, ChatColor.GRAY + "Dust(s): " + ChatColor.LIGHT_PURPLE + NumberUtils.format(factionPlayer.getCustomFaction().getDusts()));
                objectiveSign.setLine(13, ChatColor.LIGHT_PURPLE + "");
                objectiveSign.setLine(14, ChatColor.AQUA + "  " + ip);
            } else {
                objectiveSign.setLine(9, ChatColor.AQUA + "✪ " + ChatColor.RED + "Aucune faction");
                objectiveSign.setLine(10, ChatColor.LIGHT_PURPLE + "");
                objectiveSign.setLine(11, ChatColor.AQUA + "  " + ip);
            }
        } else {
            objectiveSign.setLine(1, ChatColor.AQUA + "‽" + ChatColor.RED + "Aucunes quêtes en cours");
            objectiveSign.setLine(2, ChatColor.GREEN + "");
            objectiveSign.setLine(3, ChatColor.GRAY + "Compte: " + this.rank.getStyle().getColor() + customPlayer.getName());
            objectiveSign.setLine(4, ChatColor.GRAY + "Rank: " + this.rank.getPrefix());
            objectiveSign.setLine(5, ChatColor.GRAY + "Etoiles: " + ChatColor.LIGHT_PURPLE + NumberUtils.format(this.stars) + " ✯");
            objectiveSign.setLine(6, ChatColor.GRAY + "Gemmes: " + ChatColor.DARK_PURPLE + NumberUtils.format(this.gemmes) + " ◈");
            objectiveSign.setLine(7, ChatColor.AQUA + "");
            if (faction != null) {
                objectiveSign.setLine(8, ChatColor.AQUA + "✪ " + ChatColor.LIGHT_PURPLE + faction.getName());
                objectiveSign.setLine(9, ChatColor.GRAY + "Membres: " + ChatColor.LIGHT_PURPLE + faction.getOnlinePlayers().size() + "/" + faction.getMPlayers().size());
                objectiveSign.setLine(10, ChatColor.GRAY + "Power: " + ChatColor.LIGHT_PURPLE + faction.getPower() + "/" + faction.getPowerMax());
                objectiveSign.setLine(11, ChatColor.GRAY + "Dust(s): " + ChatColor.LIGHT_PURPLE + NumberUtils.format(factionPlayer.getCustomFaction().getDusts()));
                objectiveSign.setLine(12, ChatColor.LIGHT_PURPLE + "");
                objectiveSign.setLine(13, ChatColor.AQUA + "  " + ip);
            } else {
                objectiveSign.setLine(8, ChatColor.AQUA + "✪ " + ChatColor.RED + "Aucune faction");
                objectiveSign.setLine(9, ChatColor.LIGHT_PURPLE + "");
                objectiveSign.setLine(10, ChatColor.AQUA + "  " + ip);
            }
        }

        objectiveSign.updateLines();
    }

    public void onLogout() {
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(player.getUniqueId()));
    }

    public void refreshTeams() {
        for (Player p1 : Bukkit.getOnlinePlayers()) {
            for (Player p2 : Bukkit.getOnlinePlayers()) {
                ScoreboardTeam team = plugin.getApi().getSbTeam("" + Arrays.stream(RankList.values()).filter(r -> plugin.getApi().getCommons().getPlayerManager().get(p2.getName(), p2.getUniqueId().toString()).getRank().getRank() == r).findAny().orElse(RankList.EXPLORATEUR).getPower());

                if (team != null) {
                    ((CraftPlayer) p1).getHandle().playerConnection.sendPacket(team.addOrRemovePlayer(3, p2.getName()));
                }
            }
        }
    }
}