package de.rjst.questsystem.database.entity.config;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "quest_config")
public class QuestConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "interval_type", nullable = false)
    private String intervalType;

    @Column(name = "reward", nullable = false)
    private BigInteger reward;

    @OneToMany(mappedBy = "questConfig", fetch = FetchType.EAGER)
    private List<QuestConditionConfigEntity> conditions;

}
