package de.rjst.questsystem.logic.player.reward;

import de.rjst.questsystem.database.entity.PlayerEntity;
import de.rjst.questsystem.database.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

@RequiredArgsConstructor
@Service("checkQuestRewardFunction")
public class CheckQuestRewardFunction implements BiFunction<UUID, BigInteger, Boolean> {


    private final PlayerRepository playerRepository;

    @Override
    public Boolean apply(final UUID uuid, final BigInteger amount) {
        boolean result = true;

        final Optional<PlayerEntity> optionalPlayer = playerRepository.findById(uuid);
        if (optionalPlayer.isPresent()) {
            final PlayerEntity player = optionalPlayer.get();
            final BigInteger questReward = player.getQuestReward();
            if (questReward.compareTo(amount) < 0) {
                result = false;
            }
        } else {
            result = false;
        }
        return result;
    }
}
