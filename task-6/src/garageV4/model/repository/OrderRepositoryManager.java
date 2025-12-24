package garageV4.model.repository;

import garageV4.model.domain.Order;

public interface OrderRepositoryManager extends OrderRepositoryProvider {
    void add(Order o);
    void remove(String id);
    void close(String id);
    void cancel(String id);
}
