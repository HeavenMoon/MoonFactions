package fr.heavenmoon.factions.crates;

import java.util.List;
import java.util.Random;

public class CrateContainer {

    private String name;
    private CrateLevel level;
    private List<CrateItem> rewards;

    public CrateContainer(String name, CrateLevel level, List<CrateItem> rewards) {
        this.name = name;
        this.level = level;
        this.rewards = rewards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CrateLevel getLevel() {
        return level;
    }

    public void setLevel(CrateLevel level) {
        this.level = level;
    }

    public List<CrateItem> getRewards() {
        return rewards;
    }

    public void setReward(List<CrateItem> reward) {
        this.rewards = reward;
    }

    public CrateItem getRandomReward() {
        Random random = new Random();
        return rewards.get(random.nextInt(rewards.size()));
    }

}
