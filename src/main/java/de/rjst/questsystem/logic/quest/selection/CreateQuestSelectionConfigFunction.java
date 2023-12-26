package de.rjst.questsystem.logic.quest.selection;

import de.rjst.questsystem.model.enums.IntervalType;
import de.rjst.questsystem.database.entity.config.QuestSelectionConfigEntity;
import de.rjst.questsystem.database.repository.QuestSelectionConfigRepository;
import de.rjst.questsystem.model.logic.TimePeriod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service("createQuestSelectionConfigFunction")
public class CreateQuestSelectionConfigFunction implements BiFunction<Set<Long>, TimePeriod, QuestSelectionConfigEntity> {

    private final QuestSelectionConfigRepository questSelectionConfigRepository;

    @Qualifier("idStringFunction")
    private final Function<Set<Long>, String> idStringSupplier;

    @Override
    public QuestSelectionConfigEntity apply(final Set<Long> ids, final TimePeriod timePeriod) {
        final String jsonIds = idStringSupplier.apply(ids);
        final IntervalType intervalType = timePeriod.getIntervalType();

        QuestSelectionConfigEntity result = new QuestSelectionConfigEntity();
        result.setId(0L);
        result.setSelectedIds(jsonIds);
        result.setStartDate(timePeriod.getStartDate());
        result.setEndDate(timePeriod.getEndDate());
        result.setIntervalType(intervalType.name());

        result = questSelectionConfigRepository.save(result);

        return result;
    }


}
