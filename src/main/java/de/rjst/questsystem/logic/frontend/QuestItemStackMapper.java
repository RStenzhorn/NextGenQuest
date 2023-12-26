package de.rjst.questsystem.logic.frontend;

import de.rjst.questsystem.database.entity.QuestConditionEntity;
import de.rjst.questsystem.database.entity.QuestEntity;
import de.rjst.questsystem.database.entity.config.QuestConfigEntity;
import de.rjst.questsystem.setting.NgqMessageType;
import de.rjst.questsystem.setting.NgqPlaceholder;
import de.rjst.questsystem.util.FrontendUtil;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiFunction;

import static de.rjst.questsystem.setting.NgqMessageType.QUEST_NAME;

@RequiredArgsConstructor
@Service("questItemStackMapper")
public class QuestItemStackMapper implements BiFunction<Locale, QuestEntity, ItemStack> {

    @Qualifier("messageSupplier")
    private final BiFunction<NgqMessageType, Locale, String> messageSupplier;

    @Qualifier("replacePlaceholderFunction")
    private final BiFunction<String, Map<String, String>, Component> replacePlaceHolderFunction;

    @Qualifier("questConditionLoreSupplier")
    private final BiFunction<Locale, List<QuestConditionEntity>, List<Component>> questConditionLoreSupplier;


    @Override
    public ItemStack apply(final Locale locale, final QuestEntity quest) {
        final QuestConfigEntity questConfig = quest.getQuestConfig();

        final ItemStack result = new ItemStack(Material.BOOK);
        FrontendUtil.setDisplayName(result, getItemName(locale, questConfig.getName()));

        final List<Component> lore = questConditionLoreSupplier.apply(locale, quest.getConditions());
        FrontendUtil.setLore(result, lore);
        return result;
    }

    private Component getItemName(final Locale locale, final String questName) {
        final String msg = messageSupplier.apply(QUEST_NAME, locale);
        return replacePlaceHolderFunction.apply(msg, Map.of(NgqPlaceholder.ITEM_NAME, questName));
    }
}
