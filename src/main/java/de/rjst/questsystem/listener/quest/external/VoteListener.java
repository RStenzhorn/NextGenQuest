package de.rjst.questsystem.listener.quest.external;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import de.rjst.questsystem.config.bean.PluginListener;
import de.rjst.questsystem.model.enums.ConditionType;
import de.rjst.questsystem.model.logic.QuestRequest;
import de.rjst.questsystem.model.logic.Request;
import de.rjst.questsystem.util.PaperUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

@RequiredArgsConstructor
@PluginListener
@Component
public class VoteListener implements Listener {

    @Qualifier("requestConsumer")
    private final BiConsumer<Player, Request> requestConsumer;

    @Qualifier("subConditionsSupplier")
    private final Function<Player, Map<String, String>> subConditionsSupplier;


    @EventHandler
    public void apply(final @NotNull VotifierEvent event) {
        final Vote vote = event.getVote();
        final Optional<Player> optionalPlayer = PaperUtil.getPlayer(vote.getUsername());
        if (optionalPlayer.isPresent()) {
            final Player player = optionalPlayer.get();
            final Map<String, String> subConditions = subConditionsSupplier.apply(player);

            final Request request = QuestRequest.builder()
                    .conditionType(ConditionType.VOTE.name())
                    .value(BigDecimal.ONE)
                    .subConditionTypes(subConditions)
                    .build();

            requestConsumer.accept(player, request);
        }
    }

}
