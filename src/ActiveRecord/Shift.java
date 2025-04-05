package ActiveRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Shift {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public Shift(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("Время окончания смены не может быть раньше времени начала.");
        }
        this.start = start;
        this.end = end;
    }

    public boolean overlapsWith(Shift other) {
        return !this.end.isBefore(other.start) && !this.start.isAfter(other.end);
    }

    public boolean isOnDate(LocalDate date) {
        return start.toLocalDate().equals(date);
    }

    public boolean isDuring(LocalDateTime dateTime) {
        return !dateTime.isBefore(start) && !dateTime.isAfter(end);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
