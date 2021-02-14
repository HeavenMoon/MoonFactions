package fr.heavenmoon.factions.quests;

import fr.heavenmoon.factions.storage.players.FactionPlayer;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractQuest {

    public QuestType questType;
    public QuestLevel questLevel;

    public abstract String getName();

    public abstract String getDisplayName();

    public abstract QuestType getQuestType();

    public abstract QuestGoalType getQuestGoalType();

    public QuestLevel getQuestLevel() {
        return questLevel;
    }

    public int getNeeding() {
        if (questType == QuestType.FARMING || questType == QuestType.MINING) {
            return (questLevel == QuestLevel.EASY ? 16 : (questLevel == QuestLevel.MEDIUM ? 32 : 64));
        }
        return (questLevel == QuestLevel.EASY ? 15 : (questLevel == QuestLevel.MEDIUM ? 30 : 45));
    }

    public abstract ItemStack getItemDisplayed();

    public abstract void getRewards(FactionPlayer factionPlayer);

}
