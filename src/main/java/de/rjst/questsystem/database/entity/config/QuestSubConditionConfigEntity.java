package de.rjst.questsystem.database.entity.config;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "quest_sub_condition_config")
public class QuestSubConditionConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "condition_type")
    private String conditionType;

    @Column(name = "parameter")
    private String parameter;

    @OneToMany(mappedBy = "questSubConditionConfig")
    private List<ConditionSubConditionConfig> conditionSubConditionConfigs;

}
