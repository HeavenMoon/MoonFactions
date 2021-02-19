package fr.heavenmoon.factions;

import fr.heavenmoon.factions.commands.*;
import fr.heavenmoon.factions.listeners.*;
import fr.heavenmoon.factions.utils.DCommand;
import fr.heavenmoon.factions.commands.home.DelhomeCommand;
import fr.heavenmoon.factions.commands.home.HomeCommand;
import fr.heavenmoon.factions.commands.home.SethomeCommand;
import fr.heavenmoon.factions.commands.spawn.SetSpawnCommand;
import fr.heavenmoon.factions.commands.spawn.SpawnCommand;
import fr.heavenmoon.factions.listeners.crate.CrateListener;
import fr.heavenmoon.factions.listeners.enderchest.EnderChestListener;
import fr.heavenmoon.factions.listeners.factions.FactionsCreateListener;
import fr.heavenmoon.factions.listeners.factions.FactionsDisbandListener;
import fr.heavenmoon.factions.listeners.factions.FactionsMembershipChangeListener;
import fr.heavenmoon.factions.listeners.factions.FactionsNameChangeListener;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import java.util.Collections;

public class Actions {

    private final HeavenFactions plugin;

    public Actions(HeavenFactions plugin) {
        this.plugin = plugin;

        registerEvents();
        loadCommands();
    }

    private void register(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    private void load(String command, CommandExecutor executor) {
        plugin.getCommand(command).setExecutor(executor);
    }

    public void registerEvents() {
        register(new PlayerJoinListener(plugin));
        register(new PlayerQuitListener(plugin));
        register(new PlayerDeathListener(plugin));
        register(new PlayerRespawnListener(plugin));
        register(new FactionsCreateListener(plugin));
        register(new FactionsMembershipChangeListener(plugin));
        register(new FactionsNameChangeListener(plugin));
        register(new FactionsDisbandListener(plugin));
        register(new HeavenZoneListener(plugin));
        register(new CrateListener(plugin));
        register(new EnderChestListener(plugin));
    }

    public void loadCommands() {
        new DCommand("delhome", "/delhome", "del home", null, Collections.singletonList(""), new DelhomeCommand(plugin), plugin);
        new DCommand("sethome", "/sethome", "set home", null, Collections.singletonList(""), new SethomeCommand(plugin), plugin);
        new DCommand("home", "/home", "show home", null, Collections.singletonList(""), new HomeCommand(plugin), plugin);
        new DCommand("ftop", "/ftop", "Show faction classement", null, Collections.singletonList(""), new FTopCommand(plugin), plugin);
        new DCommand("setspawn", "/setspawn", "set server spawn", null, Collections.singletonList(""), new SetSpawnCommand(plugin), plugin);
        new DCommand("spawn", "/spawn", "go to spawn", null, Collections.singletonList(""), new SpawnCommand(plugin), plugin);
        new DCommand("setcrate", "/setcrate", "SetCrate command", null, Collections.singletonList(""), new SetCrateCommand(plugin), plugin);
        new DCommand("setheavenzone", "/setheavenzone", "SetHeavenZone command", null, Collections.singletonList(""), new HeavenZoneCommand(plugin), plugin);
        new DCommand("givekey", "/givekey", "Givekey command", null, Collections.singletonList(""), new GiveCrateKeyCommand(plugin), plugin);
    }

}
