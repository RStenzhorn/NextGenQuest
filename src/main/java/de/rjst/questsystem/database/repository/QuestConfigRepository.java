package de.rjst.questsystem.database.repository;

import de.rjst.questsystem.database.entity.config.QuestConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestConfigRepository extends JpaRepository<QuestConfigEntity, Long> {

    List<QuestConfigEntity> findByIntervalType(String intervalType);

}
