package de.rjst.questsystem.logic.frontend.shop;

import de.rjst.questsystem.database.entity.RewardShopItemEntity;
import de.rjst.questsystem.logic.config.PropertySupplier;
import de.rjst.questsystem.model.ItemBuild;
import de.rjst.questsystem.model.ItemBuildRequest;
import de.rjst.questsystem.model.button.ButtonImpl;
import de.rjst.questsystem.setting.NgqMessageType;
import de.rjst.questsystem.setting.NgqPlaceholder;
import de.rjst.questsystem.setting.NgqProperty;
import de.rjst.questsystem.model.gui.CustomGui;
import de.rjst.questsystem.model.gui.CustomGuiImpl;
import de.rjst.questsystem.util.Heads;
import de.rjst.questsystem.util.PaperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;


@Slf4j
@RequiredArgsConstructor
@Service("shopRequestConsumer")
public class ShopRequestConsumer implements BiConsumer<Player, RewardShopItemEntity> {

    @Qualifier("messageSupplier")
    private final BiFunction<NgqMessageType, Locale, String> messageSupplier;

    @Qualifier("checkQuestRewardFunction")
    private final BiFunction<UUID, BigInteger, Boolean> checkQuestRewardFunction;

    @Qualifier("shopRequestSuccessConsumer")
    private final BiConsumer<Player, RewardShopItemEntity> shopRequestSuccessConsumer;

    @Qualifier("itemStackFunction")
    private final Function<ItemBuildRequest, ItemStack> itemStackFunction;

    @Qualifier("replacePlaceholderFunction")
    private final BiFunction<String, Map<String, String>, Component> replacePlaceholderFunction;

    private final PropertySupplier propertySupplier;

    @Override
    public void accept(final @NotNull Player player, final @NotNull RewardShopItemEntity entity) {
        final Locale locale = player.locale();
        final UUID uuid = player.getUniqueId();
        final String inv = messageSupplier.apply(NgqMessageType.INVENTORY_REWARD_BUY_REQUEST, locale);
        final Component invNameComponent = PaperUtil.getMessage(inv);

        if (checkQuestRewardFunction.apply(uuid, entity.getPrice())) {
            final CustomGui customGui = new CustomGuiImpl(invNameComponent, 3);
            final ItemStack yes = itemStackFunction.apply(getYesItemBuildRequest(locale));
            final ItemStack no = itemStackFunction.apply(getNoItemBuildRequest(locale));
            customGui.setButton(2,1,new ButtonImpl(yes, e -> shopRequestSuccessConsumer.accept(player, entity)));
            customGui.setButton(6,1,new ButtonImpl(no, e -> player.closeInventory()));
            player.openInventory(customGui.getInventory());
        } else {
            final String msg = messageSupplier.apply(NgqMessageType.REWARD_NOT_ENOUGH, locale);
            final Component message = replacePlaceholderFunction.apply(msg, Map.of(
                    NgqPlaceholder.CURRENCY, propertySupplier.apply(NgqProperty.CURRENCY_PLURAL, String.class)
            ));
            player.sendMessage(message);
        }
    }

    private static ItemBuildRequest getYesItemBuildRequest(final Locale locale) {
        return ItemBuild.builder()
                .baseItem(Heads.YES)
                .locale(locale)
                .itemName(NgqMessageType.INVENTORY_YES_BUTTON)
                .placeholder(Map.of())
                .build();
    }

    private static ItemBuildRequest getNoItemBuildRequest(final Locale locale) {
        return ItemBuild.builder()
                .baseItem(Heads.NO)
                .locale(locale)
                .itemName(NgqMessageType.INVENTORY_NO_BUTTON)
                .placeholder(Map.of())
                .build();
    }
}
