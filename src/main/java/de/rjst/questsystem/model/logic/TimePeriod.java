package de.rjst.questsystem.model.logic;

import de.rjst.questsystem.model.enums.IntervalType;

import java.time.LocalDateTime;

public interface TimePeriod {

     IntervalType getIntervalType();
     LocalDateTime getStartDate();
     LocalDateTime getEndDate();

}
