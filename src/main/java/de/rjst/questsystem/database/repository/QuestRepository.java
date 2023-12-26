package de.rjst.questsystem.database.repository;

import de.rjst.questsystem.database.entity.QuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestRepository extends JpaRepository<QuestEntity, Long> {

    List<QuestEntity> findByPlayer_Id(UUID uuid);

}
