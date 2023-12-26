package de.rjst.questsystem.listener.quest.farming;

import de.rjst.questsystem.config.bean.PluginListener;
import de.rjst.questsystem.model.enums.ConditionType;
import de.rjst.questsystem.model.logic.QuestRequest;
import de.rjst.questsystem.model.logic.Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
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
public class HarvestListener implements Listener {

    private static final List<Material> HARVEST_MATERIALS = List.of(
            Material.CARROTS, Material.POTATOES, Material.WHEAT, Material.BEETROOTS
    );

    @Qualifier("requestConsumer")
    private final BiConsumer<Player, Request> requestConsumer;

    @Qualifier("subConditionsSupplier")
    private final Function<Player, Map<String, String>> subConditionsSupplier;

    @EventHandler
    public void apply(final @NotNull BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();

        if (HARVEST_MATERIALS.contains(block.getType()) && block.getBlockData() instanceof final Ageable ageable) {
            if (ageable.getMaximumAge() == ageable.getAge()) {
                final Map<String, String> subConditions = subConditionsSupplier.apply(player);

                final Request request = QuestRequest.builder()
                        .conditionType(ConditionType.HARVEST.name())
                        .parameter(null)
                        .value(BigDecimal.ONE)
                        .subConditionTypes(subConditions)
                        .build();
                requestConsumer.accept(player, request);
            }
        }
    }
}
