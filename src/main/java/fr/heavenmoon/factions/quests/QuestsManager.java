package fr.heavenmoon.factions.quests;

import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.storage.players.FactionPlayer;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class QuestsManager {

    private final HeavenFactions plugin;

    public QuestsManager(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    public void addProgress(FactionPlayer factionPlayer) {
        factionPlayer.getQuestData().setProgress(factionPlayer.getQuestData().getProgress() + 1);
        plugin.getfPlayersManager().commit(factionPlayer);
        endQuest(factionPlayer);
        plugin.getScoreboardManager().update(factionPlayer.toPlayer());
    }

    public void startQuest(FactionPlayer factionPlayer, QuestUnit quest) {
        factionPlayer.getQuestData().setActiveQuest(quest);
        factionPlayer.getQuestData().setProgress(0);
        plugin.getScoreboardManager().update(factionPlayer.toPlayer());
    }

    public void endQuest(FactionPlayer factionPlayer) {
        if (factionPlayer.hasEndQuest()) {
            QuestUnit quest = factionPlayer.getQuestData().getActiveQuest();
            Arrays.asList(ChatColor.GREEN + "Vous avez terminé la quête " + ChatColor.YELLOW + quest.getQuest().getDisplayName(),
                    ChatColor.RED + "/quetes" + ChatColor.GREEN + " pour voir vos qûetes disponibles.").forEach(msg -> factionPlayer.toPlayer().sendMessage(msg));
            factionPlayer.getQuestData().getQuestsCompletes().add(quest);
            factionPlayer.getQuestData().setActiveQuest(null);
            factionPlayer.getQuestData().setProgress(0);
            plugin.getfPlayersManager().commit(factionPlayer);
            plugin.getScoreboardManager().update(factionPlayer.toPlayer());
        }
    }
}
