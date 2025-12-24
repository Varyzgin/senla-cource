package garageV4.presenter.repository;

import garageV4.model.domain.ScheduleEntry;
import garageV4.model.repository.ScheduleRepositoryManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ScheduleRepository implements ScheduleRepositoryManager {
    private static final Map<String, ScheduleEntry> scheduleEntries = new HashMap<>();

    @Override
    public void add(ScheduleEntry entry) {
        scheduleEntries.put(entry.id(), entry);
    }

    @Override
    public Collection<ScheduleEntry> getAll() {
        return scheduleEntries.values();
    }

    @Override
    public ScheduleEntry findByOrder(String orderId) {
        return scheduleEntries.values().stream()
                .filter(e -> e.orderId().equals(orderId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<ScheduleEntry> findByMasterAfter(String masterId, LocalDateTime dateTime) {
        return scheduleEntries.values().stream()
                .filter(e -> e.masterIds().contains(masterId))
                .filter(e -> e.end().isAfter(dateTime))
                .toList();
    }

    @Override
    public Collection<ScheduleEntry> findByMasterAndDate(String masterId, LocalDate date) {
        LocalDateTime dayStart = date.atStartOfDay();
        LocalDateTime dayEnd = date.plusDays(1).atStartOfDay();

        return scheduleEntries.values().stream()
                .filter(e -> e.masterIds().contains(masterId))
                .filter(e -> e.start().isBefore(dayEnd) && e.end().isAfter(dayStart))
                .toList();
    }

    @Override
    public Collection<ScheduleEntry> findByWorkplaceAndDate(String workplaceId, LocalDate date) {
        LocalDateTime dayStart = date.atStartOfDay();
        LocalDateTime dayEnd = date.plusDays(1).atStartOfDay();

        return scheduleEntries.values().stream()
                .filter(e -> e.workplaceId().equals(workplaceId))
                .filter(e -> e.start().isBefore(dayEnd) && e.end().isAfter(dayStart))
                .toList();
    }

    @Override
    public Collection<ScheduleEntry> findByWorkplaceAfter(String workplaceId, LocalDateTime dateTime) {
        return scheduleEntries.values().stream()
                .filter(e -> Objects.equals(e.workplaceId(), workplaceId))
                .filter(e -> e.end().isAfter(dateTime))
                .toList();
    }
}
