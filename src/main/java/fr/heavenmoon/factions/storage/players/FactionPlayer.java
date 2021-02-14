package fr.heavenmoon.factions.storage.players;

import com.google.gson.Gson;
import fr.heavenmoon.factions.homes.CustomHome;
import fr.heavenmoon.factions.quests.QuestLevel;
import fr.heavenmoon.factions.quests.QuestUnit;
import fr.heavenmoon.factions.storage.quests.QuestData;
import fr.heavenmoon.factions.storage.factions.CustomFaction;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionPlayer {

    private UUID uuid;
    private String name;
    private CustomFaction customFaction;
    private int maxHomes;
    private List<CustomHome> homes;
    private long dusts;
    private int victoryInKoth;
    private int victoryInGravity;

    private QuestData questData;

    public FactionPlayer(String name, UUID uuid) {
        this.uuid = uuid;
        this.name = name;
        this.customFaction = null;
        this.maxHomes = 7;
        this.homes = new ArrayList<>();
        this.dusts = 0;
        this.victoryInKoth = 0;
        this.victoryInGravity = 0;
        this.questData = new QuestData(QuestLevel.EASY);
    }

    public FactionPlayer(UUID uuid, String name, CustomFaction customFaction, int maxHomes, List<CustomHome> homes, long dusts, int victoryInKoth, int victoryInGravity, QuestData questData) {
        this.uuid = uuid;
        this.name = name;
        this.customFaction = customFaction;
        this.maxHomes = maxHomes;
        this.homes = homes;
        this.dusts = dusts;
        this.victoryInKoth = victoryInKoth;
        this.victoryInGravity = victoryInGravity;
        this.questData = questData;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomFaction getCustomFaction() {
        return customFaction;
    }

    public void setCustomFaction(CustomFaction customFaction) {
        this.customFaction = customFaction;
    }

    public int getMaxHomes() {
        return maxHomes;
    }

    public void setMaxHomes(int maxHomes) {
        this.maxHomes = maxHomes;
    }

    public List<CustomHome> getHomes() {
        return homes;
    }

    public void setHomes(List<CustomHome> homes) {
        this.homes = homes;
    }

    public long getDusts() {
        return dusts;
    }

    public void setDusts(long dusts) {
        this.dusts = dusts;
    }

    public int getVictoryInKoth() {
        return victoryInKoth;
    }

    public void setVictoryInKoth(int victoryInKoth) {
        this.victoryInKoth = victoryInKoth;
    }

    public int getVictoryInGravity() {
        return victoryInGravity;
    }

    public void setVictoryInGravity(int victoryInGravity) {
        this.victoryInGravity = victoryInGravity;
    }

    public int getKills() {
        return Bukkit.getPlayer(this.uuid).getStatistic(Statistic.PLAYER_KILLS);
    }

    public int getDeaths() {
        return Bukkit.getPlayer(this.uuid).getStatistic(Statistic.DEATHS);
    }

    public boolean hasFaction() {
        return customFaction != null;
    }

    public Player toPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    /*

    Quests

     */

    public boolean hasEndQuest() {
        return questData.getActiveQuest().getQuest().getNeeding() == questData.getProgress();
    }

    public boolean hasActiveQuest() {
        return questData.hasActiveQuest();
    }

    public QuestData getQuestData() {
        return questData;
    }

    public void setQuestData(QuestData questData) {
        this.questData = questData;
    }

    public QuestLevel getQuestLevel() {
        return questData.getQuestLevel();
    }

    public List<QuestUnit> getQuestsCompletes() {
        return new ArrayList<>(questData.getQuestsCompletes());
    }

    public boolean hasRequiredLevel(QuestUnit quest) {
        return this.getQuestLevel().getPower() >= quest.getQuest().getQuestLevel().getPower();
    }

    public boolean hasCompleteQuest(QuestUnit quest) {
        return this.getQuestsCompletes().stream().anyMatch(abstractQuest -> abstractQuest.equals(quest));
    }
}
