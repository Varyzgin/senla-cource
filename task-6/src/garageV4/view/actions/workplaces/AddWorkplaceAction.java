package garageV4.view.actions.workplaces;

import garageV4.presenter.GarageManager;
import garageV4.view.IAction;

import java.util.Scanner;

public class AddWorkplaceAction implements IAction {
    GarageManager garage = GarageManager.Singleton();
    Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.println("=== Добавление рабочего места ===");
        System.out.println("Введите название рабочего места");
        garage.addWorkplace(scanner.nextLine());
    }
}
