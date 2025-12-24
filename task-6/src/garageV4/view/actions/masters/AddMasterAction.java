package garageV4.view.actions.masters;

import garageV4.presenter.GarageManager;
import garageV4.view.IAction;

import java.util.Scanner;

public class AddMasterAction implements IAction {
    GarageManager garage = GarageManager.Singleton();
    Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.println("=== Добавление мастера ===");
        System.out.println("Введите имя мастера");
        String name = scanner.nextLine();
        System.out.println("Должность (по желанию)");
        String position = scanner.nextLine();
        System.out.println("Квалификация (по желанию)");
        String rank = scanner.nextLine();
        garage.addMaster(name, position, rank);
    }
}
