package garageV4.model.repository;

import garageV4.model.domain.ScheduleEntry;

public interface ScheduleRepositoryManager extends ScheduleRepositoryProvider {
    void add(ScheduleEntry entry);
}
