package de.rjst.questsystem.logic.frontend.shop;

import de.rjst.questsystem.database.entity.RewardShopItemEntity;
import de.rjst.questsystem.model.button.ButtonImpl;
import de.rjst.questsystem.setting.NgqMessageType;
import de.rjst.questsystem.model.gui.PageableGui;
import de.rjst.questsystem.model.gui.PageableGuiImpl;
import de.rjst.questsystem.util.PaperUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;


@RequiredArgsConstructor
@Service("shopConsumer")
public class ShopConsumer implements Consumer<InventoryClickEvent> {

    @Qualifier("messageSupplier")
    private final BiFunction<NgqMessageType, Locale, String> messageSupplier;

    @Qualifier("rewardShopItemsSupplier")
    private final Supplier<List<RewardShopItemEntity>> rewardShopItemsSupplier;

    @Qualifier("rewardShopItemMapper")
    private final BiFunction<Locale, RewardShopItemEntity, ItemStack> rewardShopItemMapper;

    @Qualifier("shopRequestConsumer")
    private final BiConsumer<Player, RewardShopItemEntity> shopRequestConsumer;


    @Override
    public void accept(final @NotNull InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof final Player player) {
            final Locale locale = player.locale();

            final String invName = messageSupplier.apply(NgqMessageType.INVENTORY_REWARD, locale);

            final PageableGui pageableGui = new PageableGuiImpl(PaperUtil.getMessage(invName), new ButtonImpl());
            final List<RewardShopItemEntity> rewardShopItems = rewardShopItemsSupplier.get();
            for (final RewardShopItemEntity rewardShopItem : rewardShopItems) {
                final ItemStack itemStack = rewardShopItemMapper.apply(locale, rewardShopItem);
                pageableGui.addButton(new ButtonImpl(itemStack, (e) -> shopRequestConsumer.accept(player, rewardShopItem)));
            }
            player.openInventory(pageableGui.render());
        }
    }
}
