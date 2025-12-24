package garageV4.presenter;

import garageV4.model.domain.Master;
import garageV4.model.domain.Order;
import garageV4.model.domain.Workplace;
import garageV4.model.param.*;
import garageV4.model.status.OrderStatus;
import garageV4.presenter.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class GarageManager {
    private static GarageManager Singleton;

    public static GarageManager Singleton() {
        return (Singleton == null) ? new GarageManager() : Singleton;
    }

    private final MasterService masterService;
    private final WorkplaceService workplaceService;
    private final OrderService orderService;
    private final SchedulingService schedulingService;

    private GarageManager() {
        this.masterService = ServiceRegistry.MASTER;
        this.workplaceService = ServiceRegistry.WORKPLACE;
        this.orderService = ServiceRegistry.ORDER;
        this.schedulingService = ServiceRegistry.SCHEDULING;
    }

    public void addMaster(String name, String position, String rank) {
        Master m = new Master.Builder()
                .name(name)
                .position(position)
                .rank(rank)
                .build();
        masterService.add(m);
    }

    public void removeMaster(String id) {
        masterService.remove(id);
    }

    public Collection<Master> showAllMasters() {
        return masterService.getAll();
    }

    public Collection<Master> showActiveMasters() {
        return masterService.getActive();
    }

    public void addWorkplace(String name) {
        workplaceService.add(new Workplace(name));
    }

    public void removeWorkplace(String id) {
        workplaceService.remove(id);
    }

    public Collection<Workplace> showAllWorkplaces() {
        return workplaceService.getAll();
    }

    public Collection<Workplace> showOpenedWorkplaces() {
        return workplaceService.getOpened();
    }

    public void addOrder(String name, String price, String durationMinutes, String mastersNeeded, String date) {
        try {
            Order order = new Order(name, Float.parseFloat(price), Double.parseDouble(durationMinutes));
            orderService.add(order);
            if (date.isBlank()) {
                schedulingService.scheduleOrderInNearestFuture(order, Integer.parseInt(mastersNeeded));
            } else {
                schedulingService.scheduleOrderInDay(
                        order,
                        LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MM yyyy")),
                        Integer.parseInt(mastersNeeded)
                );
            }
        } catch(NumberFormatException e) {
            System.out.println("Не получилось интерпретировать введенное пользователем число" + e);
        }
    }

    public void closeOrder(String id) {
        orderService.close(id);
    }

    public void cancelOrder(String id) {
        orderService.cancel(id);
    }

    public void removeOrder(String id) {
        orderService.remove(id);
    }

    public Collection<Workplace> showFreeWorkplaces() {
        return schedulingService.getFreeWorkplacesAtTime(LocalDateTime.now());
    }

    public Collection<String> showAllOrders() {
        return orderService.getOrdersSortedBy(OrdersSortParam.CREATION_TIME, false).stream()
                .map(o -> {
                    String masters = schedulingService.mastersOfOrder(o.id()).stream()
                            .map(Master::name)
                            .collect(Collectors.joining(" "));
                    String start = o.status() == OrderStatus.CREATED ? "" :
                            schedulingService.plannedStartOfOrder(o.id()).toString();
                    String end = o.status() == OrderStatus.CREATED ? "" :
                            schedulingService.plannedEndOfOrder(o.id()).toString();
                    return o.name() + " " + o.price() + " " + o.status() + " " + start + " " + end + " " + masters;
                })
                .toList();
    }


    public Collection<Order> showOrdersSortedAs(OrdersSortParam type, boolean reversed) {
        return orderService.getOrdersSortedBy(type, reversed);
    }

    public Collection<Order> showOpenedOrdersSortedAs(OrdersSortParam type, boolean reversed) {
        return orderService.getOpenedOrdersSortedBy(type, reversed);
    }

    public Collection<Order> orderByMaster(String id) {
        return schedulingService.currentOrderOfMaster(id);
    }

    public Collection<Master> mastersOfOrder(String id) {
        return schedulingService.mastersOfOrder(id);
    }

    public Collection<Order> ordersInPeriod(LocalDateTime a, LocalDateTime b,
                                            OrdersSortParam type, boolean reversed) {
        return orderService.historyOfOrdersInPeriod(a, b, type, reversed);
    }
}
