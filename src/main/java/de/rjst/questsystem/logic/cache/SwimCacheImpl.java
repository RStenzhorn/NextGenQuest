package de.rjst.questsystem.logic.cache;

import org.bukkit.entity.Player;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static de.rjst.questsystem.config.CacheNames.SWIM;


@Service
public class SwimCacheImpl implements SwimCache {
    @Cacheable(value = SWIM, key = "#player.getUniqueId()")
    @Override
    public BigDecimal get(final Player player) {
        return null;
    }

    @Override
    @CachePut(value = SWIM, key = "#player.getUniqueId()")
    public BigDecimal update(final Player player, final BigDecimal distance) {
        return distance;
    }

    @Override
    @CacheEvict(value = SWIM, key = "#player.getUniqueId()")
    public void remove(final Player player) {
    }
}
