package fr.heavenmoon.factions.listeners.factions;

import com.massivecraft.factions.event.EventFactionsDisband;
import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.storage.factions.CustomFaction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionsDisbandListener implements Listener {

    private final HeavenFactions plugin;

    public FactionsDisbandListener(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(EventFactionsDisband event) {
        CustomFaction customFaction = plugin.getFactionsManager().get(event.getFactionId());
        plugin.getFactionsManager().delete(customFaction);
        plugin.getFactionsManager().remove(customFaction);
    }
}
