package de.rjst.questsystem.logic.frontend;

import de.rjst.questsystem.model.ItemBuildRequest;
import de.rjst.questsystem.model.enums.MessageType;
import de.rjst.questsystem.util.FrontendUtil;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@RequiredArgsConstructor
@Service("itemStackFunction")
public class ItemStackFunction implements Function<ItemBuildRequest, ItemStack> {

    @Qualifier("messageSupplier")
    private final BiFunction<MessageType, Locale, String> messageSupplier;

    @Qualifier("replacePlaceholderFunction")
    private final BiFunction<String, Map<String, String>, Component> replacePlaceholderFunction;

    @Override
    public ItemStack apply(final ItemBuildRequest request) {
        final ItemStack result = request.getBaseItem();
        final Locale locale = request.getLocale();
        final Map<String, String> placeholder = request.getPlaceholder();

        final MessageType messageTypeItemName = request.getItemName();
        if (messageTypeItemName != null) {
            final String itemNameStr = messageSupplier.apply(messageTypeItemName, locale);
            final Component itemName = replacePlaceholderFunction.apply(itemNameStr, placeholder);
            FrontendUtil.setDisplayName(result, itemName);
        }

        final MessageType itemDescription = request.getDescription();
        if (itemDescription != null) {
            final String descriptionStr = messageSupplier.apply(itemDescription, locale);
            final Component description = replacePlaceholderFunction.apply(descriptionStr, placeholder);
            FrontendUtil.setLore(result, List.of(description));
        }

        return result;
    }
}
