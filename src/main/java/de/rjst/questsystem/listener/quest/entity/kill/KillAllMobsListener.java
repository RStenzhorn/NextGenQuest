package de.rjst.questsystem.listener.quest.entity.kill;

import de.rjst.questsystem.config.bean.PluginListener;
import de.rjst.questsystem.model.enums.ConditionType;
import de.rjst.questsystem.model.logic.QuestRequest;
import de.rjst.questsystem.model.logic.Request;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

@RequiredArgsConstructor
@PluginListener
@Component
public class KillAllMobsListener implements Listener {

    @Qualifier("requestConsumer")
    private final BiConsumer<Player, Request> requestConsumer;

    @Qualifier("subConditionsSupplier")
    private final Function<Player, Map<String,String>> subConditionsSupplier;

    @EventHandler
    public void apply(final @NotNull EntityDeathEvent event) {
        final LivingEntity entity = event.getEntity();
        final Player player = entity.getKiller();
        if (player != null && entity instanceof final Mob mob) {
            final Map<String, String> subConditions = subConditionsSupplier.apply(player);
            final EntityType entityType = mob.getType();

            final Request request = QuestRequest.builder()
                    .conditionType(ConditionType.KILL_ALL_MOBS.name())
                    .parameter(entityType.name())
                    .value(BigDecimal.ONE)
                    .subConditionTypes(subConditions)
                    .build();
            requestConsumer.accept(player,request);
        }
    }
}
