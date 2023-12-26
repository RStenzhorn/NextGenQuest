package de.rjst.questsystem.logic.quest.selection.monthly;

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
@Service("monthlyQuestSelectionConfigSupplier")
public class MonthlyQuestSelectionConfigSupplier implements Supplier<Optional<QuestSelectionConfigEntity>> {

    private final QuestSelectionConfigRepository questSelectionConfigRepository;

    @Override
    public Optional<QuestSelectionConfigEntity> get() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime startDate = TimeUtil.getStartDateMonth(now);
        final LocalDateTime endDate = TimeUtil.getEndDateMonth(now);
        return questSelectionConfigRepository.findByIntervalTypeAndStartDateEqualsAndEndDateEquals(IntervalType.MONTHLY.name(), startDate, endDate);
    }
}
