package garageV4.presenter.repository;

import garageV4.model.domain.Workplace;
import garageV4.model.repository.WorkplaceRepositoryManager;
import garageV4.model.status.WorkplaceStatus;

import java.time.LocalDateTime;
import java.util.*;

public class WorkplaceRepository implements WorkplaceRepositoryManager {
    private static final Map<String, Workplace> workplaces = new HashMap<>();

    @Override
    public void add(Workplace w) {
        workplaces.put(w.id(), w);
    }

    @Override
    public void remove(String id) {
        workplaces.get(id).setStatus(WorkplaceStatus.CLOSED);
    }

    @Override
    public void reopen(Workplace w) {
        workplaces.get(w.id()).setStatus(WorkplaceStatus.OPENED);
    }

    @Override
    public Workplace get(String id) {
        return workplaces.get(id);
    }
    @Override
    public Collection<Workplace> getOpened() {
        return workplaces.values().stream()
                .filter(w -> w.status() == WorkplaceStatus.OPENED)
                .toList();
    }

    @Override
    public Collection<Workplace> getClosed() {
        return workplaces.values().stream()
                .filter(w -> w.status() == WorkplaceStatus.CLOSED)
                .toList();
    }
    @Override
    public Collection<Workplace> getAll() {
        return workplaces.values();
    }

    @Override
    public Workplace getByName(String name) {
        return workplaces.values().stream()
                .filter(w -> w.name().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void print() {
        for (var w : workplaces.values()) {
            System.out.println(w.name());
        }
    }
}
