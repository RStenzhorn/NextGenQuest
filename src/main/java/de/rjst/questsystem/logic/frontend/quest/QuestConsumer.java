package de.rjst.questsystem.logic.frontend.quest;

import de.rjst.questsystem.model.ItemBuild;
import de.rjst.questsystem.model.ItemBuildRequest;
import de.rjst.questsystem.setting.NgqMessageType;
import de.rjst.questsystem.logic.config.PropertySupplier;
import de.rjst.questsystem.util.Heads;
import de.rjst.questsystem.model.button.ButtonImpl;
import de.rjst.questsystem.model.gui.CustomGui;
import de.rjst.questsystem.model.gui.CustomGuiImpl;
import de.rjst.questsystem.util.PaperUtil;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;


@RequiredArgsConstructor
@Service("questConsumer")
public class QuestConsumer implements Consumer<InventoryClickEvent> {

    private final PropertySupplier propertySupplier;

    @Qualifier("dailyQuestConsumer")
    private final Consumer<InventoryClickEvent> dailyQuestConsumer;

    @Qualifier("weeklyQuestConsumer")
    private final Consumer<InventoryClickEvent> weeklyQuestConsumer;

    @Qualifier("monthlyQuestConsumer")
    private final Consumer<InventoryClickEvent> monthlyQuestConsumer;

    @Qualifier("messageSupplier")
    private final BiFunction<NgqMessageType, Locale, String> messageSupplier;


    @Qualifier("itemStackFunction")
    private final Function<ItemBuildRequest, ItemStack> itemStackFunction;

    @Override
    public void accept(final InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof final Player player) {
            final Locale locale = player.locale();
            final String baseMessage = messageSupplier.apply(NgqMessageType.INVENTORY_MAIN, locale);
            final Component message = PaperUtil.getMessage(baseMessage);

            final ItemStack daily = itemStackFunction.apply(getDailyRequest(locale));
            final ItemStack weekly = itemStackFunction.apply(getWeeklyRequest(locale));
            final ItemStack monthly = itemStackFunction.apply(getMonthlyRequest(locale));

            final CustomGui customGui = new CustomGuiImpl(message, 3);
            customGui.setButton(2, 1, new ButtonImpl(daily, dailyQuestConsumer));
            customGui.setButton(4, 1, new ButtonImpl(weekly, weeklyQuestConsumer));
            customGui.setButton(6, 1, new ButtonImpl(monthly, monthlyQuestConsumer));
            player.openInventory(customGui.getInventory());
        }
    }

    private static ItemBuildRequest getDailyRequest(final Locale locale) {
        return ItemBuild.builder().
                baseItem(Heads.DAILY_QUEST.clone())
                .locale(locale)
                .placeholder(Map.of())
                .itemName(NgqMessageType.INVENTORY_QUESTS_SELECT_DAILY)
                .build();
    }

    private static ItemBuildRequest getWeeklyRequest(final Locale locale) {
        return ItemBuild.builder().
                baseItem(Heads.WEEKLY_QUEST.clone())
                .locale(locale)
                .placeholder(Map.of())
                .itemName(NgqMessageType.INVENTORY_QUESTS_SELECT_WEEKLY)
                .build();
    }

    private static ItemBuildRequest getMonthlyRequest(final Locale locale) {
        return ItemBuild.builder().
                baseItem(Heads.MONTHLY_QUEST.clone())
                .locale(locale)
                .placeholder(Map.of())
                .itemName(NgqMessageType.INVENTORY_QUESTS_SELECT_MONTHLY)
                .build();
    }
}
