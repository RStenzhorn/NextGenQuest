package de.rjst.questsystem.logic.quest;

import de.rjst.questsystem.database.entity.QuestEntity;
import de.rjst.questsystem.database.entity.config.QuestConfigEntity;
import de.rjst.questsystem.database.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

@RequiredArgsConstructor
@Service("questsByPlayerIntervalSupplier")
public class QuestsByUuidIntervalSupplier implements BiFunction<UUID, String, List<QuestEntity>> {

    private final QuestRepository questRepository;

    @Override
    public List<QuestEntity> apply(final UUID uuid, final String intervalType) {
        final List<QuestEntity> quests = questRepository.findByPlayer_Id(uuid);
        final List<QuestEntity> result = new ArrayList<>();

        for (final QuestEntity quest : quests) {
            final QuestConfigEntity questConfig = quest.getQuestConfig();
            final String questConfigIntervalType = questConfig.getIntervalType();

            if (questConfigIntervalType.equals(intervalType)) {
                result.add(quest);
            }
        }

        return result;
    }
}
