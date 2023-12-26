package de.rjst.questsystem.database.repository;

import de.rjst.questsystem.database.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlayerRepository extends JpaRepository<PlayerEntity, UUID> {

}
