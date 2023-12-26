package de.rjst.questsystem.logic.cache;

import org.bukkit.entity.Player;

import java.math.BigDecimal;

public interface DistanceCache {

    BigDecimal get(Player player);

    BigDecimal update(Player player, BigDecimal distance);

    void remove(Player player);

}
