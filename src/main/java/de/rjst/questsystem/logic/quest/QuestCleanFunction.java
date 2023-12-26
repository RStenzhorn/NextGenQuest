package de.rjst.questsystem.logic.quest;

import de.rjst.questsystem.database.entity.QuestEntity;
import de.rjst.questsystem.database.entity.config.QuestConfigEntity;
import de.rjst.questsystem.database.repository.QuestRepository;
import de.rjst.questsystem.util.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@RequiredArgsConstructor
@Service("questCleanFunction")
public class QuestCleanFunction implements Function<List<QuestEntity>, Set<String>> {

    @Qualifier("questRepository")
    private final QuestRepository questRepository;


    @Override
    public Set<String> apply(final @NotNull List<QuestEntity> quests) {
        final Set<String> result = new HashSet<>();

        for (final QuestEntity quest : quests) {
            final LocalDateTime startDate = quest.getStartDate();
            final LocalDateTime endDate = quest.getEndDate();

            if (!TimeUtil.isNowInRange(startDate, endDate)) {
                final QuestConfigEntity questConfig = quest.getQuestConfig();
                final String intervalType = questConfig.getIntervalType();
                questRepository.delete(quest);
                result.add(intervalType);
            }
        }

        return result;
    }
}
