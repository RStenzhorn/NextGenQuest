package de.rjst.questsystem.logic;

import de.rjst.questsystem.database.entity.QuestEntity;
import de.rjst.questsystem.database.entity.config.QuestConfigEntity;
import de.rjst.questsystem.model.enums.MessageType;
import de.rjst.questsystem.model.enums.Placeholder;
import de.rjst.questsystem.model.logic.Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Slf4j
@RequiredArgsConstructor
@Service("requestConsumer")
public class RequestConsumer implements BiConsumer<Player, Request> {

    @Qualifier("requestFunction")
    private final BiFunction<UUID, Request, List<QuestEntity>> requestFunction;

    @Qualifier("addQuestRewardFunction")
    private final BiFunction<UUID, BigInteger, Boolean> addQuestRewardFunction;

    @Qualifier("messageSupplier")
    private final BiFunction<MessageType, Locale, String> messageSupplier;

    @Qualifier("replacePlaceholderFunction")
    private final BiFunction<String, Map<String, String>, Component> replacePlaceHolderFunction;

    private final Map<UUID, Object> playerLocks = new ConcurrentHashMap<>();

    @Override
    @Async("executorService")
    public void accept(final @NotNull Player player, final Request request) {
        final UUID uuid = player.getUniqueId();
        final Locale locale = player.locale();

        synchronized (playerLocks.computeIfAbsent(uuid,lock -> new Object())) {
            try {
                final List<QuestEntity> successQuest = requestFunction.apply(uuid, request);
                for (final QuestEntity quest : successQuest) {
                    final QuestConfigEntity questConfig = quest.getQuestConfig();

                    final BigInteger reward = questConfig.getReward();
                    final Boolean addResponse = addQuestRewardFunction.apply(uuid, reward);
                    if (addResponse) {
                        final String msg = messageSupplier.apply(MessageType.QUEST_MSG_SUCCESS, locale);
                        final Map<String, String> placeHolder = Map.of(Placeholder.REWARD, String.valueOf(reward));
                        final Component message = replacePlaceHolderFunction.apply(msg, placeHolder);
                        player.sendMessage(message);
                    }
                }
            } catch (final RuntimeException ex) {
                log.error("Request failed {}", request, ex);
            }
        }
    }
}
