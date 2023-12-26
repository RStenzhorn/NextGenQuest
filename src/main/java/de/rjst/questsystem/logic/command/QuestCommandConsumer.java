package de.rjst.questsystem.logic.command;

import de.rjst.questsystem.database.entity.PlayerEntity;
import de.rjst.questsystem.database.repository.PlayerRepository;
import de.rjst.questsystem.model.ItemBuild;
import de.rjst.questsystem.model.ItemBuildRequest;
import de.rjst.questsystem.model.enums.Placeholder;
import de.rjst.questsystem.util.Heads;
import de.rjst.questsystem.model.enums.MessageType;
import de.rjst.questsystem.model.enums.QuestPermission;
import de.rjst.questsystem.exception.NoPermissionException;
import de.rjst.questsystem.model.button.ButtonImpl;
import de.rjst.questsystem.model.gui.CustomGui;
import de.rjst.questsystem.model.gui.CustomGuiImpl;
import de.rjst.questsystem.util.PaperUtil;
import de.rjst.questsystem.util.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
@Service("questCommandConsumer")
public class QuestCommandConsumer implements Consumer<Player> {

    @Qualifier("shopConsumer")
    private final Consumer<InventoryClickEvent> shopConsumer;

    @Qualifier("questConsumer")
    private final Consumer<InventoryClickEvent> questConsumer;

    @Qualifier("messageSupplier")
    private final BiFunction<MessageType, Locale, String> messageSupplier;

    @Qualifier("itemStackFunction")
    private final Function<ItemBuildRequest, ItemStack> itemStackFunction;

    @Qualifier("currencyPlaceHolderFunction")
    private final Function<BigInteger, String> currencyPlaceHolderFunction;

    private final PlayerRepository playerRepository;

    @Override
    public void accept(final Player player) {
        if (ValidatorUtil.isNotPermitted(player, QuestPermission.QUEST_CORE)) {
            throw new NoPermissionException();
        }
        final Locale locale = PaperUtil.getLocale(player);
        final String baseMessage = messageSupplier.apply(MessageType.INVENTORY_MAIN, locale);
        final Component message = PaperUtil.getMessage(baseMessage);

        final Optional<PlayerEntity> playerOptional = playerRepository.findById(player.getUniqueId());
        if (playerOptional.isPresent()) {
            final PlayerEntity playerEntity = playerOptional.get();

            final ItemStack shop = itemStackFunction.apply(getShopRequest(locale));
            final ItemStack info = itemStackFunction.apply(getInfoRequest(locale, Map.of(
                    Placeholder.CURRENCY, currencyPlaceHolderFunction.apply(playerEntity.getQuestReward())
            )));
            final ItemStack quests = itemStackFunction.apply(getQuestRequest(locale));

            final CustomGui customGui = new CustomGuiImpl(message, 3);
            customGui.setButton(2, 1, new ButtonImpl(shop, shopConsumer));
            customGui.setButton(4, 1, new ButtonImpl(info));
            customGui.setButton(6, 1, new ButtonImpl(quests, questConsumer));

            player.openInventory(customGui.getInventory());
        }
    }

    private static ItemBuildRequest getShopRequest(final Locale locale) {
        return ItemBuild.builder().
                baseItem(Heads.REWARD.clone())
                .locale(locale)
                .placeholder(Map.of())
                .itemName(MessageType.INVENTORY_MAIN_REWARDS)
                .build();
    }

    private static ItemBuildRequest getInfoRequest(final Locale locale, final Map<String, String> placeholder) {
        return ItemBuild.builder().
                baseItem(Heads.INFO.clone())
                .locale(locale)
                .placeholder(placeholder)
                .itemName(MessageType.INVENTORY_MAIN_INFO)
                .build();
    }

    private static ItemBuildRequest getQuestRequest(final Locale locale) {
        return ItemBuild.builder().
                baseItem(Heads.QUEST.clone())
                .locale(locale)
                .placeholder(Map.of())
                .itemName(MessageType.INVENTORY_MAIN_QUESTS)
                .build();
    }


}
