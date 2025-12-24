package garageV4.presenter.service;

import garageV4.model.domain.Workplace;
import garageV4.model.repository.WorkplaceRepositoryManager;
import garageV4.model.status.WorkplaceStatus;
import garageV4.presenter.repository.WorkplaceRepository;

import java.util.Collection;

public class WorkplaceService implements WorkplaceRepositoryManager {
    private final WorkplaceRepository places;
    private final SchedulingService scheduling;

    public WorkplaceService(WorkplaceRepository places, SchedulingService scheduling) {
        this.places = places;
        this.scheduling = scheduling;
    }

    @Override
    public void add(Workplace w) {
        Workplace exist = getAll().stream()
                .filter(wp -> wp.name().equals(w.name()))
                .findFirst()
                .orElse(null);
        if (exist == null) {
            places.add(w);
        } else {
            System.out.println("Рабочее место с таким названием уже существует");
            if (exist.status() == WorkplaceStatus.OPENED) {
                System.out.println("Уже доступно");
            } else {
                System.out.println("Изменяем статус на открытый");
                reopen(exist);
            }
        }
    }

    @Override
    public void remove(String id) {
        if (scheduling.workplaceHasFreeFuture(id)) {
            places.remove(id);
        } else {
            System.out.println("Рабочее место будет занято в будущем");
        }
    }

    @Override
    public void reopen(Workplace w) {
        places.reopen(w);
    }

    @Override
    public Workplace get(String id) {
        return places.get(id);
    }

    @Override
    public Collection<Workplace> getOpened() {
        return places.getOpened();
    }

    @Override
    public Collection<Workplace> getClosed() {
        return places.getClosed();
    }

    @Override
    public Collection<Workplace> getAll() {
        return places.getAll();
    }

    @Override
    public Workplace getByName(String name) {
        return places.getByName(name);
    }
}
