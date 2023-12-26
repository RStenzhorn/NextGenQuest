package de.rjst.questsystem.logic.player;

import de.rjst.questsystem.database.entity.PlayerEntity;
import de.rjst.questsystem.database.entity.QuestEntity;
import de.rjst.questsystem.database.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service("loadCacheConsumer")
public class LoadCacheConsumer implements Consumer<UUID> {

    private final PlayerRepository playerRepository;

    @Qualifier("createPlayerFunction")
    private final Function<UUID, Boolean> createPlayerFunction;

    @Qualifier("loadPlayerFunction")
    private final Function<UUID, List<QuestEntity>> loadPlayerFunction;

    @Override
    @Async("executorService")
    public void accept(final UUID uuid) {
        final Optional<PlayerEntity> optionalPlayer = playerRepository.findById(uuid);

        if (optionalPlayer.isEmpty()) {
            createPlayerFunction.apply(uuid);
        } else {
            loadPlayerFunction.apply(uuid);
        }
    }
}
