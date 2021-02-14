package fr.heavenmoon.factions.listeners.factions;

import com.massivecraft.factions.event.EventFactionsCreate;
import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.storage.factions.CustomFaction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionsCreateListener implements Listener {

    private final HeavenFactions plugin;

    public FactionsCreateListener(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(EventFactionsCreate event) {
        List<UUID> PlayerList = new ArrayList<>();
        PlayerList.add(event.getMPlayer().getUuid());
        CustomFaction customFaction = new CustomFaction(event.getFactionId(), event.getFactionName(), PlayerList, 0);
        plugin.getFactionsManager().add(customFaction);
        plugin.getFactionsManager().commit(customFaction);
    }
}
