package garageV4.view.actions;

public class Actions {
//    private static final GarageManager garage = GarageManager.getInstance();
//
//    public static void addWorkplaceDialog() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("=== Создание рабочего места ===");
//        System.out.println("Введите название рабочего места");
//        String name = scanner.nextLine();
//        Workplace newWp = new Workplace(name);
//        garage.workplaces.add(newWp);
//
//        System.out.println("Введите рабочие часы рабочего места в формате: dd MM yyyy hh mm - dd MM yyyy hh mm");
//        System.out.println("(по окончании нажмите 0)");
//        while (true) {
//            garage.workplaces.print();
//
//            String input = scanner.nextLine();
//
//            if (input.equals("0")) {
//                break;
//            }
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d M yyyy H m");
//
//            try {
//                LocalDateTime startTime = LocalDateTime.parse(input.split(" - ")[0], formatter);
//                LocalDateTime endTime = LocalDateTime.parse(input.split(" - ")[1], formatter);
//                TimeInterval newTimeInterval = new TimeInterval(startTime, endTime);
//                garage.workplaces.addTimeInterval(newWp.getId(), newTimeInterval);
//
//            } catch (RuntimeException e) {
//                System.out.println("Не удалось распознать интервал работы, попробуйте ввести снова");
//            }
//        }
//    }
//
//    public static void addMasterDialog() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("=== Добавление мастера ===");
//        System.out.println("Введите имя мастера");
//        String name = scanner.nextLine();
//        Master newMaster = new Master(name);
//        garage.masters.add(newMaster);
////        System.out.println("По желанию введите должность, ранг через пробел");
////        String parameter = scanner.next();
////        if (parameter != null) {
////            garage.masters.changePosition(newMaster.getId(), parameter);
////            parameter = scanner.next();
////            if (parameter != null) {
////                garage.masters.changeRank(newMaster.getId(), parameter);
////            }
////        }
//
//        System.out.println("Введите рабочие часы рабочего места в формате: dd MM yyyy hh mm - dd MM yyyy hh mm");
//        System.out.println("(по окончании нажмите 0)");
//        while (true) {
//            garage.masters.print();
//
//            String input = scanner.nextLine();
//
//            if (input.equals("0")) {
//                break;
//            }
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d M yyyy H m");
//
//            try {
//                LocalDateTime startTime = LocalDateTime.parse(input.split(" - ")[0], formatter);
//                LocalDateTime endTime = LocalDateTime.parse(input.split(" - ")[1], formatter);
//                TimeInterval newTimeInterval = new TimeInterval(startTime, endTime);
//                garage.masters.addTimeInterval(newMaster.getId(), newTimeInterval);
//
//            } catch (RuntimeException e) {
//                System.out.println("Не удалось распознать интервал работы, попробуйте ввести снова");
//            }
//        }
//    }

//    public static void addOrderDialog() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("=== Добавление заказа ===");
//        System.out.println("Введите название заказа");
//        String name = scanner.nextLine();
//        System.out.println("Введите сумму заказа");
//        Float price = scanner.nextFloat();
//        garage.masters.print();
//        garage.printOrders();
//        garage.workplaces.print();
//        System.out.println("Выберите мастера");

//        Order newOrder = new Order(name);
//        garage.masters.add(newMaster);
//        System.out.println("По желанию введите должность, ранг через пробел");
//        String parameter = scanner.next();
//        if (parameter != null) {
//            garage.masters.changePosition(newMaster.getId(), parameter);
//            parameter = scanner.next();
//            if (parameter != null) {
//                garage.masters.changeRank(newMaster.getId(), parameter);
//            }
//        }
//
//        System.out.println("Введите рабочие часы рабочего места в формате: dd MM yyyy hh mm - dd MM yyyy hh mm");
//        System.out.println("(по окончании нажмите 0)");
//        List<TimeInterval> schedule = new ArrayList<>();
//        while (true) {
//            garage.workplaces.print();
//
//            String input = scanner.nextLine();
//
//            if (input.equals("0")) {
//                break;
//            }
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d M yyyy H m");
//
//            try {
//                LocalDateTime startTime = LocalDateTime.parse(input.split(" - ")[0], formatter);
//                LocalDateTime endTime = LocalDateTime.parse(input.split(" - ")[1], formatter);
//                TimeInterval newTimeInterval = new TimeInterval(startTime, endTime);
//                garage.workplaces.addTimeInterval(newMaster.getId(), newTimeInterval);
//
//            } catch (RuntimeException e) {
//                System.out.println("Не удалось распознать интервал работы, попробуйте ввести снова");
//            }
//        }
//    }
}
