package de.rjst.questsystem.database.entity.config;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "condition_sub_condition_config")
public class ConditionSubConditionConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private QuestConditionConfigEntity questConditionConfig;

    @ManyToOne(fetch = FetchType.EAGER)
    private QuestSubConditionConfigEntity questSubConditionConfig;
}
