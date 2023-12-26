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
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service("createQuestsByTypeFunction")
public class CreateQuestsByTypeFunction implements BiFunction<UUID, Set<String>, List<QuestEntity>> {

    @Qualifier("questSelectionConfigSupplier")
    private final Supplier<List<QuestSelectionConfigEntity>> questSectionConfigSupplier;

    @Qualifier("idLongFunction")
    private final Function<String, Set<Long>> idLongFunction;

    @Qualifier("createQuestFunction")
    private final Function<SelectionConfigSet, QuestEntity> createQuestFunction;

    private final PlayerRepository playerRepository;


    @Override
    public List<QuestEntity> apply(final UUID uuid, final Set<String> intervalTypes) {
        final List<QuestEntity> result = new ArrayList<>();

        final Optional<PlayerEntity> optionalPlayer = playerRepository.findById(uuid);
        if (optionalPlayer.isEmpty()) {
            throw new IllegalArgumentException("Player not found");
        }
        final PlayerEntity player = optionalPlayer.get();

        final List<QuestSelectionConfigEntity> questSelection = questSectionConfigSupplier.get();
        for (final String intervalType : intervalTypes) {
            for (final QuestSelectionConfigEntity questSelectionConfig : questSelection) {
                final String intervalTypeConfig = questSelectionConfig.getIntervalType();
                if (intervalTypeConfig.equals(intervalType)) {
                    final String selectedIds = questSelectionConfig.getSelectedIds();
                    final Set<Long> ids = idLongFunction.apply(selectedIds);
                    for (final Long id : ids) {
                        final SelectionConfigSet selectionConfigSetImpl = getSelectionConfigSet(questSelectionConfig, id, player);
                        result.add(createQuestFunction.apply(selectionConfigSetImpl));
                    }
                }
            }
        }

        return result;
    }

    private static SelectionConfigSet getSelectionConfigSet(final QuestSelectionConfigEntity config, final Long id, final PlayerEntity player) {
        return SelectionConfigSetImpl.builder()
                .id(id)
                .player(player)
                .questSelectionConfig(config)
                .build();
    }
}
