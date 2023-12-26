package de.rjst.questsystem.listener.quest.farming;


import de.rjst.questsystem.config.bean.PluginListener;
import de.rjst.questsystem.model.enums.ConditionType;
import de.rjst.questsystem.model.logic.QuestRequest;
import de.rjst.questsystem.model.logic.Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@PluginListener
@Component
public class DropItemByMobListener implements Listener {

    @Qualifier("requestConsumer")
    private final BiConsumer<Player, Request> requestConsumer;

    @Qualifier("subConditionsSupplier")
    private final Function<Player, Map<String, String>> subConditionsSupplier;

    @EventHandler
    public void apply(final @NotNull EntityDeathEvent event) {
        final Entity entity = event.getEntity();
        if (entity instanceof final Mob mob) {
            final Player player = mob.getKiller();
            if (player != null) {
                final List<ItemStack> itemDrop = event.getDrops();
                for (final ItemStack itemStack : itemDrop) {
                    final Material type = itemStack.getType();
                    final int amount = itemStack.getAmount();

                    final Map<String, String> subConditions = subConditionsSupplier.apply(player);
                    final Request request = QuestRequest.builder()
                            .conditionType(ConditionType.DROP_ITEM.name())
                            .parameter(type.name())
                            .value(BigDecimal.valueOf(amount))
                            .subConditionTypes(subConditions)
                            .build();
                    requestConsumer.accept(player, request);
                }
            }
        }

    }

}
