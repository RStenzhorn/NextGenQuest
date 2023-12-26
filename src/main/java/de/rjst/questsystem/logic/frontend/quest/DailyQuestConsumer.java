package de.rjst.questsystem.logic.frontend.quest;

import de.rjst.questsystem.model.button.Button;
import de.rjst.questsystem.model.button.ButtonImpl;
import de.rjst.questsystem.model.enums.IntervalType;
import de.rjst.questsystem.database.entity.QuestEntity;
import de.rjst.questsystem.model.enums.MessageType;
import de.rjst.questsystem.model.gui.PageableGui;
import de.rjst.questsystem.model.gui.PageableGuiImpl;
import de.rjst.questsystem.util.PaperUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;


@RequiredArgsConstructor
@Service("dailyQuestConsumer")
public class DailyQuestConsumer implements Consumer<InventoryClickEvent> {

    @Qualifier("messageSupplier")
    private final BiFunction<MessageType, Locale, String> messageSupplier;

    @Qualifier("questsByPlayerIntervalSupplier")
    private final BiFunction<UUID, String, List<QuestEntity>> questsByUuidIntervalTypeSupplier;

    @Qualifier("questItemStackMapper")
    private final BiFunction<Locale, QuestEntity, ItemStack> questItemStackMapper;

    @Override
    public void accept(final @NotNull InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof final Player player) {
            final Locale locale = player.locale();
            final UUID uuid = player.getUniqueId();
            final List<QuestEntity> quests = questsByUuidIntervalTypeSupplier.apply(uuid, IntervalType.DAILY.name());
            final String inventoryName = messageSupplier.apply(MessageType.INVENTORY_DAILY_QUESTS, locale);

            final PageableGui pageableGui = new PageableGuiImpl(PaperUtil.getMessage(inventoryName), new ButtonImpl());
            for (final QuestEntity quest : quests) {
                final ItemStack itemStack = questItemStackMapper.apply(locale, quest);
                final Button button = new ButtonImpl(itemStack);
                pageableGui.addButton(button);
            }
            final Inventory gui = pageableGui.render();
            player.openInventory(gui);
        }
    }
}
