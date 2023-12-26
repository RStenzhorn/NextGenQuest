package de.rjst.questsystem.logic.player;

import de.rjst.questsystem.database.entity.PlayerEntity;
import de.rjst.questsystem.database.entity.QuestEntity;
import de.rjst.questsystem.database.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@RequiredArgsConstructor
@Service("createPlayerFunction")
public class CreatePlayerFunction implements Function<UUID, Boolean> {

    private final PlayerRepository playerRepository;

    @Qualifier("loadPlayerFunction")
    private final Function<UUID, List<QuestEntity>> loadPlayerFunction;

    @Override
    public Boolean apply(final UUID uuid) {
        final PlayerEntity player = new PlayerEntity();
        player.setId(uuid);
        player.setQuestReward(BigInteger.ZERO);
        playerRepository.save(player);
        final List<QuestEntity> quests = loadPlayerFunction.apply(uuid);
        return !quests.isEmpty();
    }
}
