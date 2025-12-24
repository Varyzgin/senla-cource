package garageV4.presenter.service;

import garageV4.presenter.repository.MasterRepository;
import garageV4.presenter.repository.OrderRepository;
import garageV4.model.repository.ScheduleRepositoryManager;
import garageV4.presenter.repository.ScheduleRepository;
import garageV4.presenter.repository.WorkplaceRepository;

public class ServiceRegistry {
    public static final MasterService MASTER;
    public static final WorkplaceService WORKPLACE;
    public static final OrderService ORDER;
    public static final SchedulingService SCHEDULING;

    static {
        MasterRepository masterRepo = new MasterRepository();
        WorkplaceRepository workplaceRepo = new WorkplaceRepository();
        OrderRepository orderRepo = new OrderRepository();
        ScheduleRepositoryManager scheduleRepo = new ScheduleRepository();

        SCHEDULING = new SchedulingService(scheduleRepo, orderRepo, workplaceRepo, masterRepo);
        MASTER = new MasterService(masterRepo, SCHEDULING);
        WORKPLACE = new WorkplaceService(workplaceRepo, SCHEDULING);
        ORDER = new OrderService(orderRepo, SCHEDULING);
    }
}