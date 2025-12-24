package garageV4.model.repository;

import garageV4.model.domain.Master;

public interface MasterRepositoryManager extends MasterRepositoryProvider {
    void add(Master m);
    void remove(String id);
    void activate(Master m);
}
