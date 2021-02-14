package fr.heavenmoon.factions.storage.quests;

import com.google.gson.Gson;
import fr.heavenmoon.factions.quests.QuestLevel;
import fr.heavenmoon.factions.quests.QuestUnit;
import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;

public class QuestData {

    private QuestUnit activeQuest;
    private Set<QuestUnit> questsCompletes;
    private QuestLevel level;
    private int progress;

    public QuestData(QuestUnit activeQuest) {
        this(activeQuest, QuestLevel.EASY, 0);
    }

    public QuestData(QuestUnit activeQuest, QuestLevel level) {
        this(activeQuest, level, 0);
    }

    public QuestData(QuestLevel level) {
        this(null, level, 0);
    }

    public QuestData(QuestUnit activeQuest, QuestLevel level, int progress) {
        this.activeQuest = activeQuest;
        this.questsCompletes = new HashSet<>();
        this.level = level;
        this.progress = progress;
    }

    public QuestUnit getActiveQuest() {
        return activeQuest;
    }

    public void setActiveQuest(QuestUnit activeQuest) {
        this.activeQuest = activeQuest;
    }

    public Set<QuestUnit> getQuestsCompletes() {
        return questsCompletes;
    }

    public boolean hasActiveQuest() {
        return activeQuest != null;
    }

    public void setQuestsCompletes(Set<QuestUnit> questsCompletes) {
        this.questsCompletes = questsCompletes;
    }

    public QuestLevel getQuestLevel() {
        return level;
    }

    public void setQuestLevel(QuestLevel level) {
        this.level = level;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getPourcentOfProgress() {
        return "" + activeQuest.getQuest().getNeeding() * (progress/100) + "%";
    }

    public String pourcentToString() {
        StringBuilder sb_gray = new StringBuilder();
        StringBuilder sb_green = new StringBuilder();
        int total = 25;
        for (int i = 0; i < total; i++) {
            if (i < total * (14 / 100.0)) {
                sb_green.append("|");
            } else {
                sb_gray.append("|");
            }
        }
        return ChatColor.GRAY + sb_gray.toString() + ChatColor.GREEN + sb_green.toString();
    }

    public QuestData fromJson(String json) {
        return new Gson().fromJson(json, QuestData.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
