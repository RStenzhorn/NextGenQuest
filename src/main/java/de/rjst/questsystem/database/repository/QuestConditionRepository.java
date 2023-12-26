package de.rjst.questsystem.database.repository;

import de.rjst.questsystem.database.entity.QuestConditionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestConditionRepository extends JpaRepository<QuestConditionEntity, Long> {
}
