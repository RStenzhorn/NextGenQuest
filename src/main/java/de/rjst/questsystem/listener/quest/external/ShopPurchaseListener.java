package de.rjst.questsystem.listener.quest.external;

import com.ghostchu.quickshop.api.event.ShopPurchaseEvent;
import com.ghostchu.quickshop.api.obj.QUser;
import com.ghostchu.quickshop.api.shop.Shop;
import de.rjst.questsystem.config.bean.PluginListener;
import de.rjst.questsystem.model.enums.ConditionType;
import de.rjst.questsystem.model.logic.QuestRequest;
import de.rjst.questsystem.model.logic.Request;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;


@RequiredArgsConstructor
@PluginListener
@Component
public class ShopPurchaseListener implements Listener {

    @Qualifier("requestConsumer")
    private final BiConsumer<Player, Request> requestConsumer;

    @Qualifier("subConditionsSupplier")
    private final Function<Player, Map<String, String>> subConditionsSupplier;

    @EventHandler
    public void apply(@NotNull final ShopPurchaseEvent event) {
        final QUser purchaser = event.getPurchaser();
        final Optional<Player> optionalPlayer = purchaser.getBukkitPlayer();
        if (optionalPlayer.isPresent()) {
            final Shop shop = event.getShop();
            final Player player = optionalPlayer.get();
            if (!shop.isUnlimited()) {
                final Map<String, String> subConditions = subConditionsSupplier.apply(player);

                final Request request = QuestRequest.builder()
                        .conditionType(ConditionType.PURCHASE_PLAYER_SHOP.name())
                        .parameter(null)
                        .value(BigDecimal.ONE)
                        .subConditionTypes(subConditions)
                        .build();

                requestConsumer.accept(player, request);
            }
        }

    }
}
