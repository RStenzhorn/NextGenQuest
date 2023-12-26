package de.rjst.questsystem.logic.cache;

import org.bukkit.entity.Player;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static de.rjst.questsystem.config.CacheNames.VEHICLE_MOVE;

@Service
public class VehicleCacheImpl implements VehicleCache {

    @Cacheable(value = VEHICLE_MOVE, key = "#player.getUniqueId()")
    @Override
    public BigDecimal get(final Player player) {
        return null;
    }

    @Override
    @CachePut(value = VEHICLE_MOVE, key = "#player.getUniqueId()")
    public BigDecimal update(final Player player, final BigDecimal distance) {
        return distance;
    }

    @Override
    @CacheEvict(value = VEHICLE_MOVE, key = "#player.getUniqueId()")
    public void remove(final Player player) {
    }

}
