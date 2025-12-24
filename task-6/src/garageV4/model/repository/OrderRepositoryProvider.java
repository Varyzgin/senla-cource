package garageV4.model.repository;

import garageV4.model.domain.Order;

import java.util.Collection;

public interface OrderRepositoryProvider {
    Order get(String id);
    Collection<Order> getAll();
}
