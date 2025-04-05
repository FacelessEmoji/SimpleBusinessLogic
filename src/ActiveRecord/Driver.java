package ActiveRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    private final int id;
    private final String fullName;
    private boolean isActive;
    private final List<Shift> shifts = new ArrayList<>();

    public Driver(int id, String fullName, boolean isActive) {
        this.id = id;
        this.fullName = fullName;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void addShift(Shift shift) {
        for (Shift s : shifts) {
            if (s.overlapsWith(shift)) {
                throw new IllegalArgumentException("Смена пересекается с уже существующей сменой.");
            }
        }
        shifts.add(shift);
    }

    public List<Shift> getShiftsForDate(LocalDate date) {
        List<Shift> result = new ArrayList<>();
        for (Shift s : shifts) {
            if (s.isOnDate(date)) {
                result.add(s);
            }
        }
        return result;
    }

    public boolean isAvailableAt(LocalDateTime dateTime) {
        for (Shift s : shifts) {
            if (s.isDuring(dateTime)) {
                return false;
            }
        }
        return true;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", isActive=" + isActive +
                ", shifts=" + shifts +
                '}';
    }
}
