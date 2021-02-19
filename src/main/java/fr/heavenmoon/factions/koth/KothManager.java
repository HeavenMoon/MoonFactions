package fr.heavenmoon.factions.koth;

import fr.heavenmoon.persistanceapi.customs.factions.CustomFaction;
import fr.heavenmoon.core.bukkit.format.Message;
import fr.heavenmoon.core.bukkit.utils.ActionbarBuilder;
import fr.heavenmoon.core.common.format.message.PrefixType;
import fr.heavenmoon.core.common.utils.wrappers.LambdaWrapper;
import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.utils.Cuboid;
import fr.heavenmoon.factions.utils.LocationUtils;
import fr.heavenmoon.factions.utils.SmokeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class KothManager {

    private final HeavenFactions plugin;
    private List<KothFaction> kothFactions;
    private boolean manyFactions;

    private Cuboid region;

    private ScheduledFuture startTask;
    private ScheduledFuture gameTask;

    public KothManager(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    public Optional<KothFaction> getKothFaction(CustomFaction faction) {
        return kothFactions.stream().filter(kf -> kf.getFaction().getId().equals(faction.getId())).findFirst();
    }

    public void start() {
        kothFactions = new ArrayList<>();
        this.region = new Cuboid(getFirstLocation(), getSecondLocation());
        Arrays.asList("Un event KOTH vient de commencer !", "/koth join | Pour rejoindre la capture !").forEach(Bukkit::broadcastMessage);
    }

    public boolean canStart() {
        if (isEnoughFactions()) {
            LambdaWrapper<Integer> seconds = new LambdaWrapper<>(30);
            startTask = plugin.getCore().getCommons().getScheduler().scheduleAtFixedRate(() -> {
                if (Arrays.asList(30, 15, 10, 5, 4, 3, 2, 1).contains(seconds.getData())) {
                    kothFactions.forEach(kothFaction -> kothFaction.getKothPlayers().forEach(player -> new Message(PrefixType.SERVER, "Le KOTH va commencé dans " + seconds.getData() + " secondes..").send(player)));
                    seconds.setData(seconds.getData() - 1);
                } else if (seconds.getData() == 0) {
                    //Create explosion (eject player with random vector))
                    SmokeUtils.spawnCloudSimple(region.getRandomLocation());
                    KothState.setCurrent(KothState.STARTING);
                    startTask.cancel(true);
                    startCapture();
                }
            }, 0, 1, TimeUnit.SECONDS);
        }
        return false;
    }

    public void startCapture() {
        gameTask = plugin.getCore().getCommons().getScheduler().scheduleAtFixedRate(() -> {
            if (manyFactions) {
                stopAllCaptures();
            } else {
                kothFactions.forEach(KothFaction::capture);
            }
            kothFactions.forEach(kothFaction -> kothFaction.getKothPlayers().forEach(kothPlayer -> {
                new ActionbarBuilder(kothFaction.pourcentToString() + ChatColor.GREEN + " " + kothFaction.getPourcent() + "%", 1).send(kothPlayer);
            }));
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void stopAllCaptures() {
        kothFactions.forEach(kothFaction -> kothFaction.setInCapture(false));
    }

    public void addFaction(CustomFaction faction) {
        if (!kothFactions.contains(new KothFaction(faction))) kothFactions.add(new KothFaction(faction));
    }

    public KothFaction getKothFactionInCapture() {
        return kothFactions.stream().filter(KothFaction::isInCapture).findFirst().orElse(null);
    }

    public boolean isEnoughFactions() {
        return kothFactions.size() >= 3;
    }

    public boolean isAlreadyInKoth(Player player) {
        return kothFactions.stream().anyMatch(kothFaction -> kothFaction.getKothPlayers().contains(player));
    }

    public boolean isAlreadyInKoth(CustomFaction customFaction) {
        return getKothFaction(customFaction).isPresent();
    }

    public boolean isManyFactions() {
        return manyFactions;
    }

    public void setManyFactions(boolean manyFactions) {
        this.manyFactions = manyFactions;
    }

    public ScheduledFuture getGameTask() {
        return gameTask;
    }

    public Location getFirstLocation() {
        return LocationUtils.str2loc(plugin.getConfig().getString("koth.region.1"));
    }

    public Location getSecondLocation() {
        return LocationUtils.str2loc(plugin.getConfig().getString("koth.region.2"));
    }

    public Cuboid getRegion() {
        return region;
    }

    public void setRegion(Cuboid region) {
        this.region = region;
    }

    public List<KothFaction> getKothFactions() {
        return kothFactions;
    }
}
