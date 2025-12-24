package garageV4.presenter.service;

import garageV4.model.domain.Master;
import garageV4.model.domain.Workplace;
import garageV4.model.param.MastersSortParam;
import garageV4.model.repository.MasterRepositoryManager;
import garageV4.model.status.MasterStatus;
import garageV4.model.status.WorkplaceStatus;
import garageV4.presenter.repository.MasterRepository;

import java.util.Collection;
import java.util.Comparator;

public class MasterService implements MasterRepositoryManager {
    private final MasterRepository masters;
    private final SchedulingService scheduling;

    public MasterService(MasterRepository masters, SchedulingService scheduling) {
        this.masters = masters;
        this.scheduling = scheduling;
    }

    @Override
    public void add(Master m) {
        Master exist = getAll().stream()
                .filter(mr -> mr.name().equals(m.name()))
                .findFirst()
                .orElse(null);
        if (exist == null) {
            masters.add(m);
        } else {
            System.out.println("Мастер с таким именем существует");
            if (exist.status() == MasterStatus.ACTIVE) {
                System.out.println("Уже работает");
            } else {
                System.out.println("Изменяем статус на активный");
                activate(exist);
            }
        }
    }

    @Override
    public void remove(String id) {
        if (scheduling.masterHasFreeFuture(id)) {
            masters.remove(id);
        } else {
            System.out.println("Мастер не может быть удален, так как участвует в выполнении заказа в будущем");
        }
    }

    @Override
    public void activate(Master m) {
        masters.activate(m);
    }

    @Override
    public Master get(String id) {
        return masters.get(id);
    }

    @Override
    public Collection<Master> getActive() {
        return masters.getActive();
    }

    @Override
    public Collection<Master> getInactive() {
        return masters.getInactive();
    }

    @Override
    public Collection<Master> getAll() {
        return masters.getAll();
    }

    @Override
    public Master getByName(String name) {
        return masters.getByName(name);
    }

    @Override
    public boolean isExist(Master m) {
        return masters.isExist(m);
    }

    public Collection<Master> getMastersSortedBy(MastersSortParam type, boolean reversed) {
        Comparator<Master> comparator = switch (type) {
            case ALPHABET -> Comparator.comparing(Master::name);
            case POSITION -> Comparator.comparing(m ->
                    scheduling.busySlotsCountOfMaster(m.id(), 5)
            );
        };

        return masters.getActive().stream()
                .sorted(comparator)
                .toList();
    }
}
