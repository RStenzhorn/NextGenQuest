package de.rjst.questsystem.command;

import de.rjst.questsystem.config.bean.PluginCommand;
import de.rjst.questsystem.database.entity.RewardShopItemEntity;
import de.rjst.questsystem.database.repository.RewardShopItemEntityRepository;
import de.rjst.questsystem.util.ItemStackParser;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@RequiredArgsConstructor
@PluginCommand("questReward")
@Component
public class QuestRewardCommand implements CommandExecutor {

    private final RewardShopItemEntityRepository rewardShopItemEntityRepository;

    @Override
    public final boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {

        if (sender instanceof final Player player) {
            final PlayerInventory inventory = player.getInventory();
            final ItemStack itemInMainHand = inventory.getItemInMainHand();

            final RewardShopItemEntity entity = new RewardShopItemEntity();
            entity.setId(0L);
            entity.setPrice(BigInteger.ONE);
            entity.setType("ITEM");
            entity.setItemStack(ItemStackParser.convertBase64(itemInMainHand));
            rewardShopItemEntityRepository.save(entity);
        }

        return true;
    }
}
