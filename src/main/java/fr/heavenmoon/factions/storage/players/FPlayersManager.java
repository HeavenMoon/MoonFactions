package fr.heavenmoon.factions.storage.players;

import com.google.gson.Gson;
import fr.heavenmoon.factions.HeavenFactions;
import fr.moon.core.bukkit.utils.BUniqueID;
import fr.moon.core.common.database.DatabaseKey;
import fr.moon.core.common.database.DatabaseManager;
import fr.moon.core.common.redis.RedisKey;
import fr.moon.core.common.redis.management.RedisManager;
import fr.moon.core.common.utils.wrappers.LambdaWrapper;
import org.bukkit.entity.Player;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.sql.SQLException;
import java.util.UUID;

public class FPlayersManager {

    private final HeavenFactions plugin;

    public FPlayersManager(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    public FactionPlayer get(Player player) {
        return get(player.getName());
    }

    public FactionPlayer get(String name) {
        String uuid = BUniqueID.get(name);
        String key = RedisKey.FPLAYER + uuid;
        RedissonClient redissonClient = RedisManager.FACTION.getRedissonClient();
        RBucket<String> customFPlayerRBucket = redissonClient.getBucket(key);
        FactionPlayer factionPlayer;

        if (customFPlayerRBucket.isExists()) {
            factionPlayer = fromJson(customFPlayerRBucket.get());
        } else {
            try {
                LambdaWrapper<String> json = new LambdaWrapper<>(null);
                DatabaseManager.FACTION.getDatabaseAccess().query("SELECT content FROM " + DatabaseKey.FPLAYER + " WHERE uuid = '" + uuid + "'", rs -> {
                    try {
                        if (rs.next()) {
                            json.setData(rs.getString(1));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                if (json.getData() != null) {
                    factionPlayer = fromJson(json.getData());
                } else {
                    factionPlayer = new FactionPlayer(name, UUID.fromString(uuid));
                    add(factionPlayer);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return factionPlayer;
    }

    public void add(FactionPlayer factionPlayer) {
        DatabaseManager.FACTION.getDatabaseAccess().update("INSERT INTO " + DatabaseKey.FPLAYER + " SET uuid = '" + factionPlayer.getUuid().toString() + "', name = '" + factionPlayer.getName() + "', content = '" + toJson(factionPlayer) + "'");
    }

    public void update(FactionPlayer factionPlayer) {
        DatabaseManager.FACTION.getDatabaseAccess().update("UPDATE " + DatabaseKey.FPLAYER + " SET uuid = '" + factionPlayer.getUuid().toString() + "', name = '" + factionPlayer.getName() + "', content = '" + toJson(factionPlayer) + "' WHERE uuid = '" + factionPlayer.getUuid().toString() + "'");
    }

    public void commit(FactionPlayer factionPlayer) {
        String key = RedisKey.FPLAYER + factionPlayer.getUuid().toString();
        RedissonClient redissonClient = RedisManager.FACTION.getRedissonClient();
        RBucket<String> customFPlayerRBucket = redissonClient.getBucket(key);

        customFPlayerRBucket.set(factionPlayer.toJson());
    }

    public void remove(FactionPlayer factionPlayer) {
        String key = RedisKey.FPLAYER + factionPlayer.getUuid();
        RedissonClient redissonClient = RedisManager.FACTION.getRedissonClient();
        RBucket<String> customFPlayerRBucket = redissonClient.getBucket(key);

        if (customFPlayerRBucket.isExists()) {
            customFPlayerRBucket.delete();
        }
    }

    public FactionPlayer fromJson(String json) {
        return new Gson().fromJson(json, FactionPlayer.class);
    }

    public String toJson(FactionPlayer factionPlayer) {
        return new Gson().toJson(factionPlayer);
    }
}
