package de.rjst.questsystem.database.repository;

import de.rjst.questsystem.database.entity.config.QuestConditionConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestConditionConfigRepository extends JpaRepository<QuestConditionConfigEntity, Long> {

    Optional<QuestConditionConfigEntity> findByQuestConfigId(Long configId);

}
