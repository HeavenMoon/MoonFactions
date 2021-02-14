package fr.heavenmoon.factions.quests;

public enum QuestType {

    FARMING("Farming", "⧶"),
    MINING("Minage", "⸕"),
    KILLING("Killing", "☠");

    private String displayName;
    private String icon;

    QuestType(String displayName, String icon) {
        this.displayName = displayName;
        this.icon = icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
