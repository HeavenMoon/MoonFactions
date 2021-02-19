package fr.heavenmoon.factions.listeners.factions;

import com.massivecraft.factions.event.EventFactionsDisband;
import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.persistanceapi.customs.factions.CustomFaction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionsDisbandListener implements Listener {

    private final HeavenFactions plugin;

    public FactionsDisbandListener(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(EventFactionsDisband event) {
        CustomFaction customFaction = plugin.getPersistanceManager().getFactionsManager().get(event.getFaction().getId());
        plugin.getPersistanceManager().getFactionsManager().delete(customFaction);
        plugin.getPersistanceManager().getFactionsManager().remove(customFaction);
    }
}
