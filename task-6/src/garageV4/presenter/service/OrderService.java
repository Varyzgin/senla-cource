package garageV4.presenter.service;

import garageV4.model.domain.Order;
import garageV4.model.param.OrdersSortParam;
import garageV4.model.repository.OrderRepositoryManager;
import garageV4.model.status.OrderStatus;
import garageV4.presenter.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;

import static java.util.Comparator.comparing;

public class OrderService implements OrderRepositoryManager {
    private final OrderRepository orders;
    private final SchedulingService scheduling;

    public OrderService(OrderRepository orders, SchedulingService scheduling) {
        this.orders = orders;
        this.scheduling = scheduling;
    }

    @Override
    public void add(Order o) {
        orders.add(o);
    }

    @Override
    public void remove(String id) {
        orders.remove(id);
    }

    @Override
    public void close(String id) {
        orders.close(id);
    }

    @Override
    public void cancel(String id) {
        orders.cancel(id);
    }

    @Override
    public Order get(String id) {
        return orders.get(id);
    }

    @Override
    public Collection<Order> getAll() {
        return orders.getAll();
    }

    private Comparator<Order> sortOrders(OrdersSortParam type, boolean reversed) {
        Comparator<Order> comparator = switch (type) {
            case PRICE -> comparing(Order::price);
            case CREATION_TIME -> comparing(Order::creationTime);
            case START_TIME -> comparing(o -> scheduling.plannedStartOfOrder(o.id()));
            case END_TIME -> comparing(o -> scheduling.plannedEndOfOrder(o.id()));
        };
        if (reversed) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

    public Collection<Order> getOrdersSortedBy(OrdersSortParam type, boolean reversed) {
        return orders.getAll().stream()
                .filter(o -> scheduling.plannedStartOfOrder(o.id()).isBefore(LocalDateTime.now()))
                .sorted(sortOrders(type, reversed))
                .toList();
    }

    public Collection<Order> getOpenedOrdersSortedBy(OrdersSortParam type, boolean reversed) {
        return getOrdersSortedBy(type, reversed).stream()
                .filter(o -> o.status() == OrderStatus.OPENED)
                .toList();
    }

    public Collection<Order> historyOfOrdersInPeriod(LocalDateTime start, LocalDateTime end,
                                                     OrdersSortParam type, boolean reversed) {
        return scheduling.findEntriesInPeriod(start, end).stream()
                .map(e -> orders.get(e.orderId()))
                .sorted(sortOrders(type, reversed))
                .toList();
    }
}
