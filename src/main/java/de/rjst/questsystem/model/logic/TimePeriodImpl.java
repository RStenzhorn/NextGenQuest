package de.rjst.questsystem.model.logic;

import de.rjst.questsystem.model.enums.IntervalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimePeriodImpl implements TimePeriod {

    private IntervalType intervalType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
