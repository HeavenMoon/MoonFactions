package fr.heavenmoon.factions.listeners.quests;

import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.quests.AbstractQuest;
import fr.heavenmoon.factions.quests.QuestType;
import fr.heavenmoon.factions.storage.players.FactionPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillEntityListener implements Listener {

    private final HeavenFactions plugin;

    public KillEntityListener(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onKillEntity(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getKiller() != null) {
            FactionPlayer factionPlayer = plugin.getfPlayersManager().get(entity.getKiller());
            if (factionPlayer.hasActiveQuest()) {
                AbstractQuest activeQuest = factionPlayer.getQuestData().getActiveQuest().getQuest();
                if (activeQuest.getQuestGoalType().getQuestType() == QuestType.KILLING && activeQuest.getQuestGoalType().getEntityType() == entity.getType()) {
                    plugin.getQuestsManager().addProgress(factionPlayer);
                }
            }
        }
    }
}