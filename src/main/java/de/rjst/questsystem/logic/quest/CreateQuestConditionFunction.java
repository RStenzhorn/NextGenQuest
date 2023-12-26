package de.rjst.questsystem.logic.quest;

import de.rjst.questsystem.database.entity.QuestConditionEntity;
import de.rjst.questsystem.database.entity.QuestEntity;
import de.rjst.questsystem.database.entity.config.QuestConditionConfigEntity;
import de.rjst.questsystem.database.entity.config.QuestConfigEntity;
import de.rjst.questsystem.database.repository.QuestConditionConfigRepository;
import de.rjst.questsystem.database.repository.QuestConditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.BiFunction;

@RequiredArgsConstructor
@Service("createQuestConditionFunction")
public class CreateQuestConditionFunction implements BiFunction<QuestEntity, QuestConfigEntity, QuestConditionEntity> {

    @Qualifier("questConditionConfigRepository")
    private final QuestConditionConfigRepository questConditionConfigRepository;

    @Qualifier("questConditionRepository")
    private final QuestConditionRepository questConditionRepository;

    @Override
    public QuestConditionEntity apply(final QuestEntity quest, final QuestConfigEntity questConfig) {
        final Optional<QuestConditionConfigEntity> optionalQuestConditionConfig = questConditionConfigRepository.findByQuestConfigId(questConfig.getId());
        if (optionalQuestConditionConfig.isEmpty()) {
            throw new IllegalArgumentException("QuestConditionConfig not found");
        }

        final QuestConditionEntity entity = new QuestConditionEntity();
        entity.setId(0L);
        entity.setConditionConfig(optionalQuestConditionConfig.get());
        entity.setQuest(quest);
        entity.setSuccess(false);
        entity.setCurrentValue(BigDecimal.ZERO);
        return questConditionRepository.save(entity);
    }
}
