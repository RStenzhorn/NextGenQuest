package de.rjst.questsystem.model.logic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestRequest implements Request {

    private String conditionType;
    private String parameter;
    private BigDecimal value;
    private Map<String, String> subConditionTypes;

}
