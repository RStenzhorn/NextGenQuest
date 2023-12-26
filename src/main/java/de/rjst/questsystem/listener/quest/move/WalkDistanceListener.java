package de.rjst.questsystem.listener.quest.move;

import de.rjst.questsystem.config.bean.PluginListener;
import de.rjst.questsystem.logic.cache.MoveCache;
import de.rjst.questsystem.model.enums.ConditionType;
import de.rjst.questsystem.model.logic.QuestRequest;
import de.rjst.questsystem.model.logic.Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@PluginListener
@Component
public class WalkDistanceListener implements Listener {

    @Qualifier("requestConsumer")
    private final BiConsumer<Player, Request> requestConsumer;

    @Qualifier("subConditionsSupplier")
    private final Function<Player, Map<String,String>> subConditionsSupplier;

    private final MoveCache moveCache;

    @EventHandler
    public void apply(final @NotNull PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final Location from = event.getFrom();
        final Location to = event.getTo();
        final BigDecimal distance = BigDecimal.valueOf(from.distance(to));

        if (player.isSprinting() && !player.isGliding()) {
            if (distance.compareTo(BigDecimal.ZERO) > 0) {
                final BigDecimal cachedDistance = moveCache.get(player);
                if (cachedDistance == null) {
                    moveCache.update(player, distance);
                } else {
                    final BigDecimal addedDistance = cachedDistance.add(distance);
                    moveCache.remove(player);
                    if (addedDistance.compareTo(BigDecimal.ONE) > 0) {
                        final Map<String, String> subConditions = subConditionsSupplier.apply(player);
                        final Request request = QuestRequest.builder()
                                .conditionType(ConditionType.MOVE.name())
                                .parameter(null)
                                .value(addedDistance)
                                .subConditionTypes(subConditions)
                                .build();

                        requestConsumer.accept(player, request);
                    } else {
                        moveCache.update(player, addedDistance);
                    }
                }
            }
        }
    }

}
