package fr.heavenmoon.factions;

import fr.heavenmoon.factions.crates.CrateManager;
import fr.heavenmoon.factions.homes.HomesManager;
import fr.heavenmoon.factions.koth.KothManager;
import fr.heavenmoon.persistanceapi.PersistanceManager;
import fr.heavenmoon.core.bukkit.MoonBukkitCore;
import fr.heavenmoon.factions.enderchest.EnderChestManager;
import fr.heavenmoon.factions.heavenzone.HeavenZoneManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class HeavenFactions extends JavaPlugin {

    private MoonBukkitCore core;
    private PersistanceManager persistanceManager;
    private static HeavenFactions instance;

    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    private HomesManager homesManager;
    private KothManager kothManager;
    private CrateManager crateManager;
    private HeavenZoneManager heavenZoneManager;
    private EnderChestManager enderChestManager;

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        this.core = MoonBukkitCore.get();
        this.persistanceManager = new PersistanceManager(getCore().getCommons().getConfig().getServerName(),
                getCore().getCommons().getDatabaseConfig(), getCore().getCommons().getRedisConfig());

        saveDefaultConfig();


        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);

        this.homesManager = new HomesManager(this);
        this.kothManager = new KothManager(this);
        this.crateManager = new CrateManager(this);
        this.heavenZoneManager = new HeavenZoneManager(this);
        this.enderChestManager = new EnderChestManager(this);

        new Actions(this);

        System.out.println("[HeavenFaction] Enabled");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static HeavenFactions get() {
        return instance;
    }

    public MoonBukkitCore getCore() {
        return core;
    }
    
    public PersistanceManager getPersistanceManager() { return persistanceManager; }

    public ScheduledExecutorService getExecutorMonoThread() {
        return executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
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
    
    public EnderChestManager getEnderChestManager() {
        return enderChestManager;
    }
}
