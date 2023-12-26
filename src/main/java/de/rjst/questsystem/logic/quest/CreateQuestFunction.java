package de.rjst.questsystem.logic.quest;

import de.rjst.questsystem.database.entity.QuestConditionEntity;
import de.rjst.questsystem.database.entity.QuestEntity;
import de.rjst.questsystem.database.entity.config.QuestConfigEntity;
import de.rjst.questsystem.database.repository.QuestConfigRepository;
import de.rjst.questsystem.database.repository.QuestRepository;
import de.rjst.questsystem.model.logic.SelectionConfigSet;
import de.rjst.questsystem.model.logic.SelectionConfigSetImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@RequiredArgsConstructor
@Service("createQuestFunction")
public class CreateQuestFunction implements Function<SelectionConfigSet, QuestEntity> {

    @Qualifier("questEntityMapper")
    private final BiFunction<SelectionConfigSet, QuestConfigEntity, QuestEntity> questEntityMapper;

    @Qualifier("createQuestConditionFunction")
    private final BiFunction<QuestEntity, QuestConfigEntity, QuestConditionEntity> createQuestConditionFunction;

    @Qualifier("questConfigRepository")
    private final QuestConfigRepository questConfigRepository;

    @Qualifier("questRepository")
    private final QuestRepository questRepository;


    @Override
    public QuestEntity apply(final SelectionConfigSet set) {
        QuestEntity result;

        final Optional<QuestConfigEntity> optionalQuestConfig = questConfigRepository.findById(set.getId());
        if (optionalQuestConfig.isEmpty()) {
            throw new IllegalArgumentException("QuestConfig not found");
        }
        final QuestConfigEntity questConfig = optionalQuestConfig.get();
        result = questEntityMapper.apply(set, questConfig);
        result = questRepository.save(result);

        final QuestConditionEntity questCondition = createQuestConditionFunction.apply(result, questConfig);
        result.setConditions(List.of(questCondition));

        return result;
    }
}
