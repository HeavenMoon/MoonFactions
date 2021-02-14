package fr.heavenmoon.factions.listeners.quests;

import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.quests.AbstractQuest;
import fr.heavenmoon.factions.quests.QuestType;
import fr.heavenmoon.factions.storage.players.FactionPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakListener implements Listener {

    private final HeavenFactions plugin;

    public BreakListener(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        FactionPlayer factionPlayer = plugin.getfPlayersManager().get(player);

        if (factionPlayer.hasActiveQuest()) {
            AbstractQuest activeQuest = factionPlayer.getQuestData().getActiveQuest().getQuest();
            if ((activeQuest.getQuestGoalType().getQuestType() == QuestType.FARMING || activeQuest.getQuestGoalType().getQuestType() == QuestType.MINING) && activeQuest.getQuestGoalType().getType() == event.getBlock().getType()) {
                plugin.getQuestsManager().addProgress(factionPlayer);
            }
        }

    }

}
