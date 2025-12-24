package garageV4.model.repository;

import garageV4.model.domain.Workplace;

import java.time.LocalDateTime;
import java.util.Collection;

public interface WorkplaceRepositoryProvider {
    Workplace get(String id);
    Collection<Workplace> getOpened();
    Collection<Workplace> getClosed();
    Collection<Workplace> getAll();
    Workplace getByName(String name);
}
