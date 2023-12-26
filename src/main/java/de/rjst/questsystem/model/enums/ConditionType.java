package de.rjst.questsystem.model.enums;

import com.google.common.collect.Sets;
import de.rjst.questsystem.setting.NgqMessageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public enum ConditionType {

    BLOCK_BREAK(NgqMessageType.INVENTORY_CONDITION_TYPE_BLOCK_BREAK),
    BLOCK_PLACE(NgqMessageType.INVENTORY_CONDITION_TYPE_BLOCK_PLACE),
    MOVE(NgqMessageType.INVENTORY_CONDITION_TYPE_MOVE),
    SWIM(NgqMessageType.INVENTORY_CONDITION_TYPE_SWIM),
    BREAK_ITEM(NgqMessageType.INVENTORY_CONDITION_TYPE_BREAK_ITEM),
    FLY(NgqMessageType.INVENTORY_CONDITION_TYPE_FLY),
    CHAT(NgqMessageType.INVENTORY_CONDITION_TYPE_CHAT),
    FISHING(NgqMessageType.INVENTORY_CONDITION_TYPE_FISHING),
    CONSUME(NgqMessageType.INVENTORY_CONDITION_TYPE_CONSUME),
    CONSUME_POTION(NgqMessageType.INVENTORY_CONDITION_TYPE_CONSUME_POTION),
    VEHICLE_LORE(NgqMessageType.INVENTORY_CONDITION_TYPE_VEHICLE_LORE),
    SPY_GLASS(NgqMessageType.INVENTORY_CONDITION_TYPE_SPY_GLASS),
    CRAFTING(NgqMessageType.INVENTORY_CONDITION_TYPE_CRAFTING),
    ENCHANTMENT(NgqMessageType.INVENTORY_CONDITION_TYPE_ENCHANTMENT),
    KILL_ALL_MOBS(NgqMessageType.INVENTORY_CONDITION_TYPE_KILL_ALL_MOBS),
    KILL_MOB(NgqMessageType.INVENTORY_CONDITION_TYPE_KILL_MOB),
    KILL_PLAYER(NgqMessageType.INVENTORY_CONDITION_TYPE_KILL_PLAYER),
    SHEEP_SHEAR(NgqMessageType.INVENTORY_CONDITION_TYPE_SHEEP_SHEAR),
    COW_MILKING(NgqMessageType.INVENTORY_CONDITION_TYPE_COW_MILKING),
    SLEEP(NgqMessageType.INVENTORY_CONDITION_TYPE_SLEEP),
    VOTE(NgqMessageType.INVENTORY_CONDITION_TYPE_VOTE),
    PURCHASE_PLAYER_SHOP(NgqMessageType.INVENTORY_CONDITION_TYPE_PURCHASE_PLAYER_SHOP),
    SNOWBALL(NgqMessageType.INVENTORY_CONDITION_TYPE_SNOWBALL),
    TRADE_VILLAGER(NgqMessageType.INVENTORY_CONDITION_TYPE_TRADE_VILLAGER),
    TRADE_MERCHANT(NgqMessageType.INVENTORY_CONDITION_TYPE_TRADE_MERCHANT),
    HARVEST(NgqMessageType.INVENTORY_CONDITION_TYPE_HARVEST),
    TOTEM_OF_UNDYING(NgqMessageType.INVENTORY_CONDITION_TYPE_TOTEM_OF_UNDYING),
    BREW(NgqMessageType.INVENTORY_CONDITION_TYPE_BREW),
    SUMMON_ENDER_DRAGON(NgqMessageType.INVENTORY_CONDITION_TYPE_SUMMON_ENDER_DRAGON),
    TRIGGER_RAID(NgqMessageType.INVENTORY_CONDITION_TYPE_TRIGGER_RAID),
    DROP_ITEM(NgqMessageType.INVENTORY_CONDITION_TYPE_DROP_ITEM),
    FOUND_STRUCT(NgqMessageType.INVENTORY_CONDITION_TYPE_FOUND_STRUCT),
    SUB_BIOME(NgqMessageType.INVENTORY_SUB_CONDITION_TYPE_BIOME),
    SUB_WORLD(NgqMessageType.INVENTORY_SUB_CONDITION_TYPE_WORLD),
    SUB_ITEM_IN_HAND(NgqMessageType.INVENTORY_SUB_CONDITION_TYPE_ITEM_IN_HAND);

    private final NgqMessageType ngqMessageType;

    private static final Set<ConditionType> MATERIAL_TYPES;
    private static final Set<ConditionType> ENTITY_TYPES;
    private static final Set<ConditionType> BIOME_TYPES;

    private static final DecimalFormat decimalFormat;

    static {
        decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.GERMAN);
        decimalFormat.applyPattern("###,##0.00");

        MATERIAL_TYPES = Sets.newHashSet();
        MATERIAL_TYPES.addAll(List.of(BLOCK_PLACE, BLOCK_BREAK, HARVEST, SUB_ITEM_IN_HAND, CONSUME, CRAFTING));

        ENTITY_TYPES = Sets.newHashSet();
        ENTITY_TYPES.add(KILL_MOB);

        BIOME_TYPES = Sets.newHashSet();
        BIOME_TYPES.add(SUB_BIOME);
    }

    public String getTranslateKey(@NotNull final String value) {
        final String result;

       if (MATERIAL_TYPES.contains(this)) {
            result = "<lang:" + Material.valueOf(value).translationKey() + ">";
        } else if (ENTITY_TYPES.contains(this)) {
            result = "<lang:" + EntityType.valueOf(value).translationKey() + ">";
        } else if (BIOME_TYPES.contains(this)) {
            result = "<lang:" + Biome.valueOf(value).translationKey() + ">";
        } else {
            result = value;
        }
        return result;
    }

    @NotNull
    public static String formatValue(final BigDecimal decimal) {
        final DecimalFormat integerFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.GERMAN);
        integerFormat.applyPattern("###,###");
        return integerFormat.format(decimal);
    }
}
