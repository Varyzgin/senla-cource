package garageV4.model.repository;

import garageV4.model.domain.ScheduleEntry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public interface ScheduleRepositoryProvider {
    Collection<ScheduleEntry> getAll();
    ScheduleEntry findByOrder(String orderId);
    Collection<ScheduleEntry> findByMasterAfter(String masterId, LocalDateTime dateTime);
    Collection<ScheduleEntry> findByMasterAndDate(String masterId, LocalDate date);
    Collection<ScheduleEntry> findByWorkplaceAndDate(String workplaceId, LocalDate date);
    Collection<ScheduleEntry> findByWorkplaceAfter(String workplaceId, LocalDateTime dateTime);
}
