package de.rjst.questsystem.listener.quest.crafting;


import de.rjst.questsystem.config.bean.PluginListener;
import de.rjst.questsystem.model.enums.ConditionType;
import de.rjst.questsystem.model.logic.QuestRequest;
import de.rjst.questsystem.model.logic.Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
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
public class BrewListener implements Listener {

    @Qualifier("requestConsumer")
    private final BiConsumer<Player, Request> requestConsumer;

    @Qualifier("subConditionsSupplier")
    private final Function<Player, Map<String, String>> subConditionsSupplier;

    @EventHandler
    public void apply(final @NotNull InventoryClickEvent event) {
        final Inventory inventory = event.getClickedInventory();
        if (inventory != null && InventoryType.BREWING == inventory.getType()) {
            final ItemStack currentItem = event.getCurrentItem();
            if (currentItem != null && event.getWhoClicked() instanceof final Player player) {
                if (currentItem.getItemMeta() instanceof final PotionMeta potionMeta) {
                    final PotionData basePotionData = potionMeta.getBasePotionData();
                    final PotionType potionType = basePotionData.getType();

                    final Map<String, String> subConditions = subConditionsSupplier.apply(player);

                    final Request request = QuestRequest.builder()
                            .conditionType(ConditionType.BREW.name())
                            .parameter(potionType.name())
                            .value(BigDecimal.ONE)
                            .subConditionTypes(subConditions)
                            .build();

                    requestConsumer.accept(player, request);
                }
            }
        }
    }
}
