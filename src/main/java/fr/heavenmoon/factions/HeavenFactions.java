package fr.heavenmoon.factions;

import fr.heavenmoon.factions.crates.CrateManager;
import fr.heavenmoon.factions.homes.HomesManager;
import fr.heavenmoon.factions.koth.KothManager;
import fr.heavenmoon.factions.quests.QuestsManager;
import fr.moon.core.bukkit.MoonBukkitCore;
import fr.heavenmoon.factions.enderchest.EnderChestManager;
import fr.heavenmoon.factions.heavenzone.HeavenZoneManager;
import fr.heavenmoon.factions.scoreboard.ScoreboardManager;
import fr.heavenmoon.factions.storage.factions.FactionsManager;
import fr.heavenmoon.factions.storage.players.FPlayersManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class HeavenFactions extends JavaPlugin {

    private MoonBukkitCore api;
    private static HeavenFactions instance;

    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    private FPlayersManager fPlayersManager;
    private FactionsManager factionsManager;
    private ScoreboardManager scoreboardManager;
    private HomesManager homesManager;
    private KothManager kothManager;
    private CrateManager crateManager;
    private HeavenZoneManager heavenZoneManager;
    private QuestsManager questsManager;
    private EnderChestManager enderChestManager;

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;

        saveDefaultConfig();

        this.api = MoonBukkitCore.get();

        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);

        this.fPlayersManager = new FPlayersManager(this);
        this.factionsManager = new FactionsManager(this);
        scoreboardManager = new ScoreboardManager(this);
        this.homesManager = new HomesManager(this);
        this.kothManager = new KothManager(this);
        this.crateManager = new CrateManager(this);
        this.heavenZoneManager = new HeavenZoneManager(this);
        this.questsManager = new QuestsManager(this);
        this.enderChestManager = new EnderChestManager(this);

        new Actions(this);

        System.out.println("[HeavenFaction] Enabled");
    }

    @Override
    public void onDisable() {
        scoreboardManager.onDisable();
        super.onDisable();
    }

    public static HeavenFactions get() {
        return instance;
    }

    public MoonBukkitCore getApi() {
        return api;
    }

    public ScheduledExecutorService getExecutorMonoThread() {
        return executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    public FPlayersManager getfPlayersManager() {
        return fPlayersManager;
    }

    public FactionsManager getFactionsManager() {
        return factionsManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public HomesManager getHomesManager() {
        return homesManager;
    }

    public KothManager getKothManager() {
        return kothManager;
    }

    public CrateManager getCrateManager() {
        return crateManager;
    }

    public HeavenZoneManager getHeavenZoneManager() {
        return heavenZoneManager;
    }

    public QuestsManager getQuestsManager() {
        return questsManager;
    }

    public EnderChestManager getEnderChestManager() {
        return enderChestManager;
    }
}
