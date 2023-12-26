package de.rjst.questsystem.listener.quest;

import de.rjst.questsystem.config.bean.PluginListener;
import de.rjst.questsystem.model.enums.ConditionType;
import de.rjst.questsystem.model.logic.QuestRequest;
import de.rjst.questsystem.model.logic.Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;


@Slf4j
@RequiredArgsConstructor
@PluginListener
@Component
public class SpyGlassListener implements Listener {

    private static final int MAX_DISTANCE = 50;

    @Qualifier("requestConsumer")
    private final BiConsumer<Player, Request> requestConsumer;

    @Qualifier("subConditionsSupplier")
    private final Function<Player, Map<String, String>> subConditionsSupplier;


    @EventHandler
    public void apply(final @NotNull PlayerInteractEvent event) {
        final Action action = event.getAction();
        final Player player = event.getPlayer();
        final PlayerInventory inventory = player.getInventory();
        final ItemStack itemInMainHand = inventory.getItemInMainHand();
        if (itemInMainHand.getType() == Material.SPYGLASS && action.isRightClick()) {
            final Optional<Entity> optionalEntity = getLookingEntity(player);
            if (optionalEntity.isPresent()) {
                final Entity entity = optionalEntity.get();
                final EntityType type = entity.getType();

                final Map<String, String> subConditions = subConditionsSupplier.apply(player);

                final Request request = QuestRequest.builder()
                        .conditionType(ConditionType.SPY_GLASS.name())
                        .parameter(type.name())
                        .subConditionTypes(subConditions)
                        .value(BigDecimal.ONE)
                        .build();
                requestConsumer.accept(player,request);
            }
        }
    }

    @NotNull
    private static Optional<Entity> getLookingEntity(final @NotNull Player player) {
        Entity result = null;
        final RayTraceResult rayTraceResult = player.rayTraceEntities(MAX_DISTANCE);
        if (rayTraceResult != null && rayTraceResult.getHitEntity() != null) {
            result = rayTraceResult.getHitEntity();
        }
        return Optional.ofNullable(result);
    }
}
