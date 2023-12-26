package de.rjst.questsystem.logic.quest;

import de.rjst.questsystem.database.entity.PlayerEntity;
import de.rjst.questsystem.database.entity.QuestEntity;
import de.rjst.questsystem.database.entity.config.QuestSelectionConfigEntity;
import de.rjst.questsystem.database.repository.PlayerRepository;
import de.rjst.questsystem.model.logic.SelectionConfigSet;
import de.rjst.questsystem.model.logic.SelectionConfigSetImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service("createQuestsFunction")
public class CreateQuestsFunction implements Function<UUID, List<QuestEntity>> {

    @Qualifier("questSelectionConfigSupplier")
    private final Supplier<List<QuestSelectionConfigEntity>> questSectionConfigSupplier;

    @Qualifier("idLongFunction")
    private final Function<String, Set<Long>> idLongFunction;

    @Qualifier("createQuestFunction")
    private final Function<SelectionConfigSet, QuestEntity> createQuestFunction;

    private final PlayerRepository playerRepository;


    @Override
    public List<QuestEntity> apply(final UUID uuid) {
        final List<QuestEntity> result = new ArrayList<>();

        final Optional<PlayerEntity> optionalPlayer = playerRepository.findById(uuid);
        if (optionalPlayer.isEmpty()) {
            throw new IllegalArgumentException("Player not found");
        }

       final List<QuestSelectionConfigEntity> questSelectionConfigs = questSectionConfigSupplier.get();
        for (final QuestSelectionConfigEntity config : questSelectionConfigs) {
            final Set<Long> ids = idLongFunction.apply(config.getSelectedIds());
            for (final Long id : ids) {
                final SelectionConfigSet selectionConfigSet = getSelectionConfigSet(config, id, optionalPlayer.get());
                final QuestEntity quest = createQuestFunction.apply(selectionConfigSet);
                result.add(quest);
            }
        }
        return result;
    }

    private static SelectionConfigSet getSelectionConfigSet(final QuestSelectionConfigEntity config, final Long id, final PlayerEntity optionalPlayer) {
        return SelectionConfigSetImpl.builder()
                .id(id)
                .player(optionalPlayer)
                .questSelectionConfig(config)
                .build();
    }
}
