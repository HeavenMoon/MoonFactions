package fr.heavenmoon.factions.storage.factions;

import com.google.gson.Gson;

import java.util.List;
import java.util.UUID;

public class CustomFaction implements Comparable<CustomFaction> {

    private String id;
    private String name;
    private List<UUID> players;
    private long dusts;

    public CustomFaction() {
    }

    public CustomFaction(String id, String name, List<UUID> players, long dusts) {
        this.id = id;
        this.name = name;
        this.players = players;
        this.dusts = dusts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public void setPlayers(List<UUID> players) {
        this.players = players;
    }

    public long getDusts() {
        return dusts;
    }

    public void setDusts(long dusts) {
        this.dusts = dusts;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public int compareTo(CustomFaction customFaction) {
        int compareDust = (int) customFaction.getDusts();

        return compareDust - (int) this.dusts;
    }
}
