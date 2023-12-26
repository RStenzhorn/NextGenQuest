package de.rjst.questsystem.listener.quest;

import de.rjst.questsystem.config.bean.PluginListener;
import de.rjst.questsystem.model.enums.ConditionType;
import de.rjst.questsystem.model.logic.QuestRequest;
import de.rjst.questsystem.model.logic.Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@PluginListener
@Component
public class SpawnEnderDragonListener implements Listener {

    private static final double RADIUS = 50.0D;
    @Qualifier("requestConsumer")
    private final BiConsumer<Player, Request> requestConsumer;

    @Qualifier("subConditionsSupplier")
    private final Function<Player, Map<String, String>> subConditionsSupplier;

    @EventHandler
    public void apply(final EntitySpawnEvent event) {
        final Entity entity = event.getEntity();
        final EntityType type = entity.getType();
        if (type == EntityType.ENDER_DRAGON && entity.getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.DEFAULT) {
            final List<Player> players = getPlayersInRange(entity);

            for (final Player player : players) {
                final Map<String, String> subConditions = subConditionsSupplier.apply(player);
                final Request request = QuestRequest.builder()
                        .conditionType(ConditionType.SUMMON_ENDER_DRAGON.name())
                        .value(BigDecimal.ONE)
                        .subConditionTypes(subConditions)
                        .build();
                requestConsumer.accept(player,request);
            }
        }
    }

    private static List<Player> getPlayersInRange(final Entity entity) {
        final List<Player> result = new ArrayList<>();
        final Location location = entity.getLocation();
        final Collection<LivingEntity> nearbyLivingEntities = location.getNearbyLivingEntities(RADIUS);
        for (final LivingEntity nearbyLivingEntity : nearbyLivingEntities) {
            if (nearbyLivingEntity instanceof final Player player) {
                result.add(player);
            }
        }
        return result;
    }

}
