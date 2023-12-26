package de.rjst.questsystem.database.repository;

import de.rjst.questsystem.database.entity.config.QuestSelectionConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface QuestSelectionConfigRepository extends JpaRepository<QuestSelectionConfigEntity, Long> {

    Optional<QuestSelectionConfigEntity> findByIntervalTypeAndStartDateEqualsAndEndDateEquals(String intervalType, LocalDateTime start, LocalDateTime end);

}
