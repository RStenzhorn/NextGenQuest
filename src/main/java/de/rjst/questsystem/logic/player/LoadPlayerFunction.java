package de.rjst.questsystem.logic.player;

import com.github.benmanes.caffeine.cache.Cache;
import de.rjst.questsystem.database.entity.QuestEntity;
import de.rjst.questsystem.database.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

@RequiredArgsConstructor
@Service("loadPlayerFunction")
public class LoadPlayerFunction implements Function<UUID, List<QuestEntity>> {

    @Qualifier("questRepository")
    private final QuestRepository questRepository;

    @Qualifier("questCleanFunction")
    private final Function<List<QuestEntity>, Set<String>> questCleanFunction;

    @Qualifier("createQuestsFunction")
    private final Function<UUID, List<QuestEntity>> createQuestsFunction;

    @Qualifier("createQuestsByTypeFunction")
    private final BiFunction<UUID, Set<String>, List<QuestEntity>> createQuestsByTypeFunction;

    @Override
    public List<QuestEntity> apply(final UUID uuid) {
        List<QuestEntity> quests = questRepository.findByPlayer_Id(uuid);

        final Set<String> intervalTypes = questCleanFunction.apply(quests);
        if (quests.isEmpty()) {
            quests = createQuestsFunction.apply(uuid);
        } else if (!intervalTypes.isEmpty()) {
            quests.addAll(createQuestsByTypeFunction.apply(uuid, intervalTypes));
        }

        return quests;
    }
}
