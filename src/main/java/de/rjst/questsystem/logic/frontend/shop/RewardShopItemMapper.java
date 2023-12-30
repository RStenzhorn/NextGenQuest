package de.rjst.questsystem.logic.frontend.shop;

import de.rjst.questsystem.database.entity.RewardShopItemEntity;
import de.rjst.questsystem.model.ItemBuild;
import de.rjst.questsystem.model.ItemBuildRequest;
import de.rjst.questsystem.setting.NgqMessageType;
import de.rjst.questsystem.setting.NgqPlaceholder;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@RequiredArgsConstructor
@Service("rewardShopItemMapper")
public class RewardShopItemMapper implements BiFunction<Locale, RewardShopItemEntity, ItemStack> {

    @Qualifier("itemStackFunction")
    private final Function<ItemBuildRequest, ItemStack> itemStackFunction;

    @Qualifier("currencyPlaceHolderFunction")
    private final Function<BigInteger, String> currencyPlaceHolderFunction;

    private final Function<String, ItemStack> itemStackMapper;

    @Override
    public ItemStack apply(final Locale locale, final RewardShopItemEntity entity) {
        ItemStack result = itemStackMapper.apply(entity.getItemStack());

        final ItemBuildRequest request = getItemBuildRequest(locale, entity, result);
        result = itemStackFunction.apply(request);
        return result;
    }

    private ItemBuildRequest getItemBuildRequest(final Locale locale, final RewardShopItemEntity entity, final ItemStack result) {
        final ItemBuildRequest request;
        if (entity.getName() != null) {
            request = ItemBuild.builder()
                    .baseItem(result)
                    .itemName(NgqMessageType.INVENTORY_ITEM_REWARD)
                    .description(NgqMessageType.INVENTORY_REWARD_DESCRIPTION)
                    .locale(locale)
                    .placeholder(Map.of(
                            NgqPlaceholder.ITEM_NAME, entity.getName(),
                            NgqPlaceholder.PRICE, currencyPlaceHolderFunction.apply(entity.getPrice())
                    ))
                    .build();
        } else {
            request = ItemBuild.builder()
                    .baseItem(result)
                    .description(NgqMessageType.INVENTORY_REWARD_DESCRIPTION)
                    .locale(locale)
                    .placeholder(Map.of(
                            NgqPlaceholder.PRICE, currencyPlaceHolderFunction.apply(entity.getPrice())
                    ))
                    .build();
        }
        return request;
    }


}
