package de.rjst.questsystem.listener.quest.entity;

import de.rjst.questsystem.config.bean.PluginListener;
import de.rjst.questsystem.model.enums.ConditionType;
import de.rjst.questsystem.model.logic.QuestRequest;
import de.rjst.questsystem.model.logic.Request;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;
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
public class SheepShearListener implements Listener {

    @Qualifier("requestConsumer")
    private final BiConsumer<Player, Request> requestConsumer;

    @Qualifier("subConditionsSupplier")
    private final Function<Player, Map<String,String>> subConditionsSupplier;

    @EventHandler
    public void apply(final @NotNull PlayerShearEntityEvent event) {
        final Player player = event.getPlayer();
        final Entity entity = event.getEntity();
        if (EntityType.SHEEP == entity.getType()) {
            final Map<String, String> subConditions = subConditionsSupplier.apply(player);

            final Request request = QuestRequest.builder()
                    .conditionType(ConditionType.SHEEP_SHEAR.name())
                    .value(BigDecimal.ONE)
                    .subConditionTypes(subConditions)
                    .build();
            requestConsumer.accept(player, request);
        }
    }
}
