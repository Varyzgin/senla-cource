import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdersList {
    private List<Order> orders = new ArrayList<>();

    int addOrder(Order order) {
        List<Order> timeOverlappingOrders = this.orders.stream().filter(item ->
                        !item.status.equals(Status.CLOSED) && !item.status.equals(Status.CANCELLED) &&
                                (item.beginTime.isAfter(order.endTime) || item.endTime.isBefore(order.beginTime))
                ).toList();

        if (timeOverlappingOrders.stream().anyMatch(item -> item.master.equals(order.master))) {
            System.out.println("Невозможно добавить заказ, так как мастер занят в это время");
            return -1;
        }
        if (timeOverlappingOrders.stream().anyMatch(item -> item.workplace.equals(order.workplace))) {
            System.out.println("Невозможно добавить заказ, так как данное место занято в это время");
            return -1;
        }
        this.orders.add(order);
        return 0;
    }
    void removeOrder(Order order) {
        this.orders.remove(order);
    }
    void closeOrder(Order order) {
        this.orders.stream()
                .filter(item -> item.getId().equals(order.getId()))
                .findFirst()
                .ifPresent(o -> o.status = Status.CLOSED);
    }

    int rescheduleOrder(Order order, LocalDateTime newBegin, LocalDateTime newEnd) {
        Order copy = (Order) this.orders.stream().filter(item -> item.getId().equals(order.getId()));
        removeOrder(order);
        int status = addOrder(order);
        if (status == 0) {
            return 0;
        } else {
            addOrder(copy);
            return -1;
        }
    }
    List<Order> showOrders() {
        return this.orders;
    }
}
