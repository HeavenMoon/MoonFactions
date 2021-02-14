package fr.heavenmoon.factions.quests;

import org.bukkit.ChatColor;

public enum QuestLevel {

    EASY("Facile", ChatColor.GRAY, 0),
    MEDIUM("Moyen", ChatColor.YELLOW, 1),
    HARD("Difficile", ChatColor.RED, 2);

    private String levelName;
    private ChatColor levelColor;
    private int power;

    QuestLevel(String levelName, ChatColor levelColor, int power) {
        this.levelName = levelName;
        this.levelColor = levelColor;
        this.power = power;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public ChatColor getLevelColor() {
        return levelColor;
    }

    public void setLevelColor(ChatColor levelColor) {
        this.levelColor = levelColor;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
