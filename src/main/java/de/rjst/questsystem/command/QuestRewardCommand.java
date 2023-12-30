package de.rjst.questsystem.command;

import de.rjst.questsystem.config.bean.PluginCommand;
import de.rjst.questsystem.database.entity.RewardShopItemEntity;
import de.rjst.questsystem.database.repository.RewardShopItemEntityRepository;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.function.Function;

@RequiredArgsConstructor
@PluginCommand("questReward")
@Component
public class QuestRewardCommand implements CommandExecutor {

    private final RewardShopItemEntityRepository rewardShopItemEntityRepository;
    private final Function<ItemStack, String> itemStackStringMapper;

    @Override
    public final boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {

        if (sender instanceof final Player player) {
            final PlayerInventory inventory = player.getInventory();
            final ItemStack itemInMainHand = inventory.getItemInMainHand();

            final RewardShopItemEntity entity = new RewardShopItemEntity();
            entity.setId(0L);
            entity.setPrice(BigInteger.ONE);
            entity.setType("ITEM");
            entity.setItemStack(itemStackStringMapper.apply(itemInMainHand));
            rewardShopItemEntityRepository.save(entity);
        }

        return true;
    }
}
