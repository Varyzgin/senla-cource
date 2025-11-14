package garageV2.java;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.Comparator.comparing;

public class GarageManager {
    MasterService masters = new MasterService();
    WorkplaceService workplaces = new WorkplaceService();
    private final List<Order> orders = new ArrayList<>();

    int addOrder(Order order) {
        // Чек на место
        // Чек на то, что оно вообще существует
        Workplace workplace = workplaces.show().stream()
                .filter(item -> item.getId().equals(order.workplaceId)).findFirst().orElse(null);
        if (workplace == null) {
            System.out.println("Рабочее место не найдено");
            return -1;
        }
        // Чек на то, что заказ будет убираться в рабочие часы данного места
        boolean fitsInWorkingHoursOfPlace = workplace.workingIntervals.stream().anyMatch(day ->
                !order.startTime.isBefore(day.start()) &&  // order.start >= day.start
                        !order.endTime.isAfter(day.end())  // order.end <= day.end
        );
        if (!fitsInWorkingHoursOfPlace) {
            System.out.println("Невозможно добавить заказ, так как нарушаются часы работы помещения");
            return -1;
        }
        // Чек на то, что нет конфликтов с другими заказами
        List<Order> ordersOnSelectedWorkplace = orders.stream()
                .filter(item -> item.workplaceId.equals(order.workplaceId)).toList();
        boolean hasConflict = ordersOnSelectedWorkplace.stream().anyMatch(item ->
                item.status.equals(Status.OPENED) &&
                item.startTime.isBefore(order.endTime) && item.endTime.isAfter(order.startTime)
        );
        if (hasConflict) {
            System.out.println("Невозможно добавить заказ, так как место занято в это время");
            return -1;
        }

        // Чек на мастеров
        List<Master> currentMasters = new ArrayList<>();
        for (String id: order.masterIds) {
            Master master = masters.show().stream()
                    .filter(item -> item.getId().equals(id)).findFirst().orElse(null);
            if (master == null) {
                System.out.println("Мастер с id: " + id + " не найден");
                return -1;
            }
            currentMasters.add(master);
        }
        // Чек на то, что заказ будет убираться в рабочие часы данного мастера
        for (Master master: currentMasters) {
            boolean fitsInWorkingHours = master.timeIntervals.stream().anyMatch(day ->
                    !order.startTime.isBefore(day.start()) &&  // order.start >= day.start
                            !order.endTime.isAfter(day.end())  // order.end <= day.end
            );
            if (!fitsInWorkingHours) {
                System.out.println("Невозможно добавить заказ, так как нарушаются часы работы мастера " + master.name);
                return -1;
            }
        }
        // Чек на то, что нет конфликтов с другими заказами у данного мастера
        for (Master master: currentMasters) {
            List<Order> ordersOfMaster = orders.stream()
                    .filter(item -> item.masterIds.contains(master.getId())).toList();
            boolean conflict = ordersOfMaster.stream()
                    .anyMatch(existing -> existing.status.equals(Status.OPENED) &&
                            existing.endTime.isAfter(order.startTime) &&
                            existing.getStartTime().isBefore(order.endTime)
                    );

            if (conflict) {
                System.out.println("Невозможно добавить заказ, так как в это время занят мастер " + master.name);
                return -1;
            }
        }

        orders.add(order);
        return 0;
    }

    void removeOrder(String id) {
        orders.stream().filter(item -> item.getId().equals(id))
                .findFirst().ifPresent(item -> item.status = Status.DELETED);
    }

    void closeOrder(String id) {
        orders.stream().filter(item -> item.getId().equals(id))
                .findFirst().ifPresent(item -> item.status = Status.CLOSED);
    }

    void rescheduleOrder(String id, LocalDateTime newStart, LocalDateTime newEnd) {
        Order copy = orders.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
        if (copy == null) {
            return;
        }
        Order newOrder = new Order(copy.name, copy.price, copy.masterIds, copy.workplaceId, newStart, newEnd);
        removeOrder(id);
        int status = addOrder(newOrder);
        if (status != 0) {
            copy.status = Status.OPENED;
        }
    }

    List<Order> showOrders() {
        return orders;
    }
    void printOrders() {
        for (var order: orders) {
            System.out.println(
                    order.name + " " +
                            workplaces.show().stream().filter(o -> o.getId().equals(order.workplaceId))
                                    .map(o -> o.name).findFirst().orElse("Unknown") +        // если не найдено
                            " " + order.startTime + " " + order.endTime + " masters:"
            );
            for (var mid: order.masterIds) {
                System.out.print("    " + masters.show().stream().filter(o -> o.getId().contains(mid))
                        .map(o -> o.name).findFirst().orElse("Unknown"));
            }
        }
    }

