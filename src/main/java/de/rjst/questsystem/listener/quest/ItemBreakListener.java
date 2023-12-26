package de.rjst.questsystem.listener.quest;

import de.rjst.questsystem.config.bean.PluginListener;
import de.rjst.questsystem.model.enums.ConditionType;
import de.rjst.questsystem.model.logic.QuestRequest;
import de.rjst.questsystem.model.logic.Request;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
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
public class ItemBreakListener implements Listener {

    @Qualifier("requestConsumer")
    private final BiConsumer<Player, Request> requestConsumer;

    @Qualifier("subConditionsSupplier")
    private final Function<Player, Map<String,String>> subConditionsSupplier;

    @EventHandler
    public void apply(final @NotNull PlayerItemBreakEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = event.getBrokenItem();
        final Material type = item.getType();

        final Map<String, String> subConditions = subConditionsSupplier.apply(player);

        final Request request = QuestRequest.builder()
                .conditionType(ConditionType.BREAK_ITEM.name())
                .parameter(type.name())
                .value(BigDecimal.ONE)
                .subConditionTypes(subConditions)
                .build();
        requestConsumer.accept(player,request);
    }
}
