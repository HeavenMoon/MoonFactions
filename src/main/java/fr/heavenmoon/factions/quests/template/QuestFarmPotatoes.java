package fr.heavenmoon.factions.quests.template;

import fr.heavenmoon.factions.quests.QuestLevel;
import fr.heavenmoon.factions.quests.AbstractQuest;
import fr.heavenmoon.factions.quests.QuestGoalType;
import fr.heavenmoon.factions.quests.QuestType;
import fr.heavenmoon.factions.storage.players.FactionPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class QuestFarmPotatoes extends AbstractQuest {

    public QuestFarmPotatoes(QuestType questType, QuestLevel questLevel) {
        this.questType = questType;
        this.questLevel = questLevel;
    }

    @Override
    public String getName() {
        return "FARM-" + getNeeding() + "-POTATOES";
    }

    @Override
    public String getDisplayName() {
        return "Farmer " + getNeeding() + " patates";
    }

    @Override
    public QuestType getQuestType() {
        return QuestType.FARMING;
    }

    @Override
    public QuestGoalType getQuestGoalType() {
        return QuestGoalType.POTATOES;
    }

    @Override
    public ItemStack getItemDisplayed() {
        return new ItemStack(Material.POTATO_ITEM, getNeeding());
    }

    @Override
    public void getRewards(FactionPlayer factionPlayer) {

    }

}
