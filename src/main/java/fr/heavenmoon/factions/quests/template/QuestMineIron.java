package fr.heavenmoon.factions.quests.template;

import fr.heavenmoon.factions.quests.QuestLevel;
import fr.heavenmoon.factions.quests.AbstractQuest;
import fr.heavenmoon.factions.quests.QuestGoalType;
import fr.heavenmoon.factions.quests.QuestType;
import fr.heavenmoon.factions.storage.players.FactionPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class QuestMineIron extends AbstractQuest {

    public QuestMineIron(QuestType questType, QuestLevel questLevel) {
        this.questType = questType;
        this.questLevel = questLevel;
    }

    @Override
    public String getName() {
        return "MINE-" + getNeeding() + "-IRONORE";
    }

    @Override
    public String getDisplayName() {
        return "Miner " + getNeeding() + " minerais de fer";
    }

    @Override
    public QuestType getQuestType() {
        return QuestType.MINING;
    }

    @Override
    public QuestGoalType getQuestGoalType() {
        return QuestGoalType.IRON_ORE;
    }

    @Override
    public ItemStack getItemDisplayed() {
        return new ItemStack(Material.IRON_ORE, getNeeding());
    }

    @Override
    public void getRewards(FactionPlayer factionPlayer) {

    }

}
