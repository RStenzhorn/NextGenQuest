package de.rjst.questsystem.logic.frontend.shop;

import de.rjst.questsystem.database.entity.RewardShopItemEntity;
import de.rjst.questsystem.model.enums.MessageType;
import de.rjst.questsystem.model.enums.RewardType;
import de.rjst.questsystem.util.ItemStackParser;
import de.rjst.questsystem.util.PaperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Locale;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Slf4j
@RequiredArgsConstructor
@Service("shopRequestSuccessConsumer")
public class ShopRequestSuccessConsumer implements BiConsumer<Player, RewardShopItemEntity> {

    @Qualifier("removeQuestRewardFunction")
    private final BiFunction<UUID, BigInteger, Boolean> removeQuestRewardFunction;

    @Qualifier("messageSupplier")
    private final BiFunction<MessageType, Locale, String> messageSupplier;

    private final ObjectFactory<Economy> economyObjectFactory;


    @Override
    public void accept(final @NotNull Player player, final @NotNull RewardShopItemEntity entity) {
        final Locale locale = player.locale();
        final RewardType rewardType = RewardType.valueOf(entity.getType());
        if (removeQuestRewardFunction.apply(player.getUniqueId(), entity.getPrice())) {
            if (rewardType == RewardType.ITEM) {
                final ItemStack itemStack = ItemStackParser.convertItemStack(entity.getItemStack());
                final PlayerInventory inventory = player.getInventory();
                inventory.addItem(itemStack);
            } else if (rewardType == RewardType.MONEY) {
                final Economy economy = economyObjectFactory.getObject();
                economy.depositPlayer(player, Double.parseDouble(entity.getCommand()));
            } else if (rewardType == RewardType.COMMAND) {
                final Server server = Bukkit.getServer();
                server.dispatchCommand(Bukkit.getConsoleSender(), entity.getCommand());
            }
            final String msg = messageSupplier.apply(MessageType.REWARD_SUCCESS, locale);
            final Component message = PaperUtil.getMessage(msg);
            player.sendMessage(message);
        } else {
            log.warn("Player has not enough currency");
        }
        player.closeInventory();
    }
}
