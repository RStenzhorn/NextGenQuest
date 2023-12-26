package de.rjst.questsystem.logic.quest.selection;

import de.rjst.questsystem.model.enums.IntervalType;
import de.rjst.questsystem.database.entity.config.QuestConfigEntity;
import de.rjst.questsystem.database.repository.QuestConfigRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.random.RandomGenerator;

@Slf4j
@RequiredArgsConstructor
@Service("newQuestsByIntervalTypeSupplier")
public class NewQuestsByIntervalTypeSupplier implements BiFunction<Integer, IntervalType, Set<Long>> {

    private final QuestConfigRepository questConfigRepository;

    private final RandomGenerator randomGenerator;

    @Override
    public Set<Long> apply(final Integer amount, final @NotNull IntervalType intervalType) {
        final List<QuestConfigEntity> questConfigs = questConfigRepository.findByIntervalType(intervalType.name());
        final Set<Long> result = new HashSet<>();

        if (questConfigs.size() >= amount) {
            while (amount > result.size()) {
                final int index = randomGenerator.nextInt(amount);
                final QuestConfigEntity questConfigEntity = questConfigs.get(index);
                result.add(questConfigEntity.getId());
            }
        } else {
            log.error("QuestConfig Error: IntervalType: {} ConfigAmount: {} Amount in DB: {}", intervalType, amount, questConfigs.size());
        }

        return result;
    }
}
