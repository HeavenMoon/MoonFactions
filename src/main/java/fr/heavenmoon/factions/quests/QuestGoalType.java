package fr.heavenmoon.factions.quests;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.Arrays;

public enum QuestGoalType {

    POTATOES(QuestType.FARMING, Material.POTATO_ITEM),
    IRON_ORE(QuestType.MINING, Material.IRON_ORE);

    private Material type;
    private QuestType questType;
    private EntityType entityType;

    QuestGoalType(QuestType questType, Material type) {
        this.questType = questType;
        this.type = type;
    }

    QuestGoalType(QuestType questType, EntityType entityType) {
        this.questType = questType;
        this.entityType = entityType;
    }

    public static boolean containsMaterial(Material material) {
        return Arrays.stream(values()).anyMatch(goalType -> goalType.getType().equals(material));
    }

    public Material getType() {
        return type;
    }

    public void setType(Material type) {
        this.type = type;
    }

    public QuestType getQuestType() {
        return questType;
    }

    public void setQuestType(QuestType questType) {
        this.questType = questType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }
}
