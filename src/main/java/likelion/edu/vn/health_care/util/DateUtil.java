package likelion.edu.vn.health_care.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {
    public static LocalDate parseDateToLocalDate(Date date) {
        // Convert Date to Instant
        Instant instant = date.toInstant();

        // Convert Instant to LocalDate using system default time zone
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }
    public static Date parseLocalDateToDate(LocalDate localDate) {
        // Convert LocalDate to Instant
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

        // Convert Instant to Date
        return Date.from(instant);
    }
}
