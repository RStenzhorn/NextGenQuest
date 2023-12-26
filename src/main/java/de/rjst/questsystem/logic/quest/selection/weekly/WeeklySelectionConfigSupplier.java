package de.rjst.questsystem.logic.quest.selection.weekly;

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
@Service("weeklySelectionConfigSupplier")
public class WeeklySelectionConfigSupplier implements Supplier<Optional<QuestSelectionConfigEntity>> {

    private final QuestSelectionConfigRepository questSelectionConfigRepository;

    @Override
    public Optional<QuestSelectionConfigEntity> get() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime startDate = TimeUtil.getStartDateWeek(now);
        final LocalDateTime endDate = TimeUtil.getEndDateWeek(now);
        return questSelectionConfigRepository.findByIntervalTypeAndStartDateEqualsAndEndDateEquals(IntervalType.WEEKLY.name(), startDate, endDate);
    }
}
