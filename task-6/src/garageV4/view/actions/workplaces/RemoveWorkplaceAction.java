package garageV4.view.actions.workplaces;

import garageV4.model.domain.Workplace;
import garageV4.presenter.GarageManager;
import garageV4.view.IAction;

import java.util.Scanner;

public class RemoveWorkplaceAction implements IAction {
    GarageManager garage = GarageManager.Singleton();
    Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.println("=== Удаление рабочего места ===");
        System.out.println("Список рабочих мест");
        Workplace[] workplaces = garage.showOpenedWorkplaces().toArray(Workplace[]::new);
        for (int i = 0; i < workplaces.length; i++) {
            System.out.println("    " + (i + 1) + ") " + workplaces[i].name());
        }
        garage.removeWorkplace(workplaces[scanner.nextInt() - 1].id());
    }
}
