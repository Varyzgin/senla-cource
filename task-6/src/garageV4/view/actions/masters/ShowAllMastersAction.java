package garageV4.view.actions.masters;

import garageV4.presenter.GarageManager;
import garageV4.view.IAction;

public class ShowAllMastersAction implements IAction {
    GarageManager garage = GarageManager.Singleton();
    @Override
    public void execute() {
        for (var m : garage.showAllMasters()) {
            System.out.println(m.showFull());
        }
    }
}
