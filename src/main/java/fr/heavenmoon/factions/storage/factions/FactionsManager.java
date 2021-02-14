package fr.heavenmoon.factions.storage.factions;

import com.google.gson.Gson;
import fr.heavenmoon.factions.HeavenFactions;
import fr.moon.core.common.database.DatabaseKey;
import fr.moon.core.common.database.DatabaseManager;
import fr.moon.core.common.redis.RedisKey;
import fr.moon.core.common.redis.management.RedisManager;
import fr.moon.core.common.utils.wrappers.LambdaWrapper;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class FactionsManager {

    private final HeavenFactions plugin;

    public FactionsManager(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    public CustomFaction get(String id) {
        RedissonClient redissonClient = RedisManager.FACTION.getRedissonClient();
        RMap<String, String> factionsMap = redissonClient.getMap(RedisKey.FACTION);
        CustomFaction customFaction;

        if (factionsMap.containsKey(id)) {
            customFaction = fromJson(factionsMap.get(id));
        } else {
            try {
                LambdaWrapper<String> json = new LambdaWrapper<>(null);
                DatabaseManager.FACTION.getDatabaseAccess().query("SELECT content FROM " + DatabaseKey.FACTION + " WHERE id = '" + id + "'", rs -> {
                    try {
                        if (rs.next()) {
                            json.setData(rs.getString(1));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                if (json.getData() != null) {
                    customFaction = fromJson(json.getData());
                } else {
                    customFaction = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return customFaction;
    }

    public List<CustomFaction> getAllFactions() {
        RedissonClient redissonClient = RedisManager.FACTION.getRedissonClient();
        RMap<String, String> factionsMap = redissonClient.getMap(RedisKey.FACTION);

        return factionsMap.values().stream().map(this::fromJson).sorted(CustomFaction::compareTo).collect(Collectors.toList());
    }

    public void add(CustomFaction customFaction) {
        DatabaseManager.FACTION.getDatabaseAccess().update("INSERT INTO " + DatabaseKey.FACTION + " SET id = '" + customFaction.getId() + "', name = '" + customFaction.getName() + "', content = '" + customFaction.toJson() + "'");
    }

    public void update(CustomFaction customFaction) {
        DatabaseManager.FACTION.getDatabaseAccess().update("UPDATE " + DatabaseKey.FACTION + " SET id = '" + customFaction.getId() + "', name = '" + customFaction.getName() + "', content = '" + customFaction.toJson() + "' WHERE id = '" + customFaction.getId() + "'");
    }

    public void commit(CustomFaction customFaction) {
        RedissonClient redissonClient = RedisManager.FACTION.getRedissonClient();
        RMap<String, String> factionsMap = redissonClient.getMap(RedisKey.FACTION);

        String factionId = customFaction.getId();

        if (!factionsMap.containsKey(factionId)) {
            factionsMap.put(factionId, customFaction.toJson());
        }
    }

    public void delete(CustomFaction customFaction) {
        DatabaseManager.FACTION.getDatabaseAccess().update("DELETE FROM '" + DatabaseKey.FACTION + "' WHERE id='" + customFaction.getId() + "'");
    }

    public void remove(CustomFaction customFaction) {
        RedissonClient redissonClient = RedisManager.FACTION.getRedissonClient();
        RMap<Integer, String> factionsMap = redissonClient.getMap(RedisKey.FACTION);

        String factionId = (customFaction.getId());

        factionsMap.remove(factionId);
    }

    public CustomFaction fromJson(String json) {
        return new Gson().fromJson(json, CustomFaction.class);
    }
}
