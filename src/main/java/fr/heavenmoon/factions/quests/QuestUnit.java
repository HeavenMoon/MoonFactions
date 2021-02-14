package fr.heavenmoon.factions.quests;

import fr.heavenmoon.factions.quests.template.QuestFarmPotatoes;
import fr.heavenmoon.factions.quests.template.QuestMineIron;

import java.util.Arrays;

public enum QuestUnit {

    FARM_POTATOES_EASY(new QuestFarmPotatoes(QuestType.FARMING, QuestLevel.EASY)),
    FARM_POTATOES_MEDIUM(new QuestFarmPotatoes(QuestType.FARMING, QuestLevel.MEDIUM)),
    FARM_POTATOES_HARD(new QuestFarmPotatoes(QuestType.FARMING, QuestLevel.HARD)),

    MINE_IRONORE_EASY(new QuestMineIron(QuestType.MINING, QuestLevel.EASY)),
    MINE_IRONORE_MEDIUM(new QuestMineIron(QuestType.MINING, QuestLevel.MEDIUM)),
    MINE_IRONORE_HARD(new QuestMineIron(QuestType.MINING, QuestLevel.HARD));

    private AbstractQuest quest;

    QuestUnit(AbstractQuest quest) {
        this.quest = quest;
    }

    public static QuestUnit getQuestByName(String questName) {
        return Arrays.stream(values()).filter(quest -> quest.getQuest().getName().equalsIgnoreCase(questName)).findFirst().orElse(null);
    }

    public AbstractQuest getQuest() {
        return quest;
    }

    public void setQuest(AbstractQuest quest) {
        this.quest = quest;
    }
}
