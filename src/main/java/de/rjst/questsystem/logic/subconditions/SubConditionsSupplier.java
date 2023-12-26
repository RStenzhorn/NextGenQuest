package de.rjst.questsystem.logic.subconditions;

import de.rjst.questsystem.model.enums.ConditionType;
import de.rjst.questsystem.util.PaperUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service("subConditionsSupplier")
public class SubConditionsSupplier implements Function<Player, Map<String,String>> {

    @Override
    public Map<String, String> apply(final @NotNull Player player) {
        final Map<String, String> result = new HashMap<>();

        final Location location = player.getLocation();
        final World world = location.getWorld();
        final PlayerInventory inventory = player.getInventory();
        final ItemStack itemInMainHand = inventory.getItemInMainHand();
        final Material itemInMainHandType = itemInMainHand.getType();

        final Biome biome = PaperUtil.getBioMeByLocation(location);
        result.put(ConditionType.SUB_BIOME.name(), biome.name());
        result.put(ConditionType.SUB_WORLD.name(), world.getName());
        result.put(ConditionType.SUB_ITEM_IN_HAND.name(), itemInMainHandType.name());

        return result;
    }
}
