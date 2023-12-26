package de.rjst.questsystem.database.entity.config;

import de.rjst.questsystem.database.entity.QuestConditionEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "quest_condition_config")
public class QuestConditionConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "condition_type", nullable = false)
    private String conditionType;

    @Column(length = 2048)
    private String parameter;

    @Column(name = "goal_value", nullable = false)
    private BigDecimal goalValue;

    @OneToMany(mappedBy = "conditionConfig")
    private List<QuestConditionEntity> conditions;

    @OneToMany(mappedBy = "questConditionConfig", fetch = FetchType.EAGER)
    private List<ConditionSubConditionConfig> conditionSubConditionConfigs;

    @ManyToOne
    private QuestConfigEntity questConfig;

}
