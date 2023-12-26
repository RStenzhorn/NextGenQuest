package de.rjst.questsystem.logic.quest.selection;

import de.rjst.questsystem.database.entity.config.QuestSelectionConfigEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service("questSelectionConfigSupplier")
public class QuestSelectionConfigSupplier implements Supplier<List<QuestSelectionConfigEntity>> {

    @Qualifier("selectDailyQuestConfigSupplier")
    private final Supplier<Optional<QuestSelectionConfigEntity>> selectionDailyQuestSelectionConfigSupplier;

    @Qualifier("selectWeeklyQuestConfigSupplier")
    private final Supplier<Optional<QuestSelectionConfigEntity>> selectionWeeklyQuestSelectionConfigSupplier;

    @Qualifier("selectMonthlyQuestConfigSupplier")
    private final Supplier<Optional<QuestSelectionConfigEntity>> selectionMonthlyQuestSelectionConfigSupplier;

    @Qualifier("dailyQuestSelectionConfigSupplier")
    private final Supplier<Optional<QuestSelectionConfigEntity>> dailyQuestSelectionConfigSupplier;

    @Qualifier("weeklySelectionConfigSupplier")
    private final Supplier<Optional<QuestSelectionConfigEntity>> weeklyQuestSelectionConfigSupplier;

    @Qualifier("monthlyQuestSelectionConfigSupplier")
    private final Supplier<Optional<QuestSelectionConfigEntity>> monthlyQuestSelectionConfigSupplier;

    @Override
    public List<QuestSelectionConfigEntity> get() {
        final List<QuestSelectionConfigEntity> result = new ArrayList<>();

        final Optional<QuestSelectionConfigEntity> daily = dailyQuestSelectionConfigSupplier.get();
        if (daily.isPresent()) {
            result.add(daily.get());
        } else {
            final Optional<QuestSelectionConfigEntity> newDaily = selectionDailyQuestSelectionConfigSupplier.get();
            newDaily.ifPresent(result::add);
        }

        final Optional<QuestSelectionConfigEntity> weekly = weeklyQuestSelectionConfigSupplier.get();
        if (weekly.isPresent()) {
            result.add(weekly.get());
        } else {
            final Optional<QuestSelectionConfigEntity> newWeekly = selectionWeeklyQuestSelectionConfigSupplier.get();
            newWeekly.ifPresent(result::add);
        }

        final Optional<QuestSelectionConfigEntity> monthly = monthlyQuestSelectionConfigSupplier.get();
        if (monthly.isPresent()) {
            result.add(monthly.get());
        } else {
            final Optional<QuestSelectionConfigEntity> newMonthly = selectionMonthlyQuestSelectionConfigSupplier.get();
            newMonthly.ifPresent(result::add);
        }

        return result;
    }
}
