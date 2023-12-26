package de.rjst.questsystem.logic.cache;

import org.bukkit.entity.Player;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static de.rjst.questsystem.config.CacheNames.FLY;

@Service
public class FlyCacheImpl implements FlyCache {
    @Cacheable(value = FLY, key = "#player.getUniqueId()")
    @Override
    public BigDecimal get(final Player player) {
        return null;
    }

    @Override
    @CachePut(value = FLY, key = "#player.getUniqueId()")
    public BigDecimal update(final Player player, final BigDecimal distance) {
        return distance;
    }

    @Override
    @CacheEvict(value = FLY, key = "#player.getUniqueId()")
    public void remove(final Player player) {
    }
}
