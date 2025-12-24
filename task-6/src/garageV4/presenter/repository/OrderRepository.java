package garageV4.presenter.repository;

import garageV4.model.domain.Order;
import garageV4.model.repository.OrderRepositoryManager;
import garageV4.model.status.OrderStatus;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OrderRepository implements OrderRepositoryManager {
    private static final Map<String, Order> orders = new HashMap<>();

    @Override
    public void add(Order o) {
        orders.put(o.id(), o);
    }

    @Override
    public void remove(String id) {
        orders.get(id).setStatus(OrderStatus.DELETED);
    }

    @Override
    public void close(String id) {
        orders.get(id).setStatus(OrderStatus.CLOSED);
    }

    @Override
    public void cancel(String id) {
        orders.get(id).setStatus(OrderStatus.CANCELLED);
    }

    @Override
    public Order get(String id) {
        return orders.get(id);
    }

    @Override
    public Collection<Order> getAll() {
        return orders.values();
    }

    public void print() {
        for (var o : orders.values()) {
            System.out.println(
                    o.name() + " " + o.price() + " " + o.status() + " " + o.durationHours() + " " + o.creationTime()
            );
        }
    }
}
