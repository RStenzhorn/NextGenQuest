package de.rjst.questsystem.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "player")
public class PlayerEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "quest_reward")
    private BigInteger questReward;

    @OneToMany(mappedBy = "player")
    private List<QuestEntity> quests;

}
