package garageV2.java;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static void testMasters(GarageManager garage) {
        System.out.println("Adding masters");
        garage.masters.add(new Master("Dima", null, null));
        garage.masters.add(new Master("Max", null, null));
        garage.masters.add(new Master("Lion", "hero", null));


        for (Master m: garage.masters.show()) {
            System.out.println(m.name);
        }
        Master masterToRemove = garage.masters.show().get(0);
        System.out.println("Removing master: " + masterToRemove.name);

        garage.masters.remove(masterToRemove);
        for (Master m : garage.masters.show()) {
            m.print();
        }

        garage.masters.show().stream().findFirst().ifPresent(master -> {
                    master.addWorkTime(new TimeInterval(
                            LocalDateTime.of(2025, 11, 13, 8, 0),
                            LocalDateTime.of(2025, 11, 13, 17, 0)
                    ));
            master.addWorkTime(new TimeInterval(
                    LocalDateTime.of(2025, 11, 14, 8, 0),
                    LocalDateTime.of(2025, 11, 14, 17, 0)
            ));
                }
        );
        for (Master m: garage.masters.show()) {
            m.print();
        }
    }
    static void testWorkplaces(GarageManager garage) {
        List<TimeInterval> workingIntervals = new ArrayList<>();
        workingIntervals.add(new TimeInterval(
                LocalDateTime.of(2025, 11, 11, 8, 0),
                LocalDateTime.of(2025, 11, 11, 17, 0)
        ));
        workingIntervals.add(new TimeInterval(
                LocalDateTime.of(2025, 11, 12, 9, 0),
                LocalDateTime.of(2025, 11, 11, 20, 0)
        ));
        workingIntervals.add(new TimeInterval(
                LocalDateTime.of(2025, 11, 13, 9, 0),
                LocalDateTime.of(2025, 11, 13, 20, 0)
        ));
        workingIntervals.add(new TimeInterval(
                LocalDateTime.of(2025, 11, 14, 7, 0),
                LocalDateTime.of(2025, 11, 14, 17, 0)
        ));
        System.out.println("Adding workplaces");
        garage.workplaces.add(new Workplace("Workplace1", workingIntervals));
        garage.workplaces.add(new Workplace("Workplace2", workingIntervals));

        List<TimeInterval> workingIntervals2 = new ArrayList<>();
        workingIntervals2.add(new TimeInterval(
                LocalDateTime.of(2025, 11, 13, 8, 0),
                LocalDateTime.of(2025, 11, 13, 17, 0)
        ));
        garage.workplaces.add(new Workplace("Workplace3", workingIntervals2));

        for (Workplace w: garage.workplaces.show()) {
            w.print();
        }
        Workplace workplaceToRemove = garage.workplaces.show().get(0);
        System.out.println("Removing workplace: " + workplaceToRemove.name);

        garage.workplaces.remove(workplaceToRemove);
        for (Workplace w : garage.workplaces.show()) {
            w.print();
        }
    }
    static void testOrders(GarageManager garage) {
        garage.addOrder(new Order("RepairEngine",
                (float) 35000.00,
                garage.masters.show().stream().filter(m -> m.name.equals("Max")).map(Master::getId).toList(),
                garage.workplaces.show().get(0).getId(),
                LocalDateTime.of(2025, 11, 14, 10, 0),
                LocalDateTime.of(2025, 11, 14, 12, 0)
        ));
        garage.printOrders();

        String idToReschedule = garage.showOrders().get(0).getId();
        System.out.println("Попробуем перенести на недоступное время");
        garage.rescheduleOrder(idToReschedule,
                LocalDateTime.of(2025, 11, 14, 7, 0),
                LocalDateTime.of(2025, 11, 14, 9, 0)
                );
        System.out.println("Попробуем перенести на правильное время");
        garage.rescheduleOrder(idToReschedule,
                LocalDateTime.of(2025, 11, 14, 8, 0),
                LocalDateTime.of(2025, 11, 14, 10, 0)
        );
        garage.printOrders();

        System.out.println("Добавим еще 1 заказ");
        garage.addOrder(new Order("FixBumper",
                (float) 120000.00,
                garage.masters.show().stream().map(Master::getId).toList(),
                garage.workplaces.show().get(1).getId(),
                LocalDateTime.of(2025, 11, 13, 8, 0),
                LocalDateTime.of(2025, 11, 13, 17, 0)
        ));
        garage.printOrders();
    }

    static void testFunctions(GarageManager garage) {
        System.out.println("\nСписок свободных рабочих мест");
        for (var w: garage.freeWorkplacesList()) {
            w.print();
        }
        String chosenOrderId = garage.masters.show().get(0).getId();
        String chosenOrderName = garage.masters.show().get(0).name;

        System.out.println("\nСписок мастеров выполняющих выбранный заказ: " + chosenOrderName);
        for (var m: garage.currentMastersListOfOrder(chosenOrderId)) {
            m.print();
        }

        System.out.println("\nСписок заказов");
        List<Order> orders = garage.orderList(OrdersSortParam.PRICE, SortType.DESC);
        for (var order: orders) {
            System.out.println(order.name + " " + order.price + " " + order.status + " " + order.startTime + " " + order.endTime);
        }

        System.out.println("\nСписок мастеров");
        List<Master> masters = garage.masterList(MastersSortParam.ALPHABET, SortType.ASC);
        for (var master: masters) {
            System.out.println(master.name + " " + master.position);
        }
        System.out.println("\nСписок текущих заказов");
        List<Order> curOrders = garage.currentOrdersList(CurrentOrdersSortParam.START_TIME, SortType.DESC);
        for (var order: curOrders) {
            System.out.println(order.name + " " + order.price + " " + order.status + " " + order.startTime + " " + order.endTime);
        }
        System.out.println("\nЗаказы (выполненные/удаленные/отмененные) за промежуток времени (сортировать по дате подачи, дате выполнения, цене)");
        List<Order> deletedOrders = garage.currentOrdersList(Status.DELETED,
                LocalDateTime.of(2025,11,14,6,0),
                LocalDateTime.of(2025,11,14,20,0),
                CurrentOrdersSortParam.PRICE, SortType.ASC);
        for (var order: deletedOrders) {
            System.out.println(order.name + " " + order.price + " " + order.status + " " + order.startTime + " " + order.endTime);
        }
    }

    public static void main(String[] args) {
        GarageManager garage = new GarageManager();

        testMasters(garage);
        testWorkplaces(garage);
        System.out.println("\n#############################\n");
        testOrders(garage);
        System.out.println("\n#############################\n");
        testFunctions(garage);
    }
}
