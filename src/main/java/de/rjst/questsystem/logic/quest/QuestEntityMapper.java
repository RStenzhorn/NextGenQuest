package de.rjst.questsystem.logic.quest;

import de.rjst.questsystem.database.entity.PlayerEntity;
import de.rjst.questsystem.database.entity.QuestEntity;
import de.rjst.questsystem.database.entity.config.QuestConfigEntity;
import de.rjst.questsystem.database.entity.config.QuestSelectionConfigEntity;
import de.rjst.questsystem.model.logic.SelectionConfigSet;
import de.rjst.questsystem.model.logic.SelectionConfigSetImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.BiFunction;

@RequiredArgsConstructor
@Service("questEntityMapper")
public class QuestEntityMapper implements BiFunction<SelectionConfigSet, QuestConfigEntity, QuestEntity> {

    @Override
    public QuestEntity apply(final SelectionConfigSet set, final QuestConfigEntity questConfigEntity) {
        final PlayerEntity player = set.getPlayer();
        final QuestSelectionConfigEntity questSelectionConfig = set.getQuestSelectionConfig();

        final var result = new QuestEntity();
        result.setId(0L);
        result.setSuccess(false);
        result.setPlayer(player);
        result.setStartDate(LocalDateTime.now());
        result.setEndDate(questSelectionConfig.getEndDate());
        result.setQuestConfig(questConfigEntity);
        return result;
    }
}
