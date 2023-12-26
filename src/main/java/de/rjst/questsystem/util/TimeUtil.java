package de.rjst.questsystem.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;

@UtilityClass
public class TimeUtil {

    private final Long FIXED_LOCAL_DATE = 500000000L;
    private  final int HOUR = 23;
    private  final int MINUTE = 59;
    private  final int SECONDS = 59;

    public LocalDateTime getStartDateDay(final LocalDateTime now) {
        return now.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public LocalDateTime getEndDateDay(final LocalDateTime now) {
        return now.withHour(HOUR).withMinute(MINUTE).withSecond(SECONDS).withNano(0);
    }

    public LocalDateTime getStartDateWeek(final LocalDateTime now) {
        return now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public LocalDateTime getEndDateWeek(final LocalDateTime now) {
        return now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).withHour(HOUR).withMinute(MINUTE).withSecond(SECONDS).withNano(0);
    }

    public LocalDateTime getStartDateMonth(final LocalDateTime now) {
        return now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public LocalDateTime getEndDateMonth(final LocalDateTime now) {
        final YearMonth yearMonth = YearMonth.of(now.getYear(), now.getMonth());
        final int lastDayOfMonth = yearMonth.lengthOfMonth();
        return now.withDayOfMonth(lastDayOfMonth).withHour(HOUR).withMinute(MINUTE).withSecond(SECONDS).withNano(0);
    }

    public boolean isNowInRange(@NotNull final ChronoLocalDateTime<LocalDate> start, @NotNull final ChronoLocalDateTime<LocalDate> end) {
        final LocalDateTime now = LocalDateTime.now();
        return now.isAfter(start) && now.isBefore(end);
    }

    public LocalDateTime getFixedTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.getNano() >= FIXED_LOCAL_DATE) {
            localDateTime = localDateTime.plusNanos(FIXED_LOCAL_DATE);
        }
        return localDateTime.truncatedTo(ChronoUnit.MINUTES);
    }

}
