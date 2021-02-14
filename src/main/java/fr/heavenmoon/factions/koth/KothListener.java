package fr.heavenmoon.factions.koth;

import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.storage.factions.CustomFaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class KothListener implements Listener {

    private final HeavenFactions plugin;

    public KothListener(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        if (KothState.isState(KothState.STARTING)) {
            if (plugin.getKothManager().getRegion().isIn(player.getLocation())) {
                CustomFaction customFaction = plugin.getfPlayersManager().get(player).getCustomFaction();
                if (!plugin.getKothManager().getKothFactionInCapture().getFaction().getId().equals(customFaction.getId()) && !plugin.getKothManager().isManyFactions()) {
                    plugin.getKothManager().getGameTask().cancel(true);
                    plugin.getKothManager().setManyFactions(true);
                    //uncap
                } else {
                    plugin.getKothManager().getGameTask().cancel(false);
                    plugin.getKothManager().setManyFactions(false);
                    //cap
                }
            }
        }

    }
}
