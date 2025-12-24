package garageV4.view.actions.orders;

import garageV4.model.domain.Order;
import garageV4.model.param.OrdersSortParam;
import garageV4.presenter.GarageManager;
import garageV4.view.IAction;
import garageV4.view.Menu;
import garageV4.view.MenuItem;

import java.util.Scanner;

public class EditOrderAction implements IAction {
    GarageManager garage = GarageManager.Singleton();
    Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.println("=== Выбрать для редактирования ===");
        System.out.println("Список заказов");
        Order[] orders = garage.showOpenedOrdersSortedAs(OrdersSortParam.CREATION_TIME, false)
                .toArray(Order[]::new);
        for (int i = 0; i < orders.length; i++) {
            System.out.println("    " + (i + 1) + ") " + orders[i].name());
        }
        System.out.println("\nВыберете заказ");
        int choice = scanner.nextInt();


        System.out.println("    0) Назад");
        System.out.println("    1) Удалить");
        System.out.println("    2) Закрыть");
        System.out.println("    3) Отменить");

        if (scanner.nextInt() == 1) {
            garage.removeOrder(orders[choice - 1].id());
        } else if (scanner.nextInt() == 2) {
            garage.closeOrder(orders[choice - 1].id());
        } else if (scanner.nextInt() == 3) {
            garage.cancelOrder(orders[choice - 1].id());
        }
    }
}
