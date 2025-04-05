import ActiveRecord.Driver;
import ActiveRecord.DriverRepository;
import ActiveRecord.Shift;
import TransactionScript.ScheduleTransactionScript;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Инициализация репозитория и создание водителя
        DriverRepository repository = new DriverRepository();
        Driver driver = new Driver(1, "Иван Иванов", true);
        repository.save(driver);

        // Инициализация транзакционного сценария для работы с графиком
        ScheduleTransactionScript scheduleScript = new ScheduleTransactionScript(repository);

        // Добавление смен для водителя
        scheduleScript.addShiftToDriver(
                driver.getId(),
                LocalDateTime.of(2025, 4, 10, 8, 0),
                LocalDateTime.of(2025, 4, 10, 12, 0)
        );
        scheduleScript.addShiftToDriver(
                driver.getId(),
                LocalDateTime.of(2025, 4, 10, 13, 0),
                LocalDateTime.of(2025, 4, 10, 17, 0)
        );

        // Попытка добавить пересекающуюся смену (ожидается ошибка)
        scheduleScript.addShiftToDriver(
                driver.getId(),
                LocalDateTime.of(2025, 4, 10, 11, 0),
                LocalDateTime.of(2025, 4, 10, 14, 0)
        );

        // Получение списка смен на определенную дату
        List<Shift> shifts = scheduleScript.getShiftsForDriverOnDate(driver.getId(), LocalDate.of(2025, 4, 10));
        System.out.println("Смены на 2025-04-10: " + shifts);

        // Проверка доступности водителя на конкретное время
        LocalDateTime checkTime = LocalDateTime.of(2025, 4, 10, 10, 0);
        boolean available = scheduleScript.checkDriverAvailability(driver.getId(), checkTime);
        System.out.println("Доступность водителя в " + checkTime + ": " + available);
    }
}
