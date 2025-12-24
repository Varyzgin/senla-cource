package garageV4.view.actions.masters;

import garageV4.model.domain.Master;
import garageV4.presenter.GarageManager;
import garageV4.view.IAction;

import java.util.Scanner;

public class RemoveMasterAction implements IAction {
    GarageManager garage = GarageManager.Singleton();
    Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.println("=== Удаление мастера ===");
        System.out.println("Список мастеров");
        Master[] masters = garage.showActiveMasters().toArray(Master[]::new);
        for (int i = 0; i < masters.length; i++) {
            System.out.println("    " + (i + 1) + ") " +
                    masters[i].name() + " " + masters[i].position() + " " + masters[i].rank());
        }
        garage.removeMaster(masters[scanner.nextInt() - 1].id());
    }
}