    public List<Workplace> freeWorkplacesList() {
        List<Workplace> result = new ArrayList<>();

        for (Workplace workplace : workplaces.show()) {
            // Все открытые заказы для этого рабочего места, отсортированные по времени начала (причем, пересекаться не могут)
            List<Order> openedOrders = orders.stream()
                    .filter(order -> order.status.equals(Status.OPENED))
                    .filter(order -> order.workplaceId.equals(workplace.getId()))
                    .sorted(comparing(order -> order.startTime))
                    .toList();

            List<TimeInterval> freeTimeIntervals = new ArrayList<>();

            for (TimeInterval interval : workplace.workingIntervals) {
                LocalDateTime freeIntervalStart = interval.start();

                for (Order order : openedOrders) {
                    if (order.endTime.isBefore(interval.start())) continue;  // заказ закончился до интервала
                    if (order.startTime.isAfter(interval.end())) break;      // заказ начался после интервала

                    if (order.startTime.isAfter(freeIntervalStart)) {
                        freeTimeIntervals.add(new TimeInterval(freeIntervalStart, order.startTime));
                    }

                    // Сдвигаем freeIntervalStart за конец заказа
                    if (order.endTime.isAfter(freeIntervalStart)) {
                        freeIntervalStart = order.endTime;
                    }
                }

                // Промежуток после последнего заказа
                if (freeIntervalStart.isBefore(interval.end())) {
                    freeTimeIntervals.add(new TimeInterval(freeIntervalStart, interval.end()));
                }
            }

            // Если остался хотя бы один свободный период — добавляем рабочее место в результат
            if (!freeTimeIntervals.isEmpty()) {
                result.add(workplace);
            }
        }
        return result;
    }


    public List<Order> orderList(OrdersSortParam param, SortType type) {
        Comparator<Order> comparator = switch (param) {
            case DATE -> comparing(order -> order.startTime);
            case PRICE -> comparing(order -> order.price);
            case STATUS -> comparing(order -> order.status);
        };
        if (type == SortType.DESC) {
            comparator = comparator.reversed();
        }
        return orders.stream().sorted(comparator).toList();
    }

    public List<Master> masterList(MastersSortParam param, SortType type) {
        Comparator<Master> comparator = switch (param) {
            case ALPHABET -> comparing(master -> master.name);
            case POSITION -> comparing(master -> master.position);
        };
        if (type == SortType.DESC) {
            comparator = comparator.reversed();
        }
        return masters.show().stream().sorted(comparator).toList();
    }

    public List<Order> currentOrdersList(CurrentOrdersSortParam param, SortType type) {
        Comparator<Order> comparator = switch (param) {
            case START_TIME -> comparing(order -> order.startTime);
            case END_TIME -> comparing(order -> order.endTime);
            case PRICE -> comparing(order -> order.price);
        };
        if (type == SortType.DESC) {
            comparator = comparator.reversed();
        }
        return orders.stream().filter(order -> order.status.equals(Status.OPENED))
                .filter(order -> order.startTime.isBefore(LocalDateTime.now())
                        && order.endTime.isAfter(LocalDateTime.now())).sorted(comparator).toList();
    }

    // Заказ, выполняемый конкретным мастером
    public Order currentOrderOfMaster(Master master) {
        return orders.stream().filter(order -> order.status.equals(Status.OPENED))
                .filter(item -> item.startTime.isBefore(LocalDateTime.now())
                        && item.endTime.isAfter(LocalDateTime.now()))
                .filter(item -> item.masterIds.contains(master.getId()))
                .findFirst().orElse(null);
    }

    // Мастера, выполняющие конкретный заказ
    public List<Master> currentMastersListOfOrder(String id) {
        Order order = orders.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (order == null) {
            return List.of(); // пустой список, если заказ не найден
        }

        return masters.show().stream()
                .filter(master -> order.masterIds.contains(master.getId()))
                .toList();
    }

    // Заказы (выполненные/удаленные/отмененные) за промежуток времени (сортировать по дате подачи, дате выполнения, цене);
    public List<Order> currentOrdersList(Status status, LocalDateTime beginTime, LocalDateTime endTime,
                                         CurrentOrdersSortParam param, SortType type) {
        Set<Status> validStatuses = Set.of(Status.CANCELLED, Status.CLOSED, Status.DELETED);
        List<Order> result = orders.stream()
                .filter(order -> order.startTime.isAfter(beginTime) && order.endTime.isBefore(endTime))
                .filter(order -> validStatuses.contains(order.status)).toList();
        Comparator<Order> comparator = switch (param) {
            case START_TIME -> comparing(order -> order.startTime);
            case END_TIME -> comparing(order -> order.endTime);
            case PRICE -> comparing(order -> order.price);
        };
        if (type == SortType.DESC) {
            comparator = comparator.reversed();
        }
        return result.stream().sorted(comparator).toList();
    }

}
