package de.rjst.questsystem.logic.quest.selection.monthly;

import de.rjst.questsystem.setting.NgqProperty;
import de.rjst.questsystem.logic.config.PropertySupplier;
import de.rjst.questsystem.model.enums.IntervalType;
import de.rjst.questsystem.database.entity.config.QuestSelectionConfigEntity;
import de.rjst.questsystem.model.logic.TimePeriod;
import de.rjst.questsystem.model.logic.TimePeriodImpl;
import de.rjst.questsystem.util.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service("selectMonthlyQuestConfigSupplier")
public class SelectMonthlyQuestSelectionConfigSupplier implements Supplier<Optional<QuestSelectionConfigEntity>> {

    private static final IntervalType intervalType = IntervalType.MONTHLY;

    private final PropertySupplier propertySupplier;

    @Qualifier("newQuestsByIntervalTypeSupplier")
    private final BiFunction<Integer, IntervalType, Set<Long>> newQuestsByIntervalTypeSupplier;

    @Qualifier("createQuestSelectionConfigFunction")
    private final BiFunction<Set<Long>, TimePeriod, QuestSelectionConfigEntity> createQuestSelectionConfigFunction;

    @Override
    public Optional<QuestSelectionConfigEntity> get() {
        QuestSelectionConfigEntity result = null;
        final Integer amount = propertySupplier.apply(NgqProperty.QUESTS_MONTHLY_AMOUNT, Integer.class);
        final Set<Long> ids = newQuestsByIntervalTypeSupplier.apply(amount, intervalType);

        if (!ids.isEmpty()) {
            final LocalDateTime fixedTime = TimeUtil.getFixedTime();
            final TimePeriod period = new TimePeriodImpl(intervalType, TimeUtil.getStartDateMonth(fixedTime), TimeUtil.getEndDateMonth(fixedTime));
            result = createQuestSelectionConfigFunction.apply(ids, period);
        }

        return Optional.ofNullable(result);
    }
}
