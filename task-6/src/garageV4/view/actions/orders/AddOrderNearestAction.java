package garageV4.view.actions.orders;

import garageV4.model.param.OrdersSortParam;
import garageV4.model.status.OrderStatus;
import garageV4.presenter.GarageManager;
import garageV4.view.IAction;

import java.util.Scanner;

public class AddOrderNearestAction implements IAction {
    GarageManager garage = GarageManager.Singleton();
    Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.println("=== Добавление заказа ===");
        System.out.println("Введите название");
        String name = scanner.nextLine();
        System.out.println("Стоимость");
        String price = scanner.nextLine();
        System.out.println("Количество времени в часах");
        String duration = scanner.nextLine();
        System.out.println("Количество мастеров для заказа");
        String mastersNeeded = scanner.nextLine();
        System.out.println("На какую дату (число месяц год) - по желанию");
        String date = scanner.nextLine();
        garage.addOrder(name, price, duration, mastersNeeded, date);
    }
}
