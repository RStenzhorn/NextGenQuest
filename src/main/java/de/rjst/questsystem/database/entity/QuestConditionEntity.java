package de.rjst.questsystem.database.entity;


import de.rjst.questsystem.database.entity.config.QuestConditionConfigEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "quest_condition")
public class QuestConditionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "success", nullable = false)
    private Boolean success;

    @Column(name = "current_value", nullable = false)
    private BigDecimal currentValue;

    @ManyToOne
    @JoinColumn(name = "quest_id")
    private QuestEntity quest;

    @ManyToOne(fetch = FetchType.EAGER)
    private QuestConditionConfigEntity conditionConfig;

}
