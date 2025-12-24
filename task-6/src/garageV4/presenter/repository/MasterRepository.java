package garageV4.presenter.repository;

import garageV4.model.domain.Master;
import garageV4.model.repository.MasterRepositoryManager;
import garageV4.model.status.MasterStatus;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MasterRepository implements MasterRepositoryManager {
    private static final Map<String, Master> masters = new HashMap<>();

    @Override
    public void add(Master m) {
        masters.put(m.id(), m);
    }

    @Override
    public void remove(String id) {
        masters.get(id).setStatus(MasterStatus.INACTIVE);
    }

    @Override
    public void activate(Master m) {
        masters.get(m.id()).setStatus(MasterStatus.ACTIVE);
    }

    @Override
    public Master get(String id) {
        return masters.get(id);
    }

    @Override
    public Collection<Master> getActive() {
        return masters.values().stream()
                .filter(m -> m.status() == MasterStatus.ACTIVE)
                .toList();
    }

    @Override
    public Collection<Master> getInactive() {
        return masters.values().stream()
                .filter(m -> m.status() == MasterStatus.INACTIVE)
                .toList();
    }

    @Override
    public Collection<Master> getAll() {
        return masters.values();
    }

    @Override
    public Master getByName(String name) {
        return masters.values().stream()
                .filter(m -> m.name().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean isExist(Master m) {
        return getByName(m.name()) != null;
    }

    public void print() {
        for (var m : masters.values()) {
            System.out.println(m.name() + " " +
                    ((m.position() != null) ? m.position() + " " : "") +
                    ((m.rank() != null) ? m.rank() : "")
            );
        }
    }
}
