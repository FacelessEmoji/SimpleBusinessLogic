package TransactionScript;

import ActiveRecord.Driver;
import ActiveRecord.DriverRepository;
import ActiveRecord.Shift;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleTransactionScript {
    private final DriverRepository repository;

    public ScheduleTransactionScript(DriverRepository repository) {
        this.repository = repository;
    }

    public void addShiftToDriver(int driverId, LocalDateTime start, LocalDateTime end) {
        Driver driver = repository.findById(driverId);
        if (driver == null) {
            throw new IllegalArgumentException("Водитель с ID " + driverId + " не найден.");
        }
        try {
            System.out.println("Начало транзакции: добавление смены для водителя " + driver.getFullName());
            Shift shift = new Shift(start, end);
            driver.addShift(shift);
            repository.save(driver);
            System.out.println("Смена успешно добавлена.");
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении смены: " + e.getMessage());
        }
    }

    public List<Shift> getShiftsForDriverOnDate(int driverId, LocalDate date) {
        Driver driver = repository.findById(driverId);
        if (driver == null) {
            throw new IllegalArgumentException("Водитель с ID " + driverId + " не найден.");
        }
        System.out.println("Получение списка смен для водителя " + driver.getFullName() + " на дату " + date);
        return driver.getShiftsForDate(date);
    }

    public boolean checkDriverAvailability(int driverId, LocalDateTime dateTime) {
        Driver driver = repository.findById(driverId);
        if (driver == null) {
            throw new IllegalArgumentException("Водитель с ID " + driverId + " не найден.");
        }
        System.out.println("Проверка доступности водителя " + driver.getFullName() + " на момент времени " + dateTime);
        return driver.isAvailableAt(dateTime);
    }
}
