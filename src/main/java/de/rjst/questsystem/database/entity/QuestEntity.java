package de.rjst.questsystem.database.entity;


import de.rjst.questsystem.database.entity.config.QuestConfigEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "quest")
public class QuestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "success", nullable = false)
    private Boolean success;

    @OneToMany(mappedBy = "quest", orphanRemoval = true)
    private List<QuestConditionEntity> conditions;

    @ManyToOne
    private QuestConfigEntity questConfig;

    @ManyToOne
    private PlayerEntity player;
}
