package garageV4.model.repository;

import garageV4.model.domain.Master;

import java.util.Collection;

public interface MasterRepositoryProvider {
    Master get(String id);
    Collection<Master> getActive();
    Collection<Master> getInactive();
    Collection<Master> getAll();
    Master getByName(String name);
    boolean isExist(Master m);
}
