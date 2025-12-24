package garageV4.model.repository;

import garageV4.model.domain.Workplace;

public interface WorkplaceRepositoryManager extends WorkplaceRepositoryProvider {
    void add(Workplace w);
    void remove(String id);
    void reopen(Workplace w);
}
