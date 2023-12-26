package de.rjst.questsystem.logic.quest.selection.daily;

import de.rjst.questsystem.model.enums.IntervalType;
import de.rjst.questsystem.database.entity.config.QuestSelectionConfigEntity;
import de.rjst.questsystem.database.repository.QuestSelectionConfigRepository;
import de.rjst.questsystem.util.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service("dailyQuestSelectionConfigSupplier")
public class DailyQuestSelectionConfigSupplier implements Supplier<Optional<QuestSelectionConfigEntity>> {

    private final QuestSelectionConfigRepository questSelectionConfigRepository;

    @Override
    public Optional<QuestSelectionConfigEntity> get() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime startDate = TimeUtil.getStartDateDay(now);
        final LocalDateTime endDate = TimeUtil.getEndDateDay(now);
        return questSelectionConfigRepository.findByIntervalTypeAndStartDateEqualsAndEndDateEquals(IntervalType.DAILY.name(), startDate, endDate);
    }
}
